package org.podpage.ijs;

import com.google.gson.Gson;

public class SimpleClassParser {
    private static Class[] simpleClasses = new Class[]{String.class, long.class, Long.class, int.class, Integer.class, short.class, Short.class, byte.class, Byte.class, boolean.class, Boolean.class};

    public static <T> T parse(Class<T> typeClass, Gson gson, Object value) {
        for (Class simpleClass : simpleClasses) {
            if (typeClass.equals(simpleClass)) {
                return (T) value;
            }
        }
        return gson.fromJson(value.toString(), typeClass);
    }
}
