package com.task.test.exp;

public class ServerBadRequestException extends RuntimeException {
    public ServerBadRequestException(String message) {
        super(message);
    }
}
