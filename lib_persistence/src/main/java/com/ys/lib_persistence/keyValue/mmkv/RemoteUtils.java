package com.ys.lib_persistence.keyValue.mmkv;

import java.util.HashSet;
import java.util.Set;

 public final class RemoteUtils {
    private RemoteUtils() {}


    @SuppressWarnings("unchecked")
    public static Set<String> castStringSet(Object value) {
        return (Set<String>)value;
    }

    public static int getPreferenceType(Object value) {
        if (value == null) return MMKVContract.TYPE_NULL;
        if (value instanceof String) return MMKVContract.TYPE_STRING;
        if (value instanceof Set<?>) return MMKVContract.TYPE_STRING_SET;
        if (value instanceof Integer) return MMKVContract.TYPE_INT;
        if (value instanceof Long) return MMKVContract.TYPE_LONG;
        if (value instanceof Float) return MMKVContract.TYPE_FLOAT;
        if (value instanceof Boolean) return MMKVContract.TYPE_BOOLEAN;
        if (value instanceof byte[]) return MMKVContract.TYPE_BYTE;
        throw new AssertionError("Unknown preference type: " + value.getClass());
    }


    public static Object serializeOutput(Object value) {
        if (value instanceof Boolean) {
            return serializeBoolean((Boolean)value);
        } else if (value instanceof Set<?>) {
            return serializeStringSet(castStringSet(value));
        } else {
            return value;
        }
    }


    public static Object deserializeInput(Object value, int expectedType) {
        if (expectedType == MMKVContract.TYPE_NULL) {
            if (value != null) {
                throw new IllegalArgumentException("Expected null, got non-null value");
            } else {
                return null;
            }
        }
        try {
            switch (expectedType) {
            case MMKVContract.TYPE_STRING:
                return (String)value;
            case MMKVContract.TYPE_STRING_SET:
                return deserializeStringSet((String)value);
            case MMKVContract.TYPE_INT:
                return (Integer)value;
            case MMKVContract.TYPE_LONG:
                return (Long)value;
            case MMKVContract.TYPE_FLOAT:
                return (Float)value;
            case MMKVContract.TYPE_BOOLEAN:
                return deserializeBoolean(value);
            }
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Expected type " + expectedType + ", got " + value.getClass(), e);
        }
        throw new IllegalArgumentException("Unknown type: " + expectedType);
    }


    private static Integer serializeBoolean(Boolean value) {
        if (value == null) {
            return null;
        } else {
            return value ? 1 : 0;
        }
    }


    private static Boolean deserializeBoolean(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof Boolean) {
            return (Boolean)value;
        } else {
            return (Integer)value != 0;
        }
    }


    public static String serializeStringSet(Set<String> stringSet) {
        if (stringSet == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (String s : stringSet) {
            sb.append(s.replace("\\", "\\\\").replace(";", "\\;"));
            sb.append(';');
        }
        return sb.toString();
    }

    public static Set<String> deserializeStringSet(String serializedString) {
        if (serializedString == null) {
            return null;
        }
        HashSet<String> stringSet = new HashSet<String>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < serializedString.length(); ++i) {
            char c = serializedString.charAt(i);
            if (c == '\\') {
                char next = serializedString.charAt(++i);
                sb.append(next);
            } else if (c == ';') {
                stringSet.add(sb.toString());
                sb.delete(0, sb.length());
            } else {
                sb.append(c);
            }
        }

        if (sb.length() != 0) {
            throw new IllegalArgumentException("Serialized string set contains trailing chars");
        }

        return stringSet;
    }
}
