package com.ys.lib_persistence.keyValue.mmkv;

public class YSMMKVProvider extends RemoteMMKVProvider {
    public YSMMKVProvider() {
        super();
    }

    @Override
    protected boolean checkAccess(String prefName, String prefKey, boolean write) {
        return true;
    }
}
