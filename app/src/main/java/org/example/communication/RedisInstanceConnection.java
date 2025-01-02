package org.example.communication;


public class RedisInstanceConnection implements IConnection{
    final int position;
    String ipAddress;
    int port;

    public RedisInstanceConnection(int position) {
        this.position = position;
    }


    @Override
    public int getPosition() {
        return position;
    }
}
