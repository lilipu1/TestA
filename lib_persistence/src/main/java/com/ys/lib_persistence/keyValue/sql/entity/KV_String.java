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
@Entity(tableName = "kv_str",indices = {@Index("key")})
public class KV_String extends KV_Base<String>{
    public KV_String() {
    }

    @Ignore
    public KV_String(String key, String s) {
        super(key, s);
    }
}
