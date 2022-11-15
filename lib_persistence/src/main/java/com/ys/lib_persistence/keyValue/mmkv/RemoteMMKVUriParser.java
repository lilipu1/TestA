package com.ys.lib_persistence.keyValue.mmkv;

import android.content.UriMatcher;
import android.net.Uri;

import java.util.List;


public class RemoteMMKVUriParser {
    private static final int PREFERENCES_ID = 1;
    private static final int PREFERENCE_ID = 2;

    private final UriMatcher mUriMatcher;

    public RemoteMMKVUriParser(String authority) {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(authority, "*/", PREFERENCES_ID);
        mUriMatcher.addURI(authority, "*/*", PREFERENCE_ID);
        mUriMatcher.addURI(authority, "*/*/*", PREFERENCE_ID);
    }


    public RemoteMMKVPath parse(Uri uri) {
        int match = mUriMatcher.match(uri);
        if (match != PREFERENCE_ID && match != PREFERENCES_ID) {
            throw new IllegalArgumentException("Invalid URI: " + uri);
        }


        List<String> pathSegments = uri.getPathSegments();

        String prefFileName = pathSegments.get(0);
        String prefKey = null;
        String defValue = null;
        if (match == PREFERENCE_ID) {
            prefKey = pathSegments.get(1);
            if (pathSegments.size() > 2){
                defValue = pathSegments.get(2);
            }
        }
        return new RemoteMMKVPath(prefFileName, prefKey,defValue);
    }
}
