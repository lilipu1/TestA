package com.ys.lib_persistence.keyValue.core;


import android.content.Context;

import androidx.lifecycle.Observer;

import com.jeremyliao.liveeventbus.LiveEventBus;
import com.ys.lib_persistence.keyValue.bean.ConfigBean;
import com.ys.lib_persistence.keyValue.sql.SqlProviderImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class KeyValueStorage {
    public final static String KV_TYPE_B = "@b";
    public final static String KV_TYPE_I = "@i";
    public final static String KV_TYPE_L = "@l";
    public final static String KV_TYPE_F = "@f";
    public final static String KV_TYPE_S = "@s";
    public final static String KV_TYPE_BT = "@bt";

    public static KeyValueInterface instance;

    private static volatile boolean isInit = false;

    public static boolean isInit() {
        return isInit;
    }

    public static void init(Context context, String packageName, String programId, String programName, String programFlag
            , String programRemark) {
        if (instance == null) {
            synchronized (KeyValueStorage.class) {
                if (instance == null) {
                    instance = new SqlProviderImpl(context);
                    isInit = true;
                }
            }
        }
        put(packageName + YSKey.SUFFIX_PACKAGE_PROGRAM_ID, programId);
        put(packageName + YSKey.SUFFIX_PACKAGE_PROGRAM_NAME, programName);
        put(packageName + YSKey.SUFFIX_PACKAGE_PROGRAM_FLAG, programFlag);
        put(packageName + YSKey.SUFFIX_PACKAGE_PROGRAM_REMARK, programRemark);
    }

    public static boolean putWithObserver(String key, Object value) {
        if (instance.put(key, value)) {
            LiveEventBus.get(ConfigBean.class).postAcrossApp(new ConfigBean(key, value));
        }
        return true;
    }

    public static boolean put(String key, Object value) {
        boolean result = instance.put(key, value);
        return result;
    }

    public static void addObserver(Observer<ConfigBean> observer) {
        LiveEventBus.get(ConfigBean.class).observeForever(observer);
    }

    public static void removeObserver(Observer<ConfigBean> observer) {
        LiveEventBus.get(ConfigBean.class).removeObserver(observer);
    }

    public static int getInt(String key, int defaultValue) {
        return instance.getInt(key, defaultValue);
    }

    public static long getLong(String key, long defaultValue) {
        return instance.getLong(key, defaultValue);
    }


    public static String getString(String key, String defaultValue) {
        return instance.getString(key, defaultValue);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return instance.getBoolean(key, defaultValue);
    }

    public static byte[] getBytes(String key) {
        return instance.getBytes(key);
    }

    public static float getFloat(String key, float defaultValue) {
        return instance.getFloat(key, defaultValue);
    }

    public static boolean containsKey(String key) {
        return instance.containsKey(key);
    }

    public static String[] getAllKeys() {
        return instance.getAllKeys();
    }

    public static List<String> getAllKeysContainSuffixExceptAT(String suffix) {
        String[] keys = getAllKeys();
        if ((null == keys)) {
            return null;
        }

        List<String> retKeys = new ArrayList<>();
        for (String key : keys) {
            int indexA = key.indexOf("@");
            String keyTmp = key.substring(0, indexA);
            if (keyTmp.endsWith(suffix)) {
                retKeys.add(keyTmp);
            }
        }
        return retKeys;
    }

    public static List<String> getAllKeysContainSuffix(String suffix) {
        String[] keys = instance.getAllKeys();
        if ((null == keys)) {
            return null;
        }
        List<String> retKeys = new ArrayList<>();
        for (String key : keys) {
            if (key.endsWith(suffix)) {
                retKeys.add(key);
            }
        }
        return retKeys;
    }

    public static void clearAllMemoryCache() {
        if (instance instanceof MMKVImp) {
            ((MMKVImp) instance).clearMemoryCache();
        }
    }

    public static List<String> getAllKeysContainPrefixExceptAT(String prefix) {
        String[] keys = instance.getAllKeys();
        if ((null == keys)) {
            return null;
        }
        List<String> retKeys = new ArrayList<>();
        for (String key : keys) {
            if (key.startsWith(prefix)) {
                int indexA = key.indexOf("@");
                String keyTmp = key.substring(0, indexA);
                retKeys.add(keyTmp);
            }
        }
        return retKeys;
    }

    public static List<String> getAllKeysContainPrefix(String prefix) {
        String[] keys = instance.getAllKeys();
        if ((null == keys)) {
            return null;
        }
        List<String> retKeys = new ArrayList<>();
        for (String key : keys) {
            if (key.startsWith(prefix)) {
                retKeys.add(key);
            }
        }
        return retKeys;
    }

    public static List<String> getAllKeysContainData(String data) {
        String[] keys = instance.getAllKeys();
        if ((null == keys)) {
            return null;
        }
        List<String> retKeys = new ArrayList<>();
        for (String key : keys) {
            if (key.contains(data)) {
                retKeys.add(key);
            }
        }
        return retKeys;
    }

    public static void clearAll() {
        instance.clearAll();
    }

    public static boolean remove(String key) {
        return instance.remove(key);
    }

    public static Map<String, Object> getConfigMap(int type) {
        return instance.getConfigMap(type);
    }

    public static void sync() {
    }
}
