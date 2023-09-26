package com.example.appnhacnhocv

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.appnhacnhocv.MyNotification.Companion.CHANNEL_ID
import com.example.appnhacnhocv.WorkAdapter.Companion.WorkList
import java.util.*

class AlarmBroadcast : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        Log.d("Alarm", "Start")
        val timeCurent = Calendar.getInstance()
        var dayCurrent = timeCurent.get(Calendar.DAY_OF_MONTH)
        var monthCurrent = timeCurent.get(Calendar.MONTH)
        var yearCurrent = timeCurent.get(Calendar.YEAR)

        val isDay : Int =  boolToInt(dayCurrent<10)
        val isMonth : Int =  boolToInt(monthCurrent<9)
        val isTime = "$isMonth$isDay"
        val timeHour : String = timeFormat(isTime, dayCurrent, monthCurrent, yearCurrent)
        var list : String = ""
        var work : String = ""
        for (i in 0..WorkList.size-1) {
            if (timeHour == WorkList[i].time) {
                list += WorkList[i].work + ", "
            }
        }

        if (list == "") {
            work = "Không có ghi chú công việc"
        } else work = list

        val notification = NotificationCompat.Builder(p0!!, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_work_history_24)
            .setContentTitle("Công việc ngày $timeHour:")
            .setContentText(work)
            .build()
        val manager = p0.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(0, notification)
    }
    private fun boolToInt(item : Boolean) : Int {
        return if (item) {
            1
        } else 0
    }
    fun timeFormat(isTime : String, mSaveDay : Int, mSaveMonth : Int, mSaveYear: Int) : String {
        when (isTime) {
            "00" -> return "$mSaveDay/${mSaveMonth+1}/$mSaveYear"
            "10" -> return "$mSaveDay/0${mSaveMonth+1}/$mSaveYear"
            "01" -> return "0$mSaveDay/${mSaveMonth+1}/$mSaveYear"
            "11" -> return "0$mSaveDay/0${mSaveMonth+1}/$mSaveYear"
            else -> return "$mSaveDay/${mSaveMonth+1}/$mSaveYear"
        }
    }
}