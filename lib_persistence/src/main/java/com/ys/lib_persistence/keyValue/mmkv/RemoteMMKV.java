package com.ys.lib_persistence.keyValue.mmkv;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;


public class RemoteMMKV implements SharedPreferences {
    private final Context mContext;
    private final Handler mHandler;
    private final Uri mBaseUri;
    private final boolean mStrictMode;
    private final WeakHashMap<OnSharedPreferenceChangeListener, PreferenceContentObserver> mListeners;
    private final RemoteMMKVUriParser mUriParser;


    public RemoteMMKV(Context context, String authority) {
        this(context, authority, false);
    }


    public RemoteMMKV(Context context, String authority, boolean strictMode) {
        this(context, new Handler(context.getMainLooper()), authority, strictMode);
    }


    RemoteMMKV(Context context, Handler handler, String authority, boolean strictMode) {
        checkNotNull("context", context);
        checkNotNull("handler", handler);
        checkNotNull("authority", authority);
        mContext = context;
        mHandler = handler;
        mBaseUri = Uri.parse("content://" + authority).buildUpon().appendPath("YS_KEY_VALUE").build();
        mStrictMode = strictMode;
        mListeners = new WeakHashMap<OnSharedPreferenceChangeListener, PreferenceContentObserver>();
        mUriParser = new RemoteMMKVUriParser(authority);
    }

    @Override
    public Map<String, ?> getAll() {
        return queryAll();
    }

    @Override
    public String getString(String key, String defValue) {
        return (String) querySingle(key, defValue, MMKVContract.TYPE_STRING);
    }

    @Override
    @TargetApi(11)
    public Set<String> getStringSet(String key, Set<String> defValues) {
        if (Build.VERSION.SDK_INT < 11) {
            throw new UnsupportedOperationException("String sets only supported on API 11 and above");
        }
        return RemoteUtils.castStringSet(querySingle(key, defValues, MMKVContract.TYPE_STRING_SET));
    }

    @Override
    public int getInt(String key, int defValue) {
        return (Integer) querySingle(key, defValue, MMKVContract.TYPE_INT);
    }

    @Override
    public long getLong(String key, long defValue) {
        return (Long) querySingle(key, defValue, MMKVContract.TYPE_LONG);
    }

    @Override
    public float getFloat(String key, float defValue) {
        return (Float) querySingle(key, defValue, MMKVContract.TYPE_FLOAT);
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return (boolean) querySingle(key, defValue, MMKVContract.TYPE_BOOLEAN);
    }

    public byte[] getBytes(String key) {
        return (byte[]) querySingle(key, new byte[]{}, MMKVContract.TYPE_BYTE);
    }


    @Override
    public boolean contains(String key) {
        return containsKey(key);
    }

    @Override
    public RemotePreferencesEditor edit() {
        return new RemotePreferencesEditor();
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {

    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {

    }


    private static void checkNotNull(String name, Object object) {
        if (object == null) {
            throw new IllegalArgumentException(name + " is null");
        }
    }


    private static void checkKeyNotEmpty(String key) {
        if (key == null || key.length() == 0) {
            throw new IllegalArgumentException("Key is null or empty");
        }
    }


    private void wrapException(Exception e) {
        if (mStrictMode) {
            throw new RemoteMMVAccessException(e);
        }
    }


    private Cursor query(Uri uri, String[] columns) {
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver().query(uri, columns, null, null, null);
        } catch (Exception e) {
            wrapException(e);
        }
        if (cursor == null && mStrictMode) {
            throw new RemoteMMVAccessException("query() failed or returned null cursor");
        }
        return cursor;
    }


    private boolean bulkInsert(Uri uri, ContentValues[] values) {
        int count;
        try {
            count = mContext.getContentResolver().bulkInsert(uri, values);
        } catch (Exception e) {
            wrapException(e);
            return false;
        }
        if (count != values.length && mStrictMode) {
            throw new RemoteMMVAccessException("bulkInsert() failed");
        }
        return count == values.length;
    }


    private Object querySingle(String key, Object defValue, int expectedType) {
        checkKeyNotEmpty(key);
        String[] columns = {MMKVContract.COLUMN_TYPE, MMKVContract.COLUMN_VALUE};
        Uri uri = mBaseUri.buildUpon().appendPath(key)
                .appendPath(String.valueOf(defValue))
                .build();
        Cursor cursor = query(uri, columns);

        try {
            if (cursor == null || !cursor.moveToFirst()) {
                return defValue;
            }

            int typeCol = cursor.getColumnIndexOrThrow(MMKVContract.COLUMN_TYPE);
            int type = cursor.getInt(typeCol);
            if (type == MMKVContract.TYPE_NULL) {
                return defValue;
            } else if (type != expectedType) {
                //throw new ClassCastException("Preference type mismatch");
            }

            int valueCol = cursor.getColumnIndexOrThrow(MMKVContract.COLUMN_VALUE);
            return getValue(cursor, typeCol, valueCol);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    private Map<String, Object> queryAll() {
        Uri uri = mBaseUri.buildUpon().appendPath("").build();
        String[] columns = {MMKVContract.COLUMN_KEY, MMKVContract.COLUMN_TYPE, MMKVContract.COLUMN_VALUE};
        Cursor cursor = query(uri, columns);
        try {
            HashMap<String, Object> map = new HashMap<String, Object>();
            if (cursor == null) {
                return map;
            }

            int keyCol = cursor.getColumnIndexOrThrow(MMKVContract.COLUMN_KEY);
            int typeCol = cursor.getColumnIndexOrThrow(MMKVContract.COLUMN_TYPE);
            int valueCol = cursor.getColumnIndexOrThrow(MMKVContract.COLUMN_VALUE);
            while (cursor.moveToNext()) {
                String key = cursor.getString(keyCol);
                map.put(key, getValue(cursor, typeCol, valueCol));
            }
            return map;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    private boolean containsKey(String key) {
        checkKeyNotEmpty(key);
        Uri uri = mBaseUri.buildUpon().appendPath(key).build();
        String[] columns = {MMKVContract.COLUMN_TYPE};
        Cursor cursor = query(uri, columns);
        try {
            if (cursor == null || !cursor.moveToFirst()) {
                return false;
            }

            int typeCol = cursor.getColumnIndexOrThrow(MMKVContract.COLUMN_TYPE);
            return cursor.getInt(typeCol) != MMKVContract.TYPE_NULL;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    private Object getValue(Cursor cursor, int typeCol, int valueCol) {
        int expectedType = cursor.getInt(typeCol);
        switch (expectedType) {
            case MMKVContract.TYPE_STRING:
                return cursor.getString(valueCol);
            case MMKVContract.TYPE_STRING_SET:
                return RemoteUtils.deserializeStringSet(cursor.getString(valueCol));
            case MMKVContract.TYPE_INT:
                return cursor.getInt(valueCol);
            case MMKVContract.TYPE_LONG:
                return cursor.getLong(valueCol);
            case MMKVContract.TYPE_FLOAT:
                return cursor.getFloat(valueCol);
            case MMKVContract.TYPE_BOOLEAN:
                return cursor.getInt(valueCol) != 0;
            case MMKVContract.TYPE_BYTE:
                return cursor.getBlob(valueCol);
            default:
                throw new AssertionError("Invalid expected type: " + expectedType);
        }
    }


    public class RemotePreferencesEditor implements Editor {
        private final ArrayList<ContentValues> mValues = new ArrayList<ContentValues>();


        private ContentValues createContentValues(String key, int type) {
            ContentValues values = new ContentValues(4);
            values.put(MMKVContract.COLUMN_KEY, key);
            values.put(MMKVContract.COLUMN_TYPE, type);
            return values;
        }


        private ContentValues createAddOp(String key, int type) {
            checkKeyNotEmpty(key);
            ContentValues values = createContentValues(key, type);
            mValues.add(values);
            return values;
        }

        private ContentValues createRemoveOp(String key) {
            ContentValues values = createContentValues(key, MMKVContract.TYPE_NULL);
            values.putNull(MMKVContract.COLUMN_VALUE);
            mValues.add(0, values);
            return values;
        }

        @Override
        public Editor putString(String key, String value) {
            createAddOp(key, MMKVContract.TYPE_STRING).put(MMKVContract.COLUMN_VALUE, value);
            return this;
        }

        public Editor putBytes(String key, byte[] bytes) {
            createAddOp(key, MMKVContract.TYPE_BYTE).put(MMKVContract.COLUMN_VALUE, bytes);
            return this;
        }

        @Override
        @TargetApi(11)
        public Editor putStringSet(String key, Set<String> value) {
            if (Build.VERSION.SDK_INT < 11) {
                throw new UnsupportedOperationException("String sets only supported on API 11 and above");
            }
            String serializedSet = RemoteUtils.serializeStringSet(value);
            createAddOp(key, MMKVContract.TYPE_STRING_SET).put(MMKVContract.COLUMN_VALUE, serializedSet);
            return this;
        }

        @Override
        public Editor putInt(String key, int value) {
            createAddOp(key, MMKVContract.TYPE_INT).put(MMKVContract.COLUMN_VALUE, value);
            return this;
        }

        @Override
        public Editor putLong(String key, long value) {
            createAddOp(key, MMKVContract.TYPE_LONG).put(MMKVContract.COLUMN_VALUE, value);
            return this;
        }

        @Override
        public Editor putFloat(String key, float value) {
            createAddOp(key, MMKVContract.TYPE_FLOAT).put(MMKVContract.COLUMN_VALUE, value);
            return this;
        }

        @Override
        public Editor putBoolean(String key, boolean value) {
            createAddOp(key, MMKVContract.TYPE_BOOLEAN).put(MMKVContract.COLUMN_VALUE, value ? 1 : 0);
            return this;
        }

        @Override
        public Editor remove(String key) {
            checkKeyNotEmpty(key);
            createRemoveOp(key);
            return this;
        }

        @Override
        public Editor clear() {
            createRemoveOp("");
            return this;
        }

        @Override
        public boolean commit() {
            ContentValues[] values = mValues.toArray(new ContentValues[mValues.size()]);
            Uri uri = mBaseUri.buildUpon().appendPath("").build();
            return bulkInsert(uri, values);
        }

        @Override
        public void apply() {
            commit();
        }
    }


    private class PreferenceContentObserver extends ContentObserver {
        private final WeakReference<OnSharedPreferenceChangeListener> mListener;

        private PreferenceContentObserver(OnSharedPreferenceChangeListener listener) {
            super(mHandler);
            mListener = new WeakReference<>(listener);
        }

        @Override
        public boolean deliverSelfNotifications() {
            return true;
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            RemoteMMKVPath path = mUriParser.parse(uri);

            OnSharedPreferenceChangeListener listener = mListener.get();
            if (listener == null) {
                mContext.getContentResolver().unregisterContentObserver(this);
            } else {
                listener.onSharedPreferenceChanged(RemoteMMKV.this, path.key);
            }
        }
    }
}
