package com.ys.lib_persistence.keyValue.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.jeremyliao.liveeventbus.core.LiveEvent;

public class ConfigBean implements LiveEvent {
    public String key;
    public Object value;

    public ConfigBean(String key, Object value) {
        this.key = key;
        this.value = value;
    }
}
