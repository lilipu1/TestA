package com.ys.lib_persistence.keyValue.sql;

import static com.ys.lib_persistence.keyValue.core.MMKVImp.*;

import android.content.Context;

import com.ys.lib_persistence.R;
import com.ys.lib_persistence.keyValue.core.ContentProviderImpl;
import com.ys.lib_persistence.keyValue.core.KeyValueInterface;
import com.ys.lib_persistence.keyValue.sql.utils.ProcessUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @date: 2022/11/15 0015
 * @author: frank
 * @email: 2370703174@qq.com
 * @desc:
 */
public class SqlProviderImpl implements KeyValueInterface {

    private RemoteSql remoteSql;

    public SqlProviderImpl(Context context) {
        init(context);
    }

    @Override
    public void init(Context context) {
        remoteSql = new RemoteSql(context);
    }

    @Override
    public boolean put(String key, Object value) {
        return remoteSql.put(key, value);
    }

    @Override
    public boolean containsKey(String key) {
        return remoteSql.contains(key);
    }

    @Override
    public void clearAll() {
        Map<String, ?> all = remoteSql.getAll();
        for (String key : all.keySet()) {
            remoteSql.remove(key);
        }
    }

    @Override
    public String[] getAllKeys() {
        return (String[]) remoteSql.getAll().keySet().toArray();
    }

    @Override
    public int getInt(String key, int defaultValue) {
        return remoteSql.getInt(key, defaultValue);
    }

    @Override
    public long getLong(String key, long defaultValue) {
        return remoteSql.getLong(key, defaultValue);
    }

    @Override
    public String getString(String key, String defaultValue) {
        return remoteSql.getString(key, defaultValue);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return remoteSql.getBoolean(key, defaultValue);
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        return remoteSql.getFloat(key, defaultValue);
    }

    @Override
    public byte[] getBytes(String key) {
        return remoteSql.getBytes(key);
    }

    @Override
    public boolean remove(String key) {
        return remoteSql.remove(key);
    }

    @Override
    public Map<String, Object> getConfigMap(int type) {
        String[] keys = getAllKeys();
        Map<String, Object> map = new HashMap<>();

        switch (type) {
            case 0:  //所有
                for (String key : keys) {
                    if (key.endsWith(KV_TYPE_B)) {
                        map.put(key, remoteSql.getBoolean(key, false));
                    } else if (key.endsWith(KV_TYPE_I)) {
                        map.put(key, remoteSql.getInt(key, 0));
                    } else if (key.endsWith(KV_TYPE_L)) {
                        map.put(key, remoteSql.getLong(key, 0));
                    } else if (key.endsWith(KV_TYPE_F)) {
                        map.put(key, remoteSql.getFloat(key, 0f));
                    } else if (key.endsWith(KV_TYPE_S)) {
                        map.put(key, remoteSql.getString(key, ""));
                    } else if (key.endsWith(KV_TYPE_BT)) {
                        map.put(key, remoteSql.getBytes(key));
                    } else {

                    }
                }
                break;
            case 1:  //boolean
                for (String key : keys) {
                    if (key.endsWith(KV_TYPE_B)) {
                        map.put(key, remoteSql.getString(key, ""));
                    }
                }
                break;
            case 2:  //int
                for (String key : keys) {
                    if (key.endsWith(KV_TYPE_I)) {
                        map.put(key, remoteSql.getInt(key, 0));
                    }
                }
                break;
            case 3:  //long
                for (String key : keys) {
                    if (key.endsWith(KV_TYPE_L)) {
                        map.put(key, remoteSql.getLong(key, 0));
                    }
                }
                break;
            case 4:  //float
                for (String key : keys) {
                    if (key.endsWith(KV_TYPE_F)) {
                        map.put(key, remoteSql.getFloat(key, 0f));
                    }
                }
                break;
            case 5:  //String
                for (String key : keys) {
                    if (key.endsWith(KV_TYPE_S)) {
                        map.put(key, remoteSql.getString(key, ""));
                    }
                }
                break;
            case 6:  //byte[]
                for (String key : keys) {
                    if (key.endsWith(KV_TYPE_BT)) {
                        map.put(key, remoteSql.getBytes(key));
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
