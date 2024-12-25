package org.example.async;

import org.example.async.multithreaded.MultiThreadedMyRedisClientHandler;

import static org.example.config.ApplicationConstant.PORT;

public class RequestHandlerFactory {
    public static RequestHandler getHandler(){

        //Based on the Configuration create the required handler and return to client


        return new MultiThreadedMyRedisClientHandler( PORT );
    }
}
