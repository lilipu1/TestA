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
@Entity(tableName = "kv_long",indices = {@Index("key")})
public class KV_Long extends KV_Base<Long>{


    public KV_Long() {
    }

    @Ignore
    public KV_Long(String key, Long aLong) {
        super(key, aLong);
    }
}
