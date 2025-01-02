package org.example.communication;

public class RedisClientRequest implements IRequest {

    private final int userId;
    String ipAddress;

    public RedisClientRequest(int userId) {
        this.userId = userId;
    }

    @Override
    public int getPosition() {
        return userId;
    }
}
