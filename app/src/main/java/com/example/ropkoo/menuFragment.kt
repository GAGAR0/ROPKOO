package com.example.ropkoo

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Layout.Directions
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.ropkoo.DB.Progress
import com.example.ropkoo.DB.Session
import com.example.ropkoo.DB.User
import com.example.ropkoo.DB.UserViewModel
import java.util.*
import java.util.concurrent.TimeUnit


class menuFragment : Fragment() {
    var userId: Int? = null
    var plan: Int? = null
    var username: String? = null
    var age: Int? = null
    var weight: Float? = null
    var height: Int? = null
    var gender: String? = null
    var email: String? = null

    var progress_id: String? = null
    var dailyCalories: String? = null
    var goalProgressPercentage: String? = null
    var stepCounter: String? = null
    var waterIntake: Int? = 0
    var dailyWeight: String? = null
    var weeklyWeight: String? = null
    var date: Long? = null
    var dateToday: Long? = null
    var DBUserID: Int? = null
    lateinit var SessionCheck: LiveData<List<Session>>
    lateinit var observerSession: Observer<List<Session>>
    lateinit var ProgressCheck: LiveData<List<Progress>>
    lateinit var observerProgress: Observer<List<Progress>>

    override fun onStop() {
        super.onStop()
        SessionCheck.removeObserver(observerSession)
        ProgressCheck.removeObserver(observerProgress)
    }

    private lateinit var alarmManager: AlarmManager
   /* lateinit var getUserId: String
    lateinit var observer: Observer<String>*/

   /* override fun onStop() {
        super.onStop()
        viewModel.userIdModel.removeObserver(observer)
    }*/
    private val args by navArgs<menuFragmentArgs>()

    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hydrationNotification(requireContext())

        getSession()
        Handler().postDelayed({
            getDay()
            if(date!!.toString().compareTo(dateToday!!.toString()) != 0){
                date = dateToday
                (requireContext() as Activity).runOnUiThread {
                    val updated = Progress(progress_id!!.toInt(), DBUserID, 0,stepCounter!!.toInt(), 0, goalProgressPercentage!!.toInt() , dailyWeight!!.toFloat(), weeklyWeight!!.toFloat(), date)
                    viewModel.updateWeight(updated)
                }
            } }, 200)

        Log.d("menu1> ", "1")
        setFragmentResultListener("MF") { _, bundle ->
            Log.d("menu1> ", "2")
            //if(bundle != null) {
            Log.d("menu1> ", "3")
            userId = bundle.getString("DBUserID")!!.toInt()
            username = bundle.getString("DBUsername")!!
            plan = bundle.getString("DBPlan")!!.toInt()
            age = bundle.getString("DBAge")!!.toInt()
            weight = bundle.getString("DBWeight")!!.toFloat()
            height = bundle.getString("DBHeight")!!.toInt()
            gender = bundle.getString("DBGender")!!
            //}
        }
        //getUserId = viewModel.userIdModel.value.toString()
        //viewModel.userIdModel.value = userId.toString()
        //Log.d("viewModelUserID1> ", getUserId)
        //getUserId = viewModel.userIdModel.value!!
        //Log.d("viewModelUserID> ", getUserId)
        /*getUserId = viewModel.userIdModel.value.toString()
        Log.d("viewModelIDFIRST>", getUserId)*/
        /*viewModel.userIdModel.value = userId.toString()
        observer = Observer { data ->
            getUserId = data
            Log.d("viewModelDATA>", data)
            getUserId = viewModel.userIdModel.value.toString()
            Log.d("viewModelUserID3> ", getUserId)
        }
        viewModel.userIdModel.observe(requireActivity(), observer)*/
        /*getUserId.observe(requireActivity(), observer)*/
        /*var userIdView: String? = viewModel.userIdModel.value
        viewModel.userIdModel.observe(viewLifecycleOwner, Observer { data ->
            Log.d("viewModelUserID> ", data)
            userIdView = data
        })*/
        /*getUserId.observe(requireActivity(), observer)*/
        /*userId = args.user.user_id!!.toInt()
        username = args.user.name!!
        plan = args.user.goals_id!!.toInt()
        age = args.user.age!!.toInt()
        weight = args.user.weight!!.toFloat()
        height = args.user.height!!.toInt()
        gender = args.user.gender!!*/
        Log.d("menu1> ", "5")
        var iv_mainLogo : ImageButton = view.findViewById(R.id.iv_mainLogo)
        iv_mainLogo.setOnClickListener{
            var user = User(userId, plan, username, null, null, gender, age, weight, height)
            val action = menuFragmentDirections.actionMenuFragmentToProfileFragment(user)
            Navigation.findNavController(view).navigate(action)
        }
        var btn_main1 : Button = view.findViewById(R.id.btn_main1)
        btn_main1.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_menuFragment_to_calorieCalculatorFragment)
        }
        var btn_main2 : Button = view.findViewById(R.id.btn_main2)
        btn_main2.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_menuFragment_to_goalProgressFragment)
        }
        var btn_main3 : Button = view.findViewById(R.id.btn_main3)
        btn_main3.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_menuFragment_to_stepCounterFragment)
        }
        var btn_main4 : Button = view.findViewById(R.id.btn_main4)
        btn_main4.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_menuFragment_to_hydrationFragment)
        }

    }

    fun hydrationNotification(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        Log.d("onReceive", "scheduleReset")
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val now = System.currentTimeMillis()
        val startMillis = calendar.timeInMillis
        val endMillis = startMillis + TimeUnit.HOURS.toMillis(12)

        if (now < startMillis) {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                startMillis,
                pendingIntent
            )
        }

        var nextMillis = startMillis + TimeUnit.HOURS.toMillis(3)
        while (nextMillis < endMillis) {
            if (nextMillis > now) {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    nextMillis,
                    pendingIntent
                )
            }
            nextMillis += TimeUnit.HOURS.toMillis(3)
        }

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
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
        Log.d("hydration", "1")
        observerProgress = Observer { dataIDe ->
            progress_id = dataIDe.toString().substring(dataIDe.toString().indexOf("progress_id=") + "progress_id=".length, dataIDe.toString().indexOf(",", dataIDe.toString().indexOf("progress_id=")))
            dailyCalories = dataIDe.toString().substring(dataIDe.toString().indexOf("dailyCalories=") + "dailyCalories=".length, dataIDe.toString().indexOf(",", dataIDe.toString().indexOf("dailyCalories=")))
            goalProgressPercentage = dataIDe.toString().substring(dataIDe.toString().indexOf("goalProgressPercentage=") + "goalProgressPercentage=".length, dataIDe.toString().indexOf(",", dataIDe.toString().indexOf("goalProgressPercentage=")))
            stepCounter = dataIDe.toString().substring(dataIDe.toString().indexOf("stepCount=") + "stepCount=".length, dataIDe.toString().indexOf(",", dataIDe.toString().indexOf("stepCount=")))
            waterIntake = dataIDe.toString().substring(dataIDe.toString().indexOf("waterIntake=") + "waterIntake=".length, dataIDe.toString().indexOf(",", dataIDe.toString().indexOf("waterIntake="))).toInt()
            dailyWeight = dataIDe.toString().substring(dataIDe.toString().indexOf("dailyWeight=") + "dailyWeight=".length, dataIDe.toString().indexOf(",", dataIDe.toString().indexOf("dailyWeight=")))
            weeklyWeight = dataIDe.toString().substring(dataIDe.toString().indexOf("weeklyWeight=") + "weeklyWeight=".length, dataIDe.toString().indexOf(",", dataIDe.toString().indexOf("weeklyWeight=")))
            date = dataIDe.toString().substring(dataIDe.toString().indexOf("date=") + "date=".length, dataIDe.toString().indexOf(")", dataIDe.toString().indexOf("date="))).toLong()

            Log.d("hydration", "3")
        }
        Log.d("hydration", "2")
        ProgressCheck.observe(requireActivity(), observerProgress)
    }

    @SuppressLint("SuspiciousIndentation")
    private fun getDay(){
        val today = Calendar.getInstance()
        today.set(Calendar.HOUR_OF_DAY, 0)
        today.set(Calendar.MINUTE, 0)
        today.set(Calendar.SECOND, 0)
        today.set(Calendar.MILLISECOND, 0)
        val date = today.time
        dateToday = date.time
    }



}