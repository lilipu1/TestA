package com.ys.lib_persistence.keyValue.sql.entity;

import androidx.room.ColumnInfo;
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
@Entity(tableName = "kv_int",indices = {@Index("key")})
public class KV_INT extends KV_Base<Integer>{
    public KV_INT() {
    }

    @Ignore
    public KV_INT(String key, Integer integer) {
        super(key, integer);
    }
}
