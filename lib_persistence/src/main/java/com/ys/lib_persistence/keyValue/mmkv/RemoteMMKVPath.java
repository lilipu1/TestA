package com.ys.lib_persistence.keyValue.mmkv;


class RemoteMMKVPath {
    public final String fileName;
    public final String key;
    public final String defValue;

    public RemoteMMKVPath(String prefFileName, String prefKey, String defValue) {
        this.fileName = prefFileName;
        this.key = prefKey;
        this.defValue = defValue;
    }

    public RemoteMMKVPath(String prefFileName, String prefKey) {
        this.fileName = prefFileName;
        this.key = prefKey;
        defValue = "";
    }

    public RemoteMMKVPath withKey(String prefKey) {
        if (this.key != null) {
            throw new IllegalArgumentException("Path already has a key");
        }
        return new RemoteMMKVPath(this.fileName, prefKey);
    }

    @Override
    public String toString() {
        String ret = "file:" + this.fileName;
        if (this.key != null) {
            ret += "/key:" + this.key;
        }
        return ret;
    }
}
