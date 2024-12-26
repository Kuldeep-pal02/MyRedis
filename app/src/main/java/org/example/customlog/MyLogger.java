package org.example.customlog;

public interface MyLogger<T> {
    void logMessage( T t );
}
