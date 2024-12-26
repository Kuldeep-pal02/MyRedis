package org.example.async.multithreaded;

import org.example.async.RequestHandler;
import org.example.customlog.LoggerFactory;
import org.example.customlog.MyLogger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class MultiThreadedMyRedisClientHandler implements RequestHandler {

    private int SERVER_PORT = 0;

    private static MyLogger logger = LoggerFactory.getLoggerInstance();
    public MultiThreadedMyRedisClientHandler( int portNumber ){
        this.SERVER_PORT = portNumber;
        logger.logMessage( "Multithreaded Server is starting at port : "+ SERVER_PORT);
    }



    @Override
    public void handleRequests(){
        try ( ServerSocket serverSocket = new ServerSocket( SERVER_PORT ) ) {
            System.out.println("Server is listening on port " + SERVER_PORT );

            while(true) {
                // Wait for client connection
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected at local port: " + clientSocket.getPort());

                // Create a new thread for each client
                new Thread( () -> {
                    try (
                            InputStream inputStream = clientSocket.getInputStream();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                            OutputStream outputStream = clientSocket.getOutputStream();
                            PrintWriter writer = new PrintWriter(outputStream, true)
                    ) {
                        while (true) {
                            // Parse RESP array
                            String line = reader.readLine();
                            if (line == null) break;

                            if (line.startsWith("*")) {
                                int arrayLength = Integer.parseInt(line.substring(1));
                                String[] arguments = new String[arrayLength];
                                for (int i = 0; i < arrayLength; i++) {
                                    // Expect $<length> format
                                    line = reader.readLine(); // $length
                                    int length = Integer.parseInt(line.substring(1));
                                    arguments[i] = reader.readLine();
                                }
                                System.out.println("Received Command: " + String.join(" ", arguments));

                                // Simple logic: If the command is "PING", reply with "PONG"
                                if (arguments[0].equalsIgnoreCase("PING")) {
                                    writer.print("+PONG\r\n");
                                }
                                else if (arguments[0].equalsIgnoreCase("ECHO") && arguments.length > 1) {
                                    writer.print("$" + arguments[1].length() + "\r\n" + arguments[1] + "\r\n");
                                }
                                else {
                                    writer.print("+OK\r\n");
                                }
                                writer.flush();
                            }
                        }

                    } catch (IOException e) {
                        System.err.println("Client exception: " + e.getMessage());
                        e.printStackTrace();
                    } finally {
                        try {
                            clientSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } ).start();
            }

        } catch (IOException e) {
            System.err.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
