package com.ys.lib_persistence

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ys.lib_persistence.keyValue.sql.KeyValueDatabase
import com.ys.lib_persistence.keyValue.sql.dao.KeyValueDao
import com.ys.lib_persistence.keyValue.sql.entity.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Boolean
import kotlin.String
import kotlin.assert
import kotlin.byteArrayOf

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    private lateinit var keyValueDao: KeyValueDao
    private lateinit var db: KeyValueDatabase
    private val TAG: String = "TEST"



    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder<KeyValueDatabase>(context, KeyValueDatabase::class.java)
            .build()
        keyValueDao = db.keyValueDao
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun test4Query(){
        keyValueDao.put(KV_INT("test",1))
        val intResult = keyValueDao.getInt("test2")
        keyValueDao.put(KV_Float("test",1f))
        val floatResult = keyValueDao.getFloat("test2");
        keyValueDao.put(KV_Long("test",1))
        val longResult = keyValueDao.getLong("test2");
        keyValueDao.put(KV_String("test","HAHA"))
        val stringResult = keyValueDao.getString("test2");
        keyValueDao.put(KV_Byte("test", byteArrayOf()))
        val byteResult = keyValueDao.getBytes("test2");
        assert(intResult == 0)
        Log.e(TAG,"结果:  $intResult  $floatResult  $longResult  $stringResult  $byteResult")
        println("结果:  $intResult  $floatResult  $longResult  $stringResult  $byteResult")
    }

    @Test
    fun test4Uri(){
        val build = Uri.parse("content://xxx")
            .buildUpon()
            .appendPath("YS_KEY_VALUE")
            .build()
            .buildUpon()
            .appendPath( "你好")
            .build()
        println("uri:"+build.pathSegments)
    }

    @Test
    fun test4ParseBoolean(){
        Boolean.parseBoolean("1")
        assert(Boolean.parseBoolean("1"))
    }
}