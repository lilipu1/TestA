package com.ys.lib_persistence.keyValue.mmkv;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.mmkv.MMKV;
import com.ys.lib_persistence.keyValue.Constants;
import com.ys.lib_persistence.keyValue.core.MMKVImp;

import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Map;


public abstract class RemoteMMKVProvider extends ContentProvider implements SharedPreferences.OnSharedPreferenceChangeListener {
    private final Uri mBaseUri;
    private final RemoteMMKVUriParser mUriParser;
    private MMKV mmkv;
    private static final String MMKV_ID = "YS_KEY_VALUE";


    public RemoteMMKVProvider() {
        mBaseUri = Uri.parse("content://" + Constants.AUTHORITY);
        mUriParser = new RemoteMMKVUriParser(Constants.AUTHORITY);
    }


    protected boolean checkAccess(String prefFileName, String prefKey, boolean write) {
        return true;
    }


    @Override
    public boolean onCreate() {

        MMKV.initialize(getContext());
        mmkv = MMKV.mmkvWithID(MMKV_ID, MMKV.MULTI_PROCESS_MODE);

        return true;
    }


    protected SharedPreferences getSharedPreferences(Context context, String prefFileName) {
        return context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        RemoteMMKVPath prefPath = mUriParser.parse(uri);

        checkAccessOrThrow(prefPath, false);
        if (projection == null) {
            projection = MMKVContract.COLUMN_ALL;
        }
        String key = prefPath.key;
        String defValue = prefPath.defValue;

        MatrixCursor cursor = new MatrixCursor(projection);


        if (isSingleKey(key)) {
            Object prefValue = "";
            if (key.endsWith(MMKVImp.KV_TYPE_S)) {
                if (TextUtils.isEmpty(defValue) && !"null".equals(defValue)) {
                    prefValue = mmkv.getString(key, defValue);
                } else {
                    prefValue = mmkv.getString(key, "");
                }
            } else if (key.endsWith(MMKVImp.KV_TYPE_I)) {
                if (TextUtils.isEmpty(defValue) && !"null".equals(defValue)) {
                    prefValue = mmkv.getInt(key, Integer.parseInt(defValue));
                } else {
                    prefValue = mmkv.getInt(key, 0);
                }
            } else if (key.endsWith(MMKVImp.KV_TYPE_B)) {
                if (TextUtils.isEmpty(defValue) && !"null".equals(defValue)) {
                    prefValue = mmkv.getBoolean(key, Boolean.parseBoolean(defValue));
                } else {
                    prefValue = mmkv.getBoolean(key, false);
                }
            } else if (key.endsWith(MMKVImp.KV_TYPE_F)) {
                if (TextUtils.isEmpty(defValue) && !"null".equals(defValue)) {
                    prefValue = mmkv.getFloat(key, Float.parseFloat(defValue));
                } else {
                    prefValue = mmkv.getFloat(key, 0f);
                }
            } else if (key.endsWith(MMKVImp.KV_TYPE_L)) {
                if (TextUtils.isEmpty(defValue) && !"null".equals(defValue)) {
                    prefValue = mmkv.getLong(key, Long.parseLong(defValue));
                } else {
                    prefValue = mmkv.getLong(key, 0);
                }
            } else if (key.endsWith(MMKVImp.KV_TYPE_BT)) {
                prefValue = mmkv.getBytes(key, new byte[]{});
            }
            cursor.addRow(buildRow(projection, prefPath.key, prefValue));
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (values == null) {
            return null;
        }

        RemoteMMKVPath prefPath = mUriParser.parse(uri);
        String prefKey = getKeyFromUriOrValues(prefPath, values);

        SharedPreferences.Editor editor = mmkv.edit();

        putPreference(editor, prefKey, values);

        if (editor.commit()) {
            return getPreferenceUri(prefPath.fileName, prefKey);
        } else {
            return null;
        }
    }


    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        RemoteMMKVPath prefPath = mUriParser.parse(uri);

        if (isSingleKey(prefPath.key)) {
            //throw new IllegalArgumentException("Cannot bulk insert with single key URI");
            return 0;
        }

        SharedPreferences.Editor editor = mmkv.edit();

        for (ContentValues value : values) {
            String prefKey = getKeyFromValues(value);
            checkAccessOrThrow(prefPath.withKey(prefKey), true);
            putPreference(editor, prefKey, value);
        }

        boolean commitResult = editor.commit();
        if (commitResult) {
            MMKV.backupAllToDirectory(Environment.getExternalStorageDirectory() + "/YSConfig");
            return values.length;
        } else {
            return 0;
        }
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        RemoteMMKVPath prefPath = mUriParser.parse(uri);

        checkAccessOrThrow(prefPath, true);

        if (isSingleKey(prefPath.key)) {
            mmkv.remove(prefPath.key);
        } else {
            mmkv.clear();
        }


        if (mmkv.commit()) {
            MMKV.backupAllToDirectory(Environment.getExternalStorageDirectory() + "/YSConfig");
            return 1;
        } else {
            return 0;
        }
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (values == null) {
            return delete(uri, selection, selectionArgs);
        } else {
            return insert(uri, values) != null ? 1 : 0;
        }
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences prefs, String prefKey) {

    }


    private void putPreference(SharedPreferences.Editor editor, String prefKey, ContentValues values) {

        Integer type = values.getAsInteger(MMKVContract.COLUMN_TYPE);
        if (type == null) {
            throw new IllegalArgumentException("Invalid or no preference type specified");
        }


        Object rawValue = values.get(MMKVContract.COLUMN_VALUE);
        Object value = RemoteUtils.deserializeInput(rawValue, type);


        if (!isSingleKey(prefKey)) {
            if (type == MMKVContract.TYPE_NULL) {
                editor.clear();
                return;
            } else {
                throw new IllegalArgumentException("Attempting to insert preference with null or empty key");
            }
        }

        switch (type) {
            case MMKVContract.TYPE_NULL:
                editor.remove(prefKey);
                break;
            case MMKVContract.TYPE_STRING:
                editor.putString(prefKey, (String) value);
                break;
            case MMKVContract.TYPE_STRING_SET:
                if (Build.VERSION.SDK_INT >= 11) {
                    editor.putStringSet(prefKey, RemoteUtils.castStringSet(value));
                } else {
                    throw new IllegalArgumentException("String set preferences not supported on API < 11");
                }
                break;
            case MMKVContract.TYPE_INT:
                editor.putInt(prefKey, (Integer) value);
                break;
            case MMKVContract.TYPE_LONG:
                editor.putLong(prefKey, (Long) value);
                break;
            case MMKVContract.TYPE_FLOAT:
                editor.putFloat(prefKey, (Float) value);
                break;
            case MMKVContract.TYPE_BOOLEAN:
                editor.putBoolean(prefKey, (Boolean) value);
                break;
            default:
                throw new IllegalArgumentException("Cannot set preference with type " + type);
        }
    }


    private Object[] buildRow(String[] projection, String key, Object value) {
        Object[] row = new Object[projection.length];
        for (int i = 0; i < row.length; ++i) {
            String col = projection[i];
            if (MMKVContract.COLUMN_KEY.equals(col)) {
                row[i] = key;
            } else if (MMKVContract.COLUMN_TYPE.equals(col)) {
                row[i] = RemoteUtils.getPreferenceType(value);
            } else if (MMKVContract.COLUMN_VALUE.equals(col)) {
                row[i] = RemoteUtils.serializeOutput(value);
            } else {
                throw new IllegalArgumentException("Invalid column name: " + col);
            }
        }
        return row;
    }


    private static boolean isSingleKey(String prefKey) {
        return prefKey != null;
    }


    private static String getKeyFromValues(ContentValues values) {
        String key = values.getAsString(MMKVContract.COLUMN_KEY);
        if (key != null && key.length() == 0) {
            key = null;
        }
        return key;
    }


    private static String getKeyFromUriOrValues(RemoteMMKVPath prefPath, ContentValues values) {
        String uriKey = prefPath.key;
        String valuesKey = getKeyFromValues(values);
        if (isSingleKey(uriKey) && isSingleKey(valuesKey)) {
            // If a key is specified in both the URI and
            // ContentValues, they must match
            if (!uriKey.equals(valuesKey)) {
                throw new IllegalArgumentException("Conflicting keys specified in URI and ContentValues");
            }
            return uriKey;
        } else if (isSingleKey(uriKey)) {
            return uriKey;
        } else if (isSingleKey(valuesKey)) {
            return valuesKey;
        } else {
            return null;
        }
    }


    private void checkAccessOrThrow(RemoteMMKVPath prefPath, boolean write) {

        String prefKey = prefPath.key;
        if (!isSingleKey(prefKey)) {
            prefKey = "";
        }

        if (!checkAccess(prefPath.fileName, prefKey, write)) {
            throw new SecurityException("Insufficient permissions to access: " + prefPath);
        }
    }


    private Uri getPreferenceUri(String prefFileName, String prefKey) {
        Uri.Builder builder = mBaseUri.buildUpon().appendPath(prefFileName);
        if (isSingleKey(prefKey)) {
            builder.appendPath(prefKey);
        }
        return builder.build();
    }
}
