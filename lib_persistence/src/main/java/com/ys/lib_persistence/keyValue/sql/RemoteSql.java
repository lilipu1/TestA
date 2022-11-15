package com.ys.lib_persistence.keyValue.sql;

import static com.ys.lib_persistence.keyValue.Constants.AUTHORITY;
import static com.ys.lib_persistence.keyValue.core.MMKVImp.*;
import static com.ys.lib_persistence.keyValue.sql.KeyValueProvider.*;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.TelephonyNetworkSpecifier;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.room.RoomDatabase;

import com.ys.lib_persistence.keyValue.core.MMKVImp;
import com.ys.lib_persistence.keyValue.mmkv.MMKVContract;
import com.ys.lib_persistence.keyValue.mmkv.RemoteMMVAccessException;
import com.ys.lib_persistence.keyValue.mmkv.RemoteUtils;
import com.ys.lib_persistence.keyValue.sql.dao.KeyValueDao;
import com.ys.lib_persistence.keyValue.sql.entity.KV_Base;
import com.ys.lib_persistence.keyValue.sql.entity.KV_Byte;
import com.ys.lib_persistence.keyValue.sql.entity.KV_Float;
import com.ys.lib_persistence.keyValue.sql.entity.KV_INT;
import com.ys.lib_persistence.keyValue.sql.entity.KV_Long;
import com.ys.lib_persistence.keyValue.sql.entity.KV_String;
import com.ys.lib_persistence.keyValue.sql.utils.ProcessUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Date: 2022/11/11 16:18
 * @Author: frank
 * @Email: 2370703174@qq.com
 * @Desc:
 */
public class RemoteSql implements SharedPreferences {

    private static final Uri NOTIFY_URI = Uri.parse("content://" + AUTHORITY + "/keyValue");
    private static final String TAG = RemoteSql.class.getSimpleName();
    private final Context mContext;
    private final String MAIN_PROCESS = "com.tcn.vending:com";
    private final KeyValueDao keyValueDao;


    public RemoteSql(Context mContext) {
        this.mContext = mContext;
        keyValueDao = KeyValueDatabase.getInstance(mContext).getKeyValueDao();
    }


    public boolean put(String key, Object value) {
        if (value instanceof Boolean) {
            boolean boolValue = (boolean) value;
            key += KV_TYPE_B;
            if (MAIN_PROCESS.equals(ProcessUtil.getCurrentProcessName(mContext))) {
                return keyValueDao.put(new KV_INT(key, boolValue ? 1 : 0)) > 0;
            }
            ContentValues values = new ContentValues();
            values.put(key, boolValue ? 1 : 0);
            Uri uri = NOTIFY_URI.buildUpon().appendPath(getPathByKey(key)).appendPath(key).build();
            return insert(uri, values);
        }
        if (value instanceof Long) {
            long longValue = (long) value;
            key += KV_TYPE_L;
            if (MAIN_PROCESS.equals(ProcessUtil.getCurrentProcessName(mContext))) {
                return keyValueDao.put(new KV_Long(key, longValue)) > 0;
            }
            ContentValues values = new ContentValues();
            values.put(key, longValue);
            Uri uri = NOTIFY_URI.buildUpon().appendPath(getPathByKey(key)).appendPath(key).build();
            return insert(uri, values);
        }
        if (value instanceof Integer) {
            int intValue = (int) value;
            key += KV_TYPE_I;
            if (MAIN_PROCESS.equals(ProcessUtil.getCurrentProcessName(mContext))) {
                return keyValueDao.put(new KV_INT(key, intValue)) > 0;
            }
            ContentValues values = new ContentValues();
            values.put(key, intValue);
            Uri uri = NOTIFY_URI.buildUpon().appendPath(getPathByKey(key)).appendPath(key).build();
            return insert(uri, values);
        }
        if (value instanceof String) {
            String strValue = (String) value;
            key += KV_TYPE_S;
            if (MAIN_PROCESS.equals(ProcessUtil.getCurrentProcessName(mContext))) {
                return keyValueDao.put(new KV_String(key, strValue)) > 0;
            }
            ContentValues values = new ContentValues();
            values.put(key, strValue);
            Uri uri = NOTIFY_URI.buildUpon().appendPath(getPathByKey(key)).appendPath(key).build();
            return insert(uri, values);
        }
        if (value instanceof Float) {
            float floatValue = (float) value;
            key += KV_TYPE_F;
            if (MAIN_PROCESS.equals(ProcessUtil.getCurrentProcessName(mContext))) {
                return keyValueDao.put(new KV_Float(key, floatValue)) > 0;
            }
            ContentValues values = new ContentValues();
            values.put(key, floatValue);
            Uri uri = NOTIFY_URI.buildUpon().appendPath(getPathByKey(key)).appendPath(key).build();
            return insert(uri, values);
        }
        if (value instanceof byte[]) {
            byte[] byteValue = (byte[]) value;
            key += KV_TYPE_BT;
            if (MAIN_PROCESS.equals(ProcessUtil.getCurrentProcessName(mContext))) {
                return keyValueDao.put(new KV_Byte(key, byteValue)) > 0;
            }
            ContentValues values = new ContentValues();
            values.put(key, byteValue);
            Uri uri = NOTIFY_URI.buildUpon().appendPath(getPathByKey(key)).appendPath(key).build();
            return insert(uri, values);
        }
        return false;
    }

    private boolean insert(Uri uri, ContentValues values) {
        Uri result;
        try {
            result = mContext.getContentResolver().insert(uri, values);
        } catch (Exception e) {
            for (String s : values.keySet()) {
                Log.e(TAG, s + "  插入错误：" + e);
            }
            return false;
        }
        return Integer.parseInt(result.getPathSegments().get(1)) > 0;
    }


    @Override
    public Map<String, ?> getAll() {
        if (MAIN_PROCESS.equals(ProcessUtil.getCurrentProcessName(mContext))){
            Map<String,Object> allMap = new HashMap<>();
            List<KV_Byte> allByte = keyValueDao.getAllByte();
            List<KV_Float> allFloat = keyValueDao.getAllFloat();
            List<KV_INT> allInt = keyValueDao.getAllInt();
            List<KV_Long> allLong = keyValueDao.getAllLong();
            List<KV_String> allString = keyValueDao.getAllString();
            List<KV_Base> allItems = new ArrayList<>();
            allItems.addAll(allByte);
            allItems.addAll(allFloat);
            allItems.addAll(allInt);
            allItems.addAll(allLong);
            allItems.addAll(allString);
            for (KV_Base allItem : allItems) {
                allMap.put(allItem.key,allItem.value);
            }
            return allMap;
        }
        return queryAll();
    }

    @Nullable
    @Override
    public String getString(String key, @Nullable String defValue) {
        return (String) querySingle(key + KV_TYPE_S, defValue);
    }

    @Nullable
    @Override
    public Set<String> getStringSet(String key, @Nullable Set<String> defValues) {
        Log.e(TAG, "不支持StringSet类型");
        return null;
    }

    @Override
    public int getInt(String key, int defValue) {
        if (!key.endsWith(KV_TYPE_I)) {
            key += KV_TYPE_I;
        }
        if (MAIN_PROCESS.equals(ProcessUtil.getCurrentProcessName(mContext))) {
            if (keyValueDao.containsKeyInt(key) == 0) {
                return defValue;
            }
            return keyValueDao.getInt(key);
        } else {
            return (int) querySingle(key, defValue);
        }
    }

    @Override
    public long getLong(String key, long defValue) {
        if (!key.endsWith(KV_TYPE_L)) {
            key += KV_TYPE_L;
        }
        if (MAIN_PROCESS.equals(ProcessUtil.getCurrentProcessName(mContext))) {
            if (keyValueDao.containsKeyLong(key) == 0) {
                return defValue;
            }
            return keyValueDao.getLong(key);
        } else {
            return (long) querySingle(key, defValue);
        }
    }

    @Override
    public float getFloat(String key, float defValue) {
        if (!key.endsWith(KV_TYPE_F)) {
            key += KV_TYPE_F;
        }
        if (MAIN_PROCESS.equals(ProcessUtil.getCurrentProcessName(mContext))) {
            if (keyValueDao.containsKeyFloat(key) == 0) {
                return defValue;
            }
            return keyValueDao.getFloat(key);
        } else {
            return (float) querySingle(key, defValue);
        }
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        if (!key.endsWith(KV_TYPE_B)) {
            key += KV_TYPE_B;
        }
        if (MAIN_PROCESS.equals(ProcessUtil.getCurrentProcessName(mContext))) {
            if (keyValueDao.containsKeyInt(key) == 0) {
                return defValue;
            }
            return keyValueDao.getInt(key) == 1;
        } else {
            return (boolean) querySingle(key, defValue);
        }
    }

    public byte[] getBytes(String key) {
        if (!key.endsWith(KV_TYPE_BT)) {
            key += KV_TYPE_BT;
        }
        if (MAIN_PROCESS.equals(ProcessUtil.getCurrentProcessName(mContext))) {
            if (keyValueDao.containsKeyByte(key) == 0) {
                return new byte[]{};
            }
            return keyValueDao.getBytes(key);
        } else {
            return (byte[]) querySingle(key, new byte[]{});
        }
    }

    @Override
    public boolean contains(String key) {
        if (MAIN_PROCESS.equals(ProcessUtil.getCurrentProcessName(mContext))) {
            return (keyValueDao.containsKeyInt(key + KV_TYPE_B) + keyValueDao.containsKeyByte(key + KV_TYPE_BT)
                    + keyValueDao.containsKeyString(key + KV_TYPE_S) + keyValueDao.containsKeyLong(key + KV_TYPE_L)
                    + keyValueDao.containsKeyFloat(key + KV_TYPE_F) + keyValueDao.containsKeyInt(key + KV_TYPE_I)) > 0;
        }
        Uri uri = NOTIFY_URI.buildUpon().appendPath(PATH_ALL).appendPath(key).build();
        String[] columns = {"type", "value"};
        Cursor cursor = query(uri, columns);
        try {
            if (cursor == null || !cursor.moveToFirst()) {
                return false;
            }

            int typeCol = cursor.getColumnIndexOrThrow("type");
            return cursor.getInt(typeCol) != UriMatcher.NO_MATCH;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public boolean remove(String key) {
        if (MAIN_PROCESS.equals(ProcessUtil.getCurrentProcessName(mContext))){
            int result = 0;
            result += keyValueDao.delete(new KV_String(key+KV_TYPE_S, keyValueDao.getString(key+KV_TYPE_S)));
            result += keyValueDao.delete(new KV_INT(key+KV_TYPE_I, keyValueDao.getInt(key+KV_TYPE_I)));
            result += keyValueDao.delete(new KV_INT(key+KV_TYPE_B, keyValueDao.getBoolean(key+KV_TYPE_B)));
            result += keyValueDao.delete(new KV_Byte(key+KV_TYPE_BT, keyValueDao.getBytes(key+KV_TYPE_BT)));
            result += keyValueDao.delete(new KV_Float(key+KV_TYPE_F, keyValueDao.getFloat(key+KV_TYPE_F)));
            result += keyValueDao.delete(new KV_Long(key+KV_TYPE_L, keyValueDao.getLong(key+KV_TYPE_L)));
            return result>0;
        }
        Uri uri = NOTIFY_URI.buildUpon().appendPath(PATH_ALL).appendPath(key).build();
        int delete = 0;
        try {
            delete = mContext.getContentResolver().delete(uri, null, null);
        } catch (Exception e) {
            Log.e(TAG, key + " 删除错误：" + e);
        }
        return delete > 0;
    }

    @Override
    public Editor edit() {
        return null;
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {

    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {

    }

    private Object querySingle(String key, Object defValue) {
        if (TextUtils.isEmpty(key)) {
            return defValue;
        }
        String[] columns = new String[]{"type", "value"};
        String pathString = getPathByKey(key);
        Uri uri = NOTIFY_URI.buildUpon().appendPath(pathString)
                .appendPath(key)
                .build();
        Cursor cursor = query(uri, columns);

        try {
            if (cursor == null || !cursor.moveToFirst()) {
                return defValue;
            }
            int typeCol = cursor.getColumnIndexOrThrow("type");
            int type = cursor.getInt(typeCol);
            if (type == UriMatcher.NO_MATCH) {
                return defValue;
            }
            int valueCol = cursor.getColumnIndexOrThrow("value");
            return getValue(cursor, type, valueCol);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private Map<String, Object> queryAll() {

        Map<String, Object> allItems = new HashMap<>();
        String[] columns = new String[]{"key", "value"};
        Uri uri = NOTIFY_URI.buildUpon().appendPath(PATH_ALL)
                .appendPath("keys@all")
                .build();
        Cursor cursor = query(uri, columns);

        try {
            if (cursor == null || !cursor.moveToFirst()) {
                return allItems;
            }
            int keyIndex = cursor.getColumnIndexOrThrow("key");
            int valueIndex = cursor.getColumnIndexOrThrow("value");
            while (cursor.moveToNext()) {
                String key = cursor.getString(keyIndex);
                int i = key.indexOf("@");
                String suffix = key.substring(i);
                if (suffix.equals(KV_TYPE_B)) {
                    allItems.put(key.substring(0, i), cursor.getInt(valueIndex) == 1);
                }
                if (suffix.equals(KV_TYPE_BT)) {
                    allItems.put(key.substring(0, i), cursor.getBlob(valueIndex));
                }
                if (suffix.equals(KV_TYPE_F)) {
                    allItems.put(key.substring(0, i), cursor.getFloat(valueIndex));
                }
                if (suffix.equals(KV_TYPE_I)) {
                    allItems.put(key.substring(0, i), cursor.getInt(valueIndex));
                }
                if (suffix.equals(KV_TYPE_L)) {
                    allItems.put(key.substring(0, i), cursor.getLong(valueIndex));
                }
                if (suffix.equals(KV_TYPE_S)) {
                    allItems.put(key.substring(0, i), cursor.getString(valueIndex));
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return allItems;
    }

    private String getPathByKey(String key) {
        if (key.endsWith(KV_TYPE_S)) {
            return PATH_STRING;
        }
        if (key.endsWith(KV_TYPE_I)) {
            return PATH_INT;
        }
        if (key.endsWith(KV_TYPE_B)) {
            return PATH_BOOL;
        }
        if (key.endsWith(KV_TYPE_BT)) {
            return PATH_BYTE;
        }
        if (key.endsWith(KV_TYPE_F)) {
            return PATH_FLOAT;
        }
        if (key.endsWith(KV_TYPE_L)) {
            return PATH_LONG;
        }
        return "unknownType";
    }

    private Cursor query(Uri uri, String[] columns) {
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver().query(uri, columns, null, null, null);
        } catch (Exception e) {
            Log.e(TAG, uri + " 查询异常：" + e);
        }
        return cursor;
    }

    private Object getValue(Cursor cursor, int type, int valueCol) {
        switch (type) {
            case KeyValueProvider.MATCH_CODE_STRING:
                return cursor.getString(valueCol);
            case KeyValueProvider.MATCH_CODE_INT:
                return cursor.getInt(valueCol);
            case KeyValueProvider.MATCH_CODE_LONG:
                return cursor.getLong(valueCol);
            case KeyValueProvider.MATCH_CODE_FLOAT:
                return cursor.getFloat(valueCol);
            case MATCH_CODE_BOOL:
                return cursor.getInt(valueCol) != 0;
            case KeyValueProvider.MATCH_CODE_BYTE:
                return cursor.getBlob(valueCol);
            default:
                Log.e(TAG, "未知类型:" + type);
                return null;
        }
    }
}
