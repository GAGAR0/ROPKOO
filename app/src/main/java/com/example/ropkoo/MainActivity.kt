package com.example.ropkoo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.ropkoo.DB.Goals
import com.example.ropkoo.DB.User
import com.example.ropkoo.DB.UserViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Hydration Reminder"
            val descriptionText = "Reminder to drink water"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("notifications", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        viewModel.addGoals(Goals(1 ,"Skinny", "LoseWeight"))
        viewModel.addGoals(Goals(2 ,"Skinny", "GainWeight"))
        viewModel.addGoals(Goals(3 ,"Skinny", "MaintainWeight"))
        viewModel.addGoals(Goals(4 ,"Skinny", "GainMuscles"))
        viewModel.addGoals(Goals(5 ,"Normal", "LoseWeight"))
        viewModel.addGoals(Goals(6 ,"Normal", "GainWeight"))
        viewModel.addGoals(Goals(7 ,"Normal", "MaintainWeight"))
        viewModel.addGoals(Goals(8 ,"Normal", "GainMuscles"))
        viewModel.addGoals(Goals(9 ,"Plump", "LoseWeight"))
        viewModel.addGoals(Goals(10 ,"Plump", "GainWeight"))
        viewModel.addGoals(Goals(11 ,"Plump", "MaintainWeight"))
        viewModel.addGoals(Goals(12 ,"Plump", "GainMuscles"))
        viewModel.addGoals(Goals(13 ,"Overweight", "LoseWeight"))
        viewModel.addGoals(Goals(14 ,"Overweight", "GainWeight"))
        viewModel.addGoals(Goals(15 ,"Overweight", "MaintainWeight"))
        viewModel.addGoals(Goals(16 ,"Overweight", "GainMuscles"))
    }


}

// setupActionBarWithNavController(findNavController(R.id.fragmentContainerView))

/*override fun onSupportNavigateUp(): Boolean {
      val navController: NavController = findNavController(R.id.fragmentContainerView)
      val hostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
      val  controller = hostFragment.navController
      return navController.navigateUp() || super.onSupportNavigateUp()
  }*/