package com.ys.lib_persistence.keyValue.sql;

import static com.ys.lib_persistence.keyValue.Constants.AUTHORITY;
import static com.ys.lib_persistence.keyValue.core.MMKVImp.*;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ys.lib_persistence.keyValue.sql.dao.KeyValueDao;
import com.ys.lib_persistence.keyValue.sql.entity.KV_Base;
import com.ys.lib_persistence.keyValue.sql.entity.KV_Byte;
import com.ys.lib_persistence.keyValue.sql.entity.KV_Float;
import com.ys.lib_persistence.keyValue.sql.entity.KV_INT;
import com.ys.lib_persistence.keyValue.sql.entity.KV_Long;
import com.ys.lib_persistence.keyValue.sql.entity.KV_String;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date: 2022/11/11 14:57
 * @Author: frank
 * @Email: 2370703174@qq.com
 * @Desc:
 */
public class KeyValueProvider extends ContentProvider {

    private static final String TAG = "ContentProvider";

    KeyValueDao keyValueDao;

    private static UriMatcher uriMatcher;

    public static final int MATCH_CODE_STRING = 0;
    public static final int MATCH_CODE_INT = 1;
    public static final int MATCH_CODE_BOOL = 2;
    public static final int MATCH_CODE_BYTE = 3;
    public static final int MATCH_CODE_FLOAT = 4;
    public static final int MATCH_CODE_LONG = 5;
    public static final int MATCH_CODE_ALL = 6;

    public static final String PATH_STRING = "kv_str";
    public static final String PATH_INT = "kv_int";
    public static final String PATH_BOOL = "kv_bool";
    public static final String PATH_BYTE = "kv_byte";
    public static final String PATH_FLOAT = "kv_float";
    public static final String PATH_LONG = "kv_long";
    public static final String PATH_ALL = "kv_all";

    private static final Uri NOTIFY_URI = Uri.parse("content://" + AUTHORITY + "/keyValue");

    static {
        //匹配不成功返回NO_MATCH(-1)
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        //添加我们需要匹配的uri
        uriMatcher.addURI(AUTHORITY, "keyValue/" + PATH_STRING + "/*", MATCH_CODE_STRING);
        uriMatcher.addURI(AUTHORITY, "keyValue/" + PATH_INT + "/*", MATCH_CODE_INT);
        uriMatcher.addURI(AUTHORITY, "keyValue/" + PATH_BOOL + "/*", MATCH_CODE_BOOL);
        uriMatcher.addURI(AUTHORITY, "keyValue/" + PATH_BYTE + "/*", MATCH_CODE_BYTE);
        uriMatcher.addURI(AUTHORITY, "keyValue/" + PATH_FLOAT + "/*", MATCH_CODE_FLOAT);
        uriMatcher.addURI(AUTHORITY, "keyValue/" + PATH_LONG + "/*", MATCH_CODE_LONG);
        uriMatcher.addURI(AUTHORITY, "keyValue/" + PATH_ALL + "/*", MATCH_CODE_ALL);
    }


    @Override
    public boolean onCreate() {
        keyValueDao = KeyValueDatabase.getInstance(getContext()).getKeyValueDao();

        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        List<String> pathSegments = uri.getPathSegments();
        String key = pathSegments.get(2);
        String[] columns = new String[]{"type", "value"};
        Object[] results = new Object[2];
        results[0] = UriMatcher.NO_MATCH;
        MatrixCursor cursor = new MatrixCursor(columns);
        int match = uriMatcher.match(uri);
        results[1] = null;
        if (match == MATCH_CODE_STRING) {
            if (keyValueDao.containsKeyString(key) != 0) {
                results[0] = match;
            }
            results[1] = keyValueDao.getString(key);
            cursor.addRow(results);
        }
        if (match == MATCH_CODE_INT) {
            if (keyValueDao.containsKeyInt(key) != 0) {
                results[0] = match;
            }
            results[1] = keyValueDao.getInt(key);
            cursor.addRow(results);
        }
        if (match == MATCH_CODE_BOOL) {
            if (keyValueDao.containsKeyString(key) != 0) {
                results[0] = match;
            }
            results[1] = keyValueDao.getBoolean(key);
            cursor.addRow(results);
        }
        if (match == MATCH_CODE_BYTE) {
            if (keyValueDao.containsKeyString(key) != 0) {
                results[0] = match;
            }
            results[1] = keyValueDao.getBytes(key);
            cursor.addRow(results);
        }
        if (match == MATCH_CODE_FLOAT) {
            if (keyValueDao.containsKeyString(key) != 0) {
                results[0] = match;
            }
            results[1] = keyValueDao.getFloat(key);
            cursor.addRow(results);
        }
        if (match == MATCH_CODE_LONG) {
            if (keyValueDao.containsKeyString(key) != 0) {
                results[0] = match;
            }
            results[1] = keyValueDao.getLong(key);
            cursor.addRow(results);
        }
        if (match == MATCH_CODE_ALL) {
            if (key.equals("keys@all")) {
                List<KV_Byte> allByte = keyValueDao.getAllByte();
                List<KV_Float> allFloat = keyValueDao.getAllFloat();
                List<KV_INT> allInt = keyValueDao.getAllInt();
                List<KV_Long> allLong = keyValueDao.getAllLong();
                List<KV_String> allString = keyValueDao.getAllString();
                String[] allCol = new String[]{"key", "value"};
                MatrixCursor allCursor = new MatrixCursor(allCol);
                List<KV_Base> allItems = new ArrayList<>();
                allItems.addAll(allByte);
                allItems.addAll(allFloat);
                allItems.addAll(allInt);
                allItems.addAll(allLong);
                allItems.addAll(allString);
                for (KV_Base allItem : allItems) {
                    Object[] item = new Object[2];
                    item[0] = allItem.key;
                    item[1] = allItem.value;
                    allCursor.addRow(item);
                }
                return allCursor;
            } else {
                results[1] = keyValueDao.containsKeyInt(key + KV_TYPE_B) + keyValueDao.containsKeyByte(key + KV_TYPE_BT)
                        + keyValueDao.containsKeyString(key + KV_TYPE_S) + keyValueDao.containsKeyLong(key + KV_TYPE_L)
                        + keyValueDao.containsKeyFloat(key + KV_TYPE_F) + keyValueDao.containsKeyInt(key + KV_TYPE_I);
                if ((int) results[1] > 0) {
                    results[0] = match;
                }
            }
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int match = uriMatcher.match(uri);
        long result = -1;
        for (String key : values.keySet()) {
            if (match == MATCH_CODE_STRING) {
                result = keyValueDao.put(new KV_String(key, (String) values.get(key)));
            }
            if (match == MATCH_CODE_INT) {
                result = keyValueDao.put(new KV_INT(key, (Integer) values.get(key)));
            }
            if (match == MATCH_CODE_BOOL) {
                result = keyValueDao.put(new KV_INT(key, (Integer) values.get(key)));
            }
            if (match == MATCH_CODE_BYTE) {
                result = keyValueDao.put(new KV_Byte(key, (byte[]) values.get(key)));
            }
            if (match == MATCH_CODE_FLOAT) {
                result = keyValueDao.put(new KV_Float(key, (float) values.get(key)));
            }
            if (match == MATCH_CODE_LONG) {
                result = keyValueDao.put(new KV_Long(key, (long) values.get(key)));
            }
        }
        if (result >= 0) {
            notifyChange();
        }
        return NOTIFY_URI.buildUpon().appendPath(String.valueOf(result)).build();
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int match = uriMatcher.match(uri);
        String key = uri.getPathSegments().get(2);
        int result = 0;
        if (match == MATCH_CODE_ALL){
            result += keyValueDao.delete(new KV_String(key+KV_TYPE_S, keyValueDao.getString(key+KV_TYPE_S)));
            result += keyValueDao.delete(new KV_INT(key+KV_TYPE_I, keyValueDao.getInt(key+KV_TYPE_I)));
            result += keyValueDao.delete(new KV_INT(key+KV_TYPE_B, keyValueDao.getBoolean(key+KV_TYPE_B)));
            result += keyValueDao.delete(new KV_Byte(key+KV_TYPE_BT, keyValueDao.getBytes(key+KV_TYPE_BT)));
            result += keyValueDao.delete(new KV_Float(key+KV_TYPE_F, keyValueDao.getFloat(key+KV_TYPE_F)));
            result += keyValueDao.delete(new KV_Long(key+KV_TYPE_L, keyValueDao.getLong(key+KV_TYPE_L)));
        }
        if (result > 0) {
            notifyChange();
        }
        return result;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int match = uriMatcher.match(uri);
        int result = -1;
        for (String key : values.keySet()) {
            if (match == MATCH_CODE_STRING) {
                result = keyValueDao.update(new KV_String(key, (String) values.get(key)));
            }
            if (match == MATCH_CODE_INT) {
                result = keyValueDao.update(new KV_INT(key, (Integer) values.get(key)));
            }
            if (match == MATCH_CODE_BOOL) {
                result = keyValueDao.update(new KV_INT(key, (Integer) values.get(key)));
            }
            if (match == MATCH_CODE_BYTE) {
                result = keyValueDao.update(new KV_Byte(key, (byte[]) values.get(key)));
            }
            if (match == MATCH_CODE_FLOAT) {
                result = keyValueDao.update(new KV_Float(key, (float) values.get(key)));
            }
            if (match == MATCH_CODE_LONG) {
                result = keyValueDao.update(new KV_Long(key, (long) values.get(key)));
            }
        }
        if (result >= 0) {
            notifyChange();
        }
        return result;
    }

    private void notifyChange() {
        getContext().getContentResolver().notifyChange(NOTIFY_URI, null);
    }
}
