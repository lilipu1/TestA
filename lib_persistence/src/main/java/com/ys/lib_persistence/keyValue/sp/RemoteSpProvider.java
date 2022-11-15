package com.ys.lib_persistence.keyValue.sp;

/**
 * @date: 2022/11/15 0015
 * @author: frank
 * @email: 2370703174@qq.com
 * @desc:
 */
public class RemoteSpProvider extends RemotePreferenceProvider{

    public RemoteSpProvider() {
        super("com.tcn.vending.preferences", new String[] {"main_prefs"});
    }
}
