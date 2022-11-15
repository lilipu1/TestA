package com.ys.lib_persistence.keyValue.mmkv;


public final class MMKVContract {
    public static final String COLUMN_KEY = "key";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_VALUE = "value";
    public static final String[] COLUMN_ALL = {
        MMKVContract.COLUMN_KEY,
        MMKVContract.COLUMN_TYPE,
        MMKVContract.COLUMN_VALUE
    };

    public static final int TYPE_NULL = 0;
    public static final int TYPE_STRING = 1;
    public static final int TYPE_STRING_SET = 2;
    public static final int TYPE_INT = 3;
    public static final int TYPE_LONG = 4;
    public static final int TYPE_FLOAT = 5;
    public static final int TYPE_BOOLEAN = 6;
    public static final int TYPE_BYTE = 7;

    private MMKVContract() {}
}
