package com.ys.lib_persistence.keyValue.sql.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * @Date: 2022/11/11 14:23
 * @Author: frank
 * @Email: 2370703174@qq.com
 * @Desc:
 */
@Entity(tableName = "kv_float",indices = {@Index("key")})
public class KV_Float extends KV_Base<Float> {

    public KV_Float() {
    }

    @Ignore
    public KV_Float(String key, Float aFloat) {
        super(key, aFloat);
    }
}
