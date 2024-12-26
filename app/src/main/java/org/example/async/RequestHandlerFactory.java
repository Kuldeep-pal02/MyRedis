package org.example.async;

import org.example.async.multithreaded.MultiThreadedMyRedisClientHandler;
import org.example.async.singlethreaded.SingleThreadedMyRedisHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class RequestHandlerFactory {

    private static final String CONFIG_FILE = "config.properties";

    public static RequestHandler getHandler() {
        // Load configuration
        Properties properties = new Properties();

        System.out.println(RequestHandlerFactory.class.getClassLoader().getResource(CONFIG_FILE));

        ClassLoader classLoader = RequestHandlerFactory.class.getClassLoader();
        URL url = classLoader.getResource(".");
        System.out.println("Classpath: " + url);


        String handlerType;
        int port;
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            properties.load(fis);
            handlerType = properties.getProperty("handler.type");
            port = Integer.parseInt(properties.getProperty("server.port", "6379"));
        } catch (IOException e) {
            //throw new RuntimeException("Failed to load configuration file: " + CONFIG_FILE, e);
            handlerType = "single-threaded";
            port = 9000;
        }

        // Return handler based on configuration
        switch (handlerType) {
            case "single-threaded":
                return new SingleThreadedMyRedisHandler(port);
            case "multi-threaded":
                return new MultiThreadedMyRedisClientHandler(port);
            default:
                throw new IllegalArgumentException("Unsupported handler type: " + handlerType);
        }
    }


}
