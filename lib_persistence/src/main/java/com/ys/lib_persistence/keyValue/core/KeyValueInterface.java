package com.ys.lib_persistence.keyValue.core;

import android.content.Context;

import java.util.Map;

public interface KeyValueInterface {

    void init(Context context);

    boolean put(String key, Object value);

    boolean containsKey(String key);

    void clearAll();

    String[] getAllKeys();

    int getInt(String key, int defaultValue);

    long getLong(String key, long defaultValue);

    String getString(String key, String defaultValue);

    boolean getBoolean(String key, boolean defaultValue);

    float getFloat(String key, float defaultValue);

    byte[] getBytes(String key);

    boolean remove(String key);

    Map<String,Object> getConfigMap(int type);

    void sync();


}
