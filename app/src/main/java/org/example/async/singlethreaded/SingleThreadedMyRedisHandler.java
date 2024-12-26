package org.example.async.singlethreaded;

import org.example.async.RequestHandler;
import org.example.commands.CommandRegistry;
import org.example.commands.IRedisCommand;
import org.example.customlog.LoggerFactory;
import org.example.customlog.MyLogger;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class SingleThreadedMyRedisHandler implements RequestHandler {

    private int SERVER_PORT = 0;

    private static MyLogger logger = LoggerFactory.getLoggerInstance();
    public SingleThreadedMyRedisHandler( int port){
        System.out.println( );
        this.SERVER_PORT = port;
        logger.logMessage( "SingleThreaded Server is starting at port : "+ SERVER_PORT);
        CommandRegistry.initCommandRegistry();

    }


    @Override
    public void handleRequests() {
        try (Selector selector = Selector.open(); ServerSocketChannel serverChannel = ServerSocketChannel.open()) {

            logger.logMessage( "Configuration started ");
            serverChannel.configureBlocking(false);
            serverChannel.socket().bind(new InetSocketAddress(SERVER_PORT));
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            //System.out.println("Server is listening on port " + SERVER_PORT);
            logger.logMessage( "Server is listening on port " + SERVER_PORT);
            while (true) {
                // Wait for an event
                logger.logMessage( "Waiting for event ");
                selector.select();
                logger.logMessage( "For the event for  "+ selector.selectedKeys() );
                // Iterate through the selected keys
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    keyIterator.remove();

                    if (key.isAcceptable()) {
                        handleAccept(selector, key);
                    } else if (key.isReadable()) {
                        handleRead(key);
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleAccept(Selector selector, SelectionKey key) throws IOException {
        // Accept the new client connection
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = serverChannel.accept();
        clientChannel.configureBlocking(false);

        // Register the new client with the selector for READ events
        clientChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
        System.out.println("New client connected: " + clientChannel.getRemoteAddress());
    }

    private void handleRead(SelectionKey key) {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();

        try {
            int bytesRead = clientChannel.read(buffer);

            // Check if the client has closed the connection
            if (bytesRead == -1) {
                System.out.println("Client disconnected: " + clientChannel.getRemoteAddress());
                clientChannel.close();
                key.cancel();
                return;
            }

            // Process the data in the buffer
            buffer.flip();
            String receivedData = new String(buffer.array(), 0, buffer.limit());
            buffer.clear();
            System.out.println("Received from client: " + receivedData);

            // Handle RESP Protocol (Simplified)
            String[] lines = receivedData.split("\r\n");
            if (lines.length > 0 && lines[0].startsWith("*")) {
                int arrayLength = Integer.parseInt(lines[0].substring(1));
                String[] arguments = new String[arrayLength];
                int idx = 1;
                for (int i = 0; i < arrayLength; i++) {
                    idx++; // Skip $<length>
                    arguments[i] = lines[idx];
                    idx++;
                }
                handleCommand(clientChannel, arguments);
            }

        } catch (IOException e) {
            System.err.println("Read exception: " + e.getMessage());
            try {
                clientChannel.close();
                key.cancel();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void handleCommand(SocketChannel clientChannel, String[] arguments) throws IOException {
//            String response;
//            if (arguments[0].equalsIgnoreCase("PING")) {
//                response = "+PONG\r\n";
//            } else if (arguments[0].equalsIgnoreCase("ECHO") && arguments.length > 1) {
//                response = "$" + arguments[1].length() + "\r\n" + arguments[1] + "\r\n";
//            } else {
//                response = "+OK\r\n";
//            }
//
//            // Send response to the client
//            clientChannel.write(ByteBuffer.wrap(response.getBytes()));
        if (arguments == null || arguments.length == 0) {
            clientChannel.write(ByteBuffer.wrap("-ERR no command specified\r\n".getBytes()));
            return;
        }

        String commandName = arguments[0];
        String[] commandArgs = java.util.Arrays.copyOfRange(arguments, 1, arguments.length);

        IRedisCommand command = CommandRegistry.getCommand(commandName);
        String response;

        if (command != null) {
            response = command.execute(commandArgs);
        } else {
            response = "-ERR unknown command '" + commandName + "'\r\n";
        }

        // Send response to the client
        clientChannel.write(ByteBuffer.wrap(response.getBytes()));
        }

}
