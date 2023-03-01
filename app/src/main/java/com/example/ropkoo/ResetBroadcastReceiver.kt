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
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.room.Room
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.ropkoo.DB.*
import kotlinx.coroutines.GlobalScope

import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.CountDownLatch


class ResetStepCountWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        // Get the step count
        /*val sensorManager = applicationContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val stepCountSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        val stepCount = stepCountSensor?.let { sensor ->
            val sensorEvent = CountDownLatch(1)
            var count = 0
            sensorManager.registerListener(object : SensorEventListener {
                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
                override fun onSensorChanged(event: SensorEvent?) {
                    count = event?.values?.get(0)?.toInt() ?: 0
                    sensorEvent.countDown()
                    Log.d("WORKER", "WORK")
                }
            }, sensor, SensorManager.SENSOR_DELAY_NORMAL)
            sensorEvent.await()
            count
        } ?: 0
        Log.d("WORKER", "WORK")
        // Write the step count to a file
        val file = applicationContext.getFileStreamPath("step_count.txt")
        file.outputStream().use { output ->
            output.write(stepCount.toString().toByteArray())
        }*/
        val sensorManager = applicationContext.getSystemService(Context.SENSOR_SERVICE) as? SensorManager
        val stepCountSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        var stepCount = 0

        sensorManager?.registerListener(
            object : SensorEventListener {
                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
                override fun onSensorChanged(event: SensorEvent?) {
                    event?.let {
                            stepCount = event.values[0].toInt()
                            Log.d("onReceiveSTEP", stepCount.toString())

                        Handler(Looper.getMainLooper()).postDelayed({
                            val file = File(applicationContext.filesDir, "step_count.txt")
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
        return Result.success()
    }
}
/*
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

        // Write the step count to a file

        // Schedule the alarm for the next day





        /*val data = dataDao.getProgress()
        data!!.observeForever {
            it?.let {
                // Reset your data here
            }
        }*/
    }


}*/