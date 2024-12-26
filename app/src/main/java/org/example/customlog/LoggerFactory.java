package org.example.customlog;


public class LoggerFactory {

    public static MyLogger INSTANCE = new MyLogger() {
        @Override
        public void logMessage(Object o) {
            System.out.println( o.toString() );
        }
    };

    public static MyLogger getLoggerInstance(){

        return INSTANCE;
    }
}
