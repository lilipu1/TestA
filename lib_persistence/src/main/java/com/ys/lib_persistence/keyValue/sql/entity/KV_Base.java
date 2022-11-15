package com.ys.lib_persistence.keyValue.sql.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * @Date: 2022/11/11 15:12
 * @Author: frank
 * @Email: 2370703174@qq.com
 * @Desc:
 */
@Entity
public class KV_Base<T> {

    @PrimaryKey
    @NonNull
    public String key;

    public T value;

    public KV_Base() {
    }

    @Ignore
    public KV_Base(String key, T t) {
        this.key = key;
        this.value = t;
    }
}
