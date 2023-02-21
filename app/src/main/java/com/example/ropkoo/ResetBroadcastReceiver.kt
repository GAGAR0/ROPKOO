package com.example.ropkoo

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.ContentProvider
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.AsyncTask
import android.os.Handler
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.room.Room
import com.example.ropkoo.DB.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class ResetBroadcastReceiver : BroadcastReceiver(){

    override fun onReceive(context: Context?, intent: Intent?) {

        /*val database = Room.databaseBuilder(receiver, UserDatabase::class.java, "user_database")
        val dataDao = database.userDAO()

        dataDao.getCurrentSession().observeForever{
            Log.d("observeForever", it.toString())
        }
        GlobalScope.launch {
            val repository = UserRepository(context)
            repository.readAllData
        }*/
        val sensorManager = context!!.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val stepCountSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        var stepCount = 0

        sensorManager.registerListener(
            object : SensorEventListener {
                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
                override fun onSensorChanged(event: SensorEvent?) {
                    event?.let {
                        Handler().postDelayed({
                            stepCount = event!!.values[0].toInt()
                            sensorManager.unregisterListener(this)
                            Log.d("onReceiveSTEP", stepCount.toString())

                            val file = File(context.filesDir, "step_count.txt")
                            val outputStream = FileOutputStream(file)
                            outputStream.write(stepCount.toString().toByteArray())
                            outputStream.close()
                            },1000)
                    }
                }
            },
            stepCountSensor,
            SensorManager.SENSOR_DELAY_UI
        )




        /*val data = dataDao.getProgress()
        data!!.observeForever {
            it?.let {
                // Reset your data here
            }
        }*/
    }


}