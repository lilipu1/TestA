package com.ys.lib_persistence.keyValue.core;

import android.content.Context;
import android.text.TextUtils;

import com.tencent.mmkv.MMKV;
import com.tencent.mmkv.MMKVHandler;
import com.tencent.mmkv.MMKVLogLevel;
import com.tencent.mmkv.MMKVRecoverStrategic;

import java.util.HashMap;
import java.util.Map;

public class MMKVImp implements KeyValueInterface, MMKVHandler {

    private MMKV mmkv;

    private static final String MMKV_ID = "YS_KEY_VALUE";
    public final static String DATA_AT = "@";
    public final static String KV_TYPE_B = "@b";
    public final static String KV_TYPE_I = "@i";
    public final static String KV_TYPE_L = "@l";
    public final static String KV_TYPE_F = "@f";
    public final static String KV_TYPE_S = "@s";
    public final static String KV_TYPE_BT = "@bt";
    private static final String ROOT_DIR = "/mnt/sdcard";

    @Override
    public void init(Context context) {
//        MMKV.initialize(context, Environment.getExternalStorageDirectory() + "/YSConfig");
        MMKV.initialize(context);
    }

    public MMKVImp(Context context) {
        init(context);
        mmkv = MMKV.mmkvWithID(MMKV_ID, MMKV.MULTI_PROCESS_MODE);

    }

    @Override
    public boolean put(String key, Object value) {
        if (value instanceof Boolean) {
            if (!containsKey(key) || mmkv.decodeBool(key + KV_TYPE_B) != (boolean) value) {
                mmkv.encode(key + KV_TYPE_B, (boolean) value);
                sync();
                return true;
            }
        }
        if (value instanceof Long) {
            if (mmkv.decodeLong(key + KV_TYPE_L, -1000) != (long) value) {
                mmkv.encode(key + KV_TYPE_L, (long) value);
                sync();
                return true;
            }
        }
        if (value instanceof Integer) {
            if (mmkv.decodeInt(key + KV_TYPE_I, -1000) != (int) value) {
                mmkv.encode(key + KV_TYPE_I, (int) value);
                sync();
                return true;
            }
        }
        if (value instanceof String) {
            if (!TextUtils.equals(mmkv.decodeString(key + KV_TYPE_S), (String) value)) {
                mmkv.encode(key + KV_TYPE_S, (String) value);
                sync();
                return true;
            }
        }
        if (value instanceof Float) {
            if (mmkv.decodeFloat(key + KV_TYPE_F, -10000) != (float) value) {
                mmkv.encode(key + KV_TYPE_F, (float) value);
                sync();
                return true;
            }
        }
        if (value instanceof byte[]) {
            if (mmkv.decodeBytes(key + KV_TYPE_BT) != (byte[]) value) {
                mmkv.encode(key + KV_TYPE_BT, (byte[]) value);
                sync();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsKey(String key) {
        String[] keys = new String[]{
                key + KV_TYPE_B,
                key + KV_TYPE_L,
                key + KV_TYPE_I,
                key + KV_TYPE_S,
                key + KV_TYPE_F,
                key + KV_TYPE_BT
        };
        for (String mKey : keys) {
            if (mmkv.containsKey(mKey)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void clearAll() {
        mmkv.clearMemoryCache();
        mmkv.clearAll();
    }

    @Override
    public int getInt(String key, int defaultValue) {
        return mmkv.decodeInt(key + KV_TYPE_I, defaultValue);
    }

    @Override
    public long getLong(String key, long defaultValue) {
        return mmkv.decodeLong(key + KV_TYPE_L, defaultValue);
    }


    @Override
    public String getString(String key, String defaultValue) {
        return mmkv.decodeString(key + KV_TYPE_S, defaultValue);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return mmkv.decodeBool(key + KV_TYPE_B, defaultValue);
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        return mmkv.decodeFloat(key + KV_TYPE_F, defaultValue);
    }


    @Override
    public byte[] getBytes(String key) {
        return mmkv.decodeBytes(key + KV_TYPE_BT);
    }

    @Override
    public boolean remove(String key) {
         mmkv.removeValueForKey(key);
         return true;
    }

    @Override
    public Map<String, Object> getConfigMap(int type) {
        String[] keys = getAllKeys();
        Map<String, Object> map = new HashMap<>();

        switch (type) {
            case 0:  //所有
                for (String key:keys) {
                    if(key.endsWith(KV_TYPE_B)) {
                        map.put(key,mmkv.decodeBool(key));
                    } else if(key.endsWith(KV_TYPE_I)) {
                        map.put(key,mmkv.decodeInt(key));
                    } else if(key.endsWith(KV_TYPE_L)) {
                        map.put(key,mmkv.decodeLong(key));
                    } else if(key.endsWith(KV_TYPE_F)) {
                        map.put(key,mmkv.decodeFloat(key));
                    } else if(key.endsWith(KV_TYPE_S)) {
                        map.put(key,mmkv.decodeString(key));
                    } else if(key.endsWith(KV_TYPE_BT)) {
                        map.put(key,mmkv.decodeBytes(key));
                    } else {

                    }
                }
                break;
            case 1:  //boolean
                for (String key:keys) {
                    if ( key.endsWith(KV_TYPE_B) ) {
                        map.put(key,mmkv.decodeBool(key));
                    }
                }
                break;
            case 2:  //int
                for (String key:keys) {
                    if ( key.endsWith(KV_TYPE_I) ) {
                        map.put(key,mmkv.decodeInt(key));
                    }
                }
                break;
            case 3:  //long
                for (String key:keys) {
                    if ( key.endsWith(KV_TYPE_L) ) {
                        map.put(key,mmkv.decodeLong(key));
                    }
                }
                break;
            case 4:  //float
                for (String key:keys) {
                    if ( key.endsWith(KV_TYPE_F) ) {
                        map.put(key,mmkv.decodeFloat(key));
                    }
                }
                break;
            case 5:  //String
                for (String key:keys) {
                    if ( key.endsWith(KV_TYPE_S) ) {
                        map.put(key,mmkv.decodeString(key));
                    }
                }
                break;
            case 6:  //byte[]
                for (String key:keys) {
                    if ( key.endsWith(KV_TYPE_BT) ) {
                        map.put(key,mmkv.decodeBytes(key));
                    }
                }
                break;
            default:
                break;
        }

        return map;
    }

    @Override
    public String[] getAllKeys() {
        return mmkv.allKeys();
    }

    @Override
    public MMKVRecoverStrategic onMMKVCRCCheckFail(String mmapID) {
        return null;
    }

    @Override
    public MMKVRecoverStrategic onMMKVFileLengthError(String mmapID) {
        return null;
    }

    @Override
    public boolean wantLogRedirecting() {
        return true;
    }

    @Override
    public void mmkvLog(MMKVLogLevel level, String file, int line, String function, String message) {
        switch (level) {
            case LevelDebug:
                break;
            case LevelInfo:
                break;
            case LevelWarning:
                break;
            case LevelError:
                break;
            case LevelNone:
                break;
        }
    }

    @Override
    public void sync() {
        mmkv.sync();
    }

    public void clearMemoryCache() {
        mmkv.clearMemoryCache();
    }
}
