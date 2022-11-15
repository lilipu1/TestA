package com.ys.lib_persistence.keyValue.core;


import static com.ys.lib_persistence.keyValue.core.KeyValueStorage.KV_TYPE_B;
import static com.ys.lib_persistence.keyValue.core.KeyValueStorage.KV_TYPE_BT;
import static com.ys.lib_persistence.keyValue.core.KeyValueStorage.KV_TYPE_F;
import static com.ys.lib_persistence.keyValue.core.KeyValueStorage.KV_TYPE_I;
import static com.ys.lib_persistence.keyValue.core.KeyValueStorage.KV_TYPE_L;
import static com.ys.lib_persistence.keyValue.core.KeyValueStorage.KV_TYPE_S;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.ys.lib_persistence.keyValue.Constants;
import com.ys.lib_persistence.keyValue.mmkv.RemoteMMKV;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date: 2022/11/9 20:36
 * @Author: frank
 * @Email: 2370703174@qq.com
 * @Desc:
 */
public class ContentProviderImpl implements KeyValueInterface {

    RemoteMMKV prefs;

    public ContentProviderImpl(Context context) {
        init(context);
    }

    @Override
    public void init(Context context) {
        prefs = new RemoteMMKV(context, Constants.AUTHORITY);
    }

    @Override
    public boolean put(String key, Object value) {
        if (value instanceof Boolean) {
            key = getKey(key, KV_TYPE_B);
            if (!containsKey(key) || prefs.getBoolean(key, false) != (boolean) value) {
                return prefs.edit().putBoolean(key, (Boolean) value).commit();
            }
        }
        if (value instanceof Long) {
            key = getKey(key,KV_TYPE_L);
            if (prefs.getLong(key, -1000) != (long) value) {
                return prefs.edit().putLong(key, (long) value).commit();
            }
        }
        if (value instanceof Integer) {
            key = getKey(key,KV_TYPE_I);
            if (prefs.getInt(key, -1000) != (int) value) {
                return prefs.edit().putInt(key, (int) value).commit();
            }
        }
        if (value instanceof String) {
            key = getKey(key,KV_TYPE_S);
            if (!TextUtils.equals(prefs.getString(key, ""), (String) value)) {
                return prefs.edit().putString(key, (String) value).commit();
            }
        }
        if (value instanceof Float) {
            key = getKey(key,KV_TYPE_F);
            if (prefs.getFloat(key, -10000) != (float) value) {
                return prefs.edit().putFloat(key, (float) value).commit();
            }
        }
        if (value instanceof byte[]) {
            key = getKey(key,KV_TYPE_B);
            if (prefs.getBytes(key) != value) {
                return prefs.edit().putBytes(key, (byte[]) value).commit();
            }
        }
        return false;
    }

    private String getKey(String key, String suffix) {
        if (!key.endsWith(suffix)) {
           return key + suffix;
        }else {
            return key;
        }
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
            if (prefs.contains(mKey)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void clearAll() {
        prefs.edit().clear().commit();
    }

    @Override
    public String[] getAllKeys() {
        Map<String, ?> all = prefs.getAll();
        return (String[]) all.keySet().toArray();
    }

    @Override
    public int getInt(String key, int defaultValue) {
        return prefs.getInt(key+KV_TYPE_I,defaultValue);
    }

    @Override
    public long getLong(String key, long defaultValue) {
        return prefs.getLong(key+KV_TYPE_L,defaultValue);
    }

    @Override
    public String getString(String key, String defaultValue) {
        return prefs.getString(key+KV_TYPE_S,defaultValue);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return prefs.getBoolean(key+KV_TYPE_B,defaultValue);
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        return prefs.getFloat(key+KV_TYPE_F,defaultValue);
    }

    @Override
    public byte[] getBytes(String key) {
        return prefs.getBytes(key+KV_TYPE_BT);
    }

    @Override
    public boolean remove(String key) {
        return prefs.edit().remove(key).commit();
    }

    @Override
    public Map<String, Object> getConfigMap(int type) {
        String[] keys = getAllKeys();
        Map<String, Object> map = new HashMap<>();

        switch (type) {
            case 0:  //所有
                for (String key : keys) {
                    if (key.endsWith(KV_TYPE_B)) {
                        map.put(key, prefs.getBoolean(key,false));
                    } else if (key.endsWith(KV_TYPE_I)) {
                        map.put(key, prefs.getInt(key,0));
                    } else if (key.endsWith(KV_TYPE_L)) {
                        map.put(key, prefs.getLong(key,0));
                    } else if (key.endsWith(KV_TYPE_F)) {
                        map.put(key, prefs.getFloat(key,0f));
                    } else if (key.endsWith(KV_TYPE_S)) {
                        map.put(key, prefs.getString(key,""));
                    } else if (key.endsWith(KV_TYPE_BT)) {
                        map.put(key, prefs.getBytes(key));
                    } else {

                    }
                }
                break;
            case 1:  //boolean
                for (String key : keys) {
                    if (key.endsWith(KV_TYPE_B)) {
                        map.put(key, prefs.getBoolean(key,false));
                    }
                }
                break;
            case 2:  //int
                for (String key : keys) {
                    if (key.endsWith(KV_TYPE_I)) {
                        map.put(key, prefs.getInt(key,0));
                    }
                }
                break;
            case 3:  //long
                for (String key : keys) {
                    if (key.endsWith(KV_TYPE_L)) {
                        map.put(key, prefs.getLong(key,0));
                    }
                }
                break;
            case 4:  //float
                for (String key : keys) {
                    if (key.endsWith(KV_TYPE_F)) {
                        map.put(key,prefs.getFloat(key,0));
                    }
                }
                break;
            case 5:  //String
                for (String key : keys) {
                    if (key.endsWith(KV_TYPE_S)) {
                        map.put(key, prefs.getString(key,""));
                    }
                }
                break;
            case 6:  //byte[]
                for (String key : keys) {
                    if (key.endsWith(KV_TYPE_BT)) {
                        map.put(key, prefs.getBytes(key));
                    }
                }
                break;
            default:
                break;
        }

        return map;
    }

    @Override
    public void sync() {

    }
}
