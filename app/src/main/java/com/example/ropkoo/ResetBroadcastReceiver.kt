package com.example.ropkoo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.room.Room
import com.example.ropkoo.DB.UserDAO
import com.example.ropkoo.DB.UserDatabase
import com.example.ropkoo.DB.UserViewModel

class ResetBroadcastReceiver : BroadcastReceiver(){

    override fun onReceive(context: Context?, intent: Intent?) {


        Log.d("onReceive", "success")


        /*val data = dataDao.getProgress()
        data!!.observeForever {
            it?.let {
                // Reset your data here
            }
        }*/
    }


}