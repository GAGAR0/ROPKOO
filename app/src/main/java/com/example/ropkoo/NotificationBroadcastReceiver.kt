package com.example.ropkoo

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.content.ContentProvider
import android.os.AsyncTask
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.room.Room
import com.example.ropkoo.DB.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NotificationBroadcastReceiver : BroadcastReceiver() {

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
        val builder = NotificationCompat.Builder(context!!, "notifications")
            .setSmallIcon(R.drawable.koachlogoblack)
            .setContentTitle("Reminder to drink some water!")
            .setStyle(NotificationCompat.BigTextStyle().bigText("Open the application to check your progress!"))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .addAction(R.drawable.koachlogoblack, "Open", PendingIntent.getActivity(context, 0, Intent(context, MainActivity::class.java), 0))

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(1, builder.build())

        Log.d("onReceive", "success")


        /*val data = dataDao.getProgress()
        data!!.observeForever {
            it?.let {
                // Reset your data here
            }
        }*/
    }
}