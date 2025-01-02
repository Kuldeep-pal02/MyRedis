package org.example.communication;

import java.util.LinkedList;
import java.util.List;

public class CommunicationDriverClass {
    public static void main(String[] args) {
        init();
    }


    private static void init(){
        List<IConnection> consistentHashRing = new LinkedList<>();

        consistentHashRing.add( new RedisInstanceConnection( 1 ) );
        consistentHashRing.add( new RedisInstanceConnection( 50 ) );
        consistentHashRing.add( new RedisInstanceConnection( 300 ) );
        consistentHashRing.add( new RedisInstanceConnection( 600 ) );
        consistentHashRing.add( new RedisInstanceConnection( 1000 ) );


        IRouteStrategy routeStrategy = new ConsistentHashing(consistentHashRing);
        System.out.println( " For client request 45 , service assinged is : "+routeStrategy.route( new RedisClientRequest( 45 ) ).getConnection().getPosition() ) ;
        System.out.println( " For client request 51 , service assinged is : "+routeStrategy.route( new RedisClientRequest( 51 ) ).getConnection().getPosition() ) ;

    }
}
