package com.example.ropkoo

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.room.Room
import com.example.ropkoo.DB.Progress
import com.example.ropkoo.DB.Session
import com.example.ropkoo.DB.UserDatabase
import com.example.ropkoo.DB.UserViewModel
import kotlinx.android.synthetic.main.fragment_step_counter.*
import org.w3c.dom.Text
import java.util.*


/*class stepCounterFragment : Fragment() {

    private var sensorManager: SensorManager? = null

    // variable gives the running status
    private var running = false

    // variable counts total steps
    private var totalSteps = 0f

    val ACTIVITY_RECOGNITION_REQUEST_CODE = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        sensorManager = getSystemService(SENSOR_SERVICE.Context) as SensorManager
        return inflater.inflate(R.layout.fragment_step_counter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var ib_back : ImageButton = view.findViewById(R.id.ib_back)
        ib_back.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_stepCounterFragment_to_menuFragment)
        }
    }
}*/

class stepCounterFragment : Fragment(), SensorEventListener {

    private lateinit var viewModel: UserViewModel
    var progress_id: String? = null
    var dailyCalories: String? = null
    var goalProgressPercentage: String? = null
    var stepCounter: String? = null
    var waterIntake: Int? = 0
    var dailyWeight: String? = null
    var weeklyWeight: String? = null
    var DBUserID: Int? = null
    lateinit var SessionCheck: LiveData<List<Session>>
    lateinit var observerSession: Observer<List<Session>>
    lateinit var ProgressCheck: LiveData<List<Progress>>
    lateinit var observerProgress: Observer<List<Progress>>
   // lateinit var observerReset: Observer<List<Progress>>
    //************************************************
    //timer
    private lateinit var alarmManager: AlarmManager
   /* private lateinit var resetStepCountPendingIntent: PendingIntent*/




    override fun onStop() {
        super.onStop()
        SessionCheck.removeObserver(observerSession)
        ProgressCheck.removeObserver(observerProgress)
       // ProgressCheck.removeObserver(observerReset)
    }

    /*override fun onDestroyView() {
        Log.d("onReceive", "onDestroy")
        alarmManager.cancel(resetStepCountPendingIntent)
        super.onDestroyView()
    }*/

    private var sensorManager: SensorManager? = null

    private var running = false

    private var totalSteps = 0f

    val ACTIVITY_RECOGNITION_REQUEST_CODE = 100
    var stepsCounted: Int? = null
    var currentSteps: Int? = null
    var finalSteps: Int? = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /*val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, 19)
        calendar.set(Calendar.MINUTE, 5)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        //calendar.add(Calendar.DAY_OF_YEAR, )
        val midnightMillis = calendar.timeInMillis
        Log.d("midnight", midnightMillis.toString())
        val delayMillis = midnightMillis - System.currentTimeMillis()
        Handler(Looper.getMainLooper()).postDelayed({
            resetStepCount()
        }, delayMillis)*/

        alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        /*resetStepCountPendingIntent = getResetStepCountPendingIntent(requireContext())*/

        /*val database = Room.databaseBuilder(requireContext(), UserDatabase::class.java, "user_database").build()
        val dataDao = database.userDAO()

        dataDao.getCurrentSession().observeForever{
            Log.d("observeForever", it.toString())
        }*/

       scheduleResetData(requireContext())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), ACTIVITY_RECOGNITION_REQUEST_CODE)
            }
        }

        sensorManager = requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        return inflater.inflate(R.layout.fragment_step_counter, container, false)
    }

    override fun onResume() {

        super.onResume()
        running = true

        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepSensor == null) {
            Toast.makeText(requireContext(), "No sensor detected on this device", Toast.LENGTH_SHORT).show()
        } else {
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getSession()
        Handler().postDelayed({ stepNum.setText("$finalSteps")  }, 150)

        var ib_back : ImageButton = view.findViewById(R.id.ib_back)
        ib_back.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_stepCounterFragment_to_menuFragment)
        }
    }

    override fun onPause() {
        super.onPause()
        running = false
        sensorManager?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {


        if (running) {

            totalSteps = event!!.values[0]

            currentSteps = totalSteps.toInt()
            getSession()


            stepNum.setText("$finalSteps")
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    @SuppressLint("SuspiciousIndentation")
    private fun getSession(){
        SessionCheck = viewModel.getCurrentSession()!!
        observerSession = Observer { data ->
            val indexSession = data.toString().indexOf("loggedUser_id=")
            val endIndexSession = data.toString().indexOf(")", indexSession)
            val session = data.toString().substring(indexSession + "loggedUser_id=".length, endIndexSession)
            DBUserID = session.toInt()
            writeToDB(DBUserID!!)

        }
        SessionCheck.observe(requireActivity(), observerSession)
    }

    private fun writeToDB(user_id: Int){
        ProgressCheck = viewModel.getProgress(user_id)!!
        observerProgress = Observer { dataIDe ->
            progress_id = dataIDe.toString().substring(dataIDe.toString().indexOf("progress_id=") + "progress_id=".length, dataIDe.toString().indexOf(",", dataIDe.toString().indexOf("progress_id=")))
            dailyCalories = dataIDe.toString().substring(dataIDe.toString().indexOf("dailyCalories=") + "dailyCalories=".length, dataIDe.toString().indexOf(",", dataIDe.toString().indexOf("dailyCalories=")))
            goalProgressPercentage = dataIDe.toString().substring(dataIDe.toString().indexOf("goalProgressPercentage=") + "goalProgressPercentage=".length, dataIDe.toString().indexOf(",", dataIDe.toString().indexOf("goalProgressPercentage=")))
            stepCounter = dataIDe.toString().substring(dataIDe.toString().indexOf("stepCount=") + "stepCount=".length, dataIDe.toString().indexOf(",", dataIDe.toString().indexOf("stepCount=")))
            waterIntake = dataIDe.toString().substring(dataIDe.toString().indexOf("waterIntake=") + "waterIntake=".length, dataIDe.toString().indexOf(",", dataIDe.toString().indexOf("waterIntake="))).toInt()
            dailyWeight = dataIDe.toString().substring(dataIDe.toString().indexOf("dailyWeight=") + "dailyWeight=".length, dataIDe.toString().indexOf(",", dataIDe.toString().indexOf("dailyWeight=")))
            weeklyWeight = dataIDe.toString().substring(dataIDe.toString().indexOf("weeklyWeight=") + "weeklyWeight=".length, dataIDe.toString().indexOf(")", dataIDe.toString().indexOf("weeklyWeight=")))
            if (stepCounter!!.toInt() == 0){
            (requireContext() as Activity).runOnUiThread {
                val updated = Progress(progress_id!!.toInt(), DBUserID, dailyCalories!!.toInt(),currentSteps!!.toInt(), waterIntake!!.toInt(), goalProgressPercentage!!.toInt() , dailyWeight!!.toFloat(), weeklyWeight!!.toFloat())
                viewModel.updateWeight(updated)
                stepsCounted = stepCounter!!.toInt()
                finalSteps = currentSteps!!-stepsCounted!!
                }
            }
            else{stepsCounted = stepCounter!!.toInt()
                finalSteps = currentSteps!!-stepsCounted!!
            }
        }
        ProgressCheck.observe(requireActivity(), observerProgress)
    }

    /*@SuppressLint("SuspiciousIndentation")
    private fun resetStepCount(){
        ProgressCheck = viewModel.getProgress(DBUserID!!)!!
        observerReset = Observer { dataIDe ->
            progress_id = dataIDe.toString().substring(dataIDe.toString().indexOf("progress_id=") + "progress_id=".length, dataIDe.toString().indexOf(",", dataIDe.toString().indexOf("progress_id=")))
            dailyCalories = dataIDe.toString().substring(dataIDe.toString().indexOf("dailyCalories=") + "dailyCalories=".length, dataIDe.toString().indexOf(",", dataIDe.toString().indexOf("dailyCalories=")))
            goalProgressPercentage = dataIDe.toString().substring(dataIDe.toString().indexOf("goalProgressPercentage=") + "goalProgressPercentage=".length, dataIDe.toString().indexOf(",", dataIDe.toString().indexOf("goalProgressPercentage=")))
            stepCounter = dataIDe.toString().substring(dataIDe.toString().indexOf("stepCount=") + "stepCount=".length, dataIDe.toString().indexOf(",", dataIDe.toString().indexOf("stepCount=")))
            waterIntake = dataIDe.toString().substring(dataIDe.toString().indexOf("waterIntake=") + "waterIntake=".length, dataIDe.toString().indexOf(",", dataIDe.toString().indexOf("waterIntake="))).toInt()
            dailyWeight = dataIDe.toString().substring(dataIDe.toString().indexOf("dailyWeight=") + "dailyWeight=".length, dataIDe.toString().indexOf(",", dataIDe.toString().indexOf("dailyWeight=")))
            weeklyWeight = dataIDe.toString().substring(dataIDe.toString().indexOf("weeklyWeight=") + "weeklyWeight=".length, dataIDe.toString().indexOf(")", dataIDe.toString().indexOf("weeklyWeight=")))
                (requireContext() as Activity).runOnUiThread {
                    val updated = Progress(progress_id!!.toInt(), DBUserID, dailyCalories!!.toInt(),currentSteps!!.toInt(), waterIntake!!.toInt(), goalProgressPercentage!!.toInt() , dailyWeight!!.toFloat(), weeklyWeight!!.toFloat())
                    viewModel.updateWeight(updated)
                    stepsCounted = stepCounter!!.toInt()
                    finalSteps = currentSteps!!-stepsCounted!!
                }
        }
        ProgressCheck.observe(requireActivity(), observerReset)
    }*/

    /*companion object{
    const val RESET_STEP_COUNT_ACTION = "com.example.app.ropkoo"
    private fun getResetStepCountPendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, ResetBroadcastReceiver::class.java).apply {
            action = RESET_STEP_COUNT_ACTION
        }
        Log.d("onReceive", "companion")
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
    }}*/

    fun scheduleResetData(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ResetBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        Log.d("onReceive", "scheduleReset")
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 18)
            set(Calendar.MINUTE, 47)
            set(Calendar.SECOND, 0)
            //add(Calendar.DAY_OF_YEAR, 1)
        }

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

}