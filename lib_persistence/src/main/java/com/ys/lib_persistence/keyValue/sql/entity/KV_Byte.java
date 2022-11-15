package com.ys.lib_persistence.keyValue.sql.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * @Date: 2022/11/11 14:44
 * @Author: frank
 * @Email: 2370703174@qq.com
 * @Desc:
 */
@Entity(tableName = "kv_byte",indices = {@Index("key")})
public class KV_Byte extends KV_Base<byte[]>{
    public KV_Byte() {
    }

    @Ignore
    public KV_Byte(String key, byte[] bytes) {
        super(key, bytes);
    }
}
