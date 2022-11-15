package com.frank.testa

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ys.lib_persistence.keyValue.core.KeyValueStorage
import com.ys.lib_persistence.keyValue.sp.RemotePreferences
import com.ys.lib_persistence.keyValue.sql.KeyValueDatabase
import com.ys.lib_persistence.keyValue.sql.RemoteSql
import com.ys.lib_persistence.keyValue.sql.dao.KeyValueDao
import com.ys.lib_persistence.keyValue.sql.entity.*
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    private lateinit var keyValueDao: KeyValueDao
    private lateinit var db: KeyValueDatabase
    private val TAG: String = "TEST"

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = KeyValueDatabase.getInstance(this)

        val prefs: SharedPreferences =
            RemotePreferences(this, "com.tcn.vending.preferences", "main_prefs")

        keyValueDao = db.keyValueDao
        KeyValueStorage.init(this, packageName, "test", "testA", "flag", "这是一个测试程序")
        val remoteSql = RemoteSql(this)

        findViewById<TextView>(R.id.tvInsert).setOnClickListener {
            val startTimeMillis = System.currentTimeMillis()
            for (i in 0..80) {
                keyValueDao.put(KV_INT("TEST1$i", Random.nextInt(1000)))
                keyValueDao.getInt("TEST1$i")
                keyValueDao.put(KV_INT("TEST2$i", 1))
                keyValueDao.getInt("TEST2$i")
                keyValueDao.put(KV_Float("TEST3$i", 2f))
                keyValueDao.getFloat("TEST3$i")
                keyValueDao.put(KV_Long("TEST4$i", 3L))
                keyValueDao.getLong("TEST4$i")
                keyValueDao.put(KV_String("TEST5$i", "你好世界"))
                keyValueDao.getString("TEST5$i")
                keyValueDao.put(KV_Byte("TEST6$i", "teeedggga".toByteArray()))
                keyValueDao.getBytes("TEST6$i")

            }
            val endTimeMillis = System.currentTimeMillis()
            Log.e(TAG, "SQL插入耗时：" + (endTimeMillis - startTimeMillis))
        }

        findViewById<TextView>(R.id.tvRemoteSQLInsert).setOnClickListener {
            val startTimeMillis = System.currentTimeMillis()
            for (i in 0..80) {
                KeyValueStorage.put("TEST1$i", Random.nextInt(1000))
                KeyValueStorage.getInt("TEST1$i", 0)
                KeyValueStorage.put("TEST2$i", true)
                KeyValueStorage.getBoolean("TEST2$i", true)
                KeyValueStorage.put("TEST3$i", 2f)
                KeyValueStorage.getFloat("TEST3$i", 3f)
                KeyValueStorage.put("TEST4$i", 3L)
                KeyValueStorage.getLong("TEST4$i", 0L)
                KeyValueStorage.put("TEST5$i", "你好世界")
                KeyValueStorage.getString("TEST5$i", "哈哈")
                KeyValueStorage.put("TEST6$i", "teeedggga".toByteArray())
                KeyValueStorage.getBytes("TEST6$i")

            }
            val endTimeMillis = System.currentTimeMillis()
            Log.e(TAG, "远程SQL插入耗时：" + (endTimeMillis - startTimeMillis))
        }


        findViewById<TextView>(R.id.tvMMKVInsert).setOnClickListener {
            val startTimeMillis = System.currentTimeMillis()
            KeyValueStorage.instance.clearAll()
            val endTimeMillis = System.currentTimeMillis()
            Log.e(TAG, "远程Mmkv插入耗时：" + (endTimeMillis - startTimeMillis))
        }

        findViewById<TextView>(R.id.tvSpInsert).setOnClickListener {
            val startTimeMillis = System.currentTimeMillis()
            for (i in 0..80) {
                val putInt = prefs.edit().putInt("TEST1$i", Random.nextInt(1000)).commit()
                prefs.getInt("TEST1$i", 0)
                prefs.edit().putBoolean("TEST2$i", true).commit()
                prefs.getBoolean("TEST2$i", false)
                prefs.edit().putFloat("TEST3$i", 2f).commit()
                prefs.getFloat("TEST3$i", 3f)
                prefs.edit().putLong("TEST4$i", 3L).commit()
                prefs.getLong("TEST4$i", 0L)
                prefs.edit().putString("TEST5$i", "你好世界").commit()
                prefs.getString("TEST5$i", "哈哈")
            }
            val endTimeMillis = System.currentTimeMillis()
            Log.e(TAG, "远程SP插入耗时：" + (endTimeMillis - startTimeMillis))

            val sharedPreferences = getSharedPreferences("test", Context.MODE_PRIVATE)

        }

        val sharedPreferences = getSharedPreferences("test", Context.MODE_PRIVATE)
    }
}