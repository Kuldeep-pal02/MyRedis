package org.example.communication;

public class Route {
    private IConnection connection;
    private String metadata;

    public Route( IConnection connection ){
        this.connection = connection;
    }
    IConnection getConnection(){
        return this.connection;
    }
}
