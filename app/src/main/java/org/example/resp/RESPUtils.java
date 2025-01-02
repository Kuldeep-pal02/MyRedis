package org.example.resp;

import org.example.datastore.CustomMyRedisObject;

import java.util.ArrayList;
import java.util.List;

public class RESPUtils {

    // Encode a Simple String
    public static String encodeSimpleString(String value) {
        if (value == null) {
            return "$-1\r\n"; // Null Bulk String
        }
        return "+" + value + "\r\n";
    }

    // Encode an Error
    public static String encodeError(String message) {
        return "-" + message + "\r\n";
    }

    // Encode an Integer
    public static String encodeInteger(long value) {
        return ":" + value + "\r\n";
    }

    // Encode a Bulk String
    public static String encodeBulkString(String value) {
        if (value == null) {
            return "$-1\r\n"; // Null Bulk String
        }
        return "$" + value.length() + "\r\n" + value + "\r\n";
    }

    // Encode an Array
    public static String encodeArray(List<String> elements) {
        if (elements == null || elements.isEmpty()) {
            return "*-1\r\n"; // Null Array
        }
        StringBuilder sb = new StringBuilder();
        sb.append("*").append(elements.size()).append("\r\n");
        for (String element : elements) {
            sb.append(encodeBulkString(element));
        }
        return sb.toString();
    }

    //Encode custom object
    public static String encodeCustomObject(CustomMyRedisObject obj) {
        if (obj == null) {
            return "$-1\r\n"; // Null response
        }

        // Encode based on the type field
        switch (obj.getType()) {
            case 1: // Integer
                return encodeInteger((Long) obj.getObj());
            case 2: // String
                return encodeBulkString((String) obj.getObj());
            default:
                // Default to treating as a string if type is unknown
                return encodeBulkString(obj.getObj().toString());
        }
    }

    // Decode RESP into arguments (Array of Bulk Strings)
    public static String[] decodeRESP(String resp) {
        if (resp == null || resp.isEmpty()) {
            return new String[0];
        }

        String[] lines = resp.split("\r\n");
        if (lines[0].startsWith("*")) {
            int numElements = Integer.parseInt(lines[0].substring(1));
            List<String> elements = new ArrayList<>();
            int index = 1;
            for (int i = 0; i < numElements; i++) {
                if (lines[index].startsWith("$")) {
                    int length = Integer.parseInt(lines[index].substring(1));
                    index++; // Move to the actual string
                    elements.add(lines[index]);
                    index++; // Move to the next length or end
                }
            }
            return elements.toArray(new String[0]);
        }
        return new String[0];
    }
}
