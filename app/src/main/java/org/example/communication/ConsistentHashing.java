package org.example.communication;

import java.util.List;

public class ConsistentHashing implements IRouteStrategy{


    private final List<IConnection> consistentHashRing;

    public ConsistentHashing(List<IConnection> consistentHashRing ) {
        this.consistentHashRing = consistentHashRing;
    }

    @Override
    public Route route( IRequest request ) {

        int requestedPosition = request.getPosition();
        Route ans = null;
        for( IConnection connection : consistentHashRing ){
            if( requestedPosition > connection.getPosition() ){
                continue;
            }else{
                ans = new Route( connection );
                break;
            }

        }
        if( ans == null ){
            //return the first connection on the ring
            throw new RuntimeException( "No suitable Redis instance found for this Rewuest");
        }

        return ans;
    }
}
