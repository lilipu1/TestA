package com.ys.lib_persistence.keyValue.sql;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.ys.lib_persistence.keyValue.sql.dao.KeyValueDao;
import com.ys.lib_persistence.keyValue.sql.entity.KV_Byte;
import com.ys.lib_persistence.keyValue.sql.entity.KV_Float;
import com.ys.lib_persistence.keyValue.sql.entity.KV_INT;
import com.ys.lib_persistence.keyValue.sql.entity.KV_Long;
import com.ys.lib_persistence.keyValue.sql.entity.KV_String;

import java.io.File;
import java.io.PipedReader;

/**
 * @Date: 2022/11/11 14:21
 * @Author: frank
 * @Email: 2370703174@qq.com
 * @Desc:
 */
@Database(entities = {KV_Float.class, KV_INT.class, KV_String.class, KV_Byte.class, KV_Long.class}
        ,version = 1
        ,exportSchema = true
)
public abstract class KeyValueDatabase extends RoomDatabase{

    private static volatile KeyValueDatabase INSTANCE;

    public abstract KeyValueDao getKeyValueDao();

    public static KeyValueDatabase getInstance(Context context) {

        if (null == INSTANCE) {
            synchronized (KeyValueDatabase.class) {
                if (null == INSTANCE) {
                    INSTANCE = buildDatabaseInstance(context);
                }
            }
        }
        return INSTANCE;
    }

    private static KeyValueDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,KeyValueDatabase.class,"YSKeyValue")
                .allowMainThreadQueries()
                .enableMultiInstanceInvalidation()//多进程共享
                .fallbackToDestructiveMigration()//升级出错清空数据,上线以后去掉
                .fallbackToDestructiveMigrationFrom(1)//升级出错清空数据,上线以后去掉
                .fallbackToDestructiveMigrationOnDowngrade()//降级出错清空数据,上线以后去掉
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Log.e("KeyValueDataBase", "onCreate");
                    }

                    @Override
                    public void onOpen(@NonNull SupportSQLiteDatabase db) {
                        super.onOpen(db);
                        Log.e("KeyValueDataBase", "onOpen");
                    }

                    @Override
                    public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db) {
                        super.onDestructiveMigration(db);
                    }
                })
                .build();
    }

}
