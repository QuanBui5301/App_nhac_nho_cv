package com.example.appnhacnhocv

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.icu.util.Calendar
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appnhacnhocv.WorkAdapter.Companion.WorkList


class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var workAdapter: WorkAdapter
    lateinit var cursorViewModel: CursorViewModel
    private var mHandler: Handler = Handler()
    private var runnable: Runnable? = null
    val uri : Uri = Uri.parse("content://com.example.appquanlycongviec.WorkContentProvider/tbl_work")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        cursorViewModel = ViewModelProvider(this).get(CursorViewModel::class.java)
        var intent : Intent? = getPackageManager().getLaunchIntentForPackage("com.example.appquanlycongviec")
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            intent.putExtra("isDone", 1)
            startActivity(intent)
            LoadData()
        } else {
            Toast.makeText(this, "Ứng dụng nguồn chưa được cài đặt sẵn!", Toast.LENGTH_SHORT).show()
        }
        alarmCreate()
    }


    private fun LoadData() {
        runnable = object : Runnable {
            override fun run() {
                val cursor : Cursor? = contentResolver.query(uri, arrayOf("id", "name", "time"), null, null, null)
                if (cursor != null) {
                    WorkList = mutableListOf()
                    cursor.moveToFirst()
                    while (!cursor.isAfterLast) {
                        WorkList.add(WorkList.size, Work(cursor.getInt(0), cursor.getString(1), cursor.getString(2)))
                        cursor.moveToNext()
                    }
                    workAdapter = WorkAdapter()
                    recyclerView.layoutManager = LinearLayoutManager(applicationContext)
                    recyclerView.adapter = workAdapter
                    mHandler.removeCallbacks(runnable!!)
                }
                mHandler.postDelayed(this, 1000)
            }
        }
        runnable!!.run()
    }
    fun alarmCreate() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val notificationIntent = Intent(this, AlarmBroadcast::class.java)
        var bundle = Bundle()
        val pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, 0)

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 6)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }
}