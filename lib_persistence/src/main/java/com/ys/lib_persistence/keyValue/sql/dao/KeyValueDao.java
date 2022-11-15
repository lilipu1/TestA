package com.ys.lib_persistence.keyValue.sql.dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.ys.lib_persistence.keyValue.sql.entity.KV_Base;
import com.ys.lib_persistence.keyValue.sql.entity.KV_Byte;
import com.ys.lib_persistence.keyValue.sql.entity.KV_Float;
import com.ys.lib_persistence.keyValue.sql.entity.KV_INT;
import com.ys.lib_persistence.keyValue.sql.entity.KV_Long;
import com.ys.lib_persistence.keyValue.sql.entity.KV_String;

import java.util.List;

/**
 * @Date: 2022/11/11 15:07
 * @Author: frank
 * @Email: 2370703174@qq.com
 * @Desc:
 */
@Dao
public interface KeyValueDao {

    @Query("select 1 from kv_int where `key` = :key limit 1")
    int containsKeyInt(String key);

    @Query("select 1 from kv_str where `key` = :key limit 1")
    int containsKeyString(String key);

    @Query("select 1 from kv_float where `key` = :key limit 1")
    int containsKeyFloat(String key);

    @Query("select 1 from kv_byte where `key` = :key limit 1")
    int containsKeyByte(String key);

    @Query("select 1 from kv_long where `key` = :key limit 1")
    int containsKeyLong(String key);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long put(KV_INT t);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long put(KV_Byte t);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long put(KV_Long t);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long put(KV_Float t);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long put(KV_String t);


    @Transaction
    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(KV_INT... t);

    @Transaction
    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(KV_Byte... t);

    @Transaction
    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(KV_Long... t);

    @Transaction
    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(KV_Float... t);

    @Transaction
    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(KV_String... t);


    @Transaction
    @Delete
    int delete(KV_INT... t);

    @Transaction
    @Delete
    int delete(KV_Byte... t);

    @Transaction
    @Delete
    int delete(KV_Long... t);

    @Transaction
    @Delete
    int delete(KV_Float... t);

    @Transaction
    @Delete
    int delete(KV_String... t);


    @Query("select value  from kv_long where `key` = :key")
    long getLong(String key);

    @Query("select value from kv_int where `key` = :key")
    int getInt(String key);

    @Query("select value from kv_float where `key` = :key")
    float getFloat(String key);

    @Query("select value from kv_str where `key` = :key")
    String getString(String key);

    @Query("select value from kv_byte where `key` = :key")
    byte[] getBytes(String key);

    @Query("select value from kv_int where `key` = :key")
    int getBoolean(String key);

    @Query("select * from kv_int ")
    List<KV_INT> getAllInt();

    @Query("select * from kv_long ")
    List<KV_Long> getAllLong();

    @Query("select * from kv_byte ")
    List<KV_Byte> getAllByte();

    @Query("select * from kv_float ")
    List<KV_Float> getAllFloat();

    @Query("select * from kv_str ")
    List<KV_String> getAllString();
}
