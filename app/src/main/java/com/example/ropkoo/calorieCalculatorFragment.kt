package com.example.ropkoo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.ropkoo.DB.Progress
import com.example.ropkoo.DB.Session
import com.example.ropkoo.DB.UserViewModel
import kotlinx.android.synthetic.main.fragment_calorie_calculator.*
import kotlinx.android.synthetic.main.fragment_goal_progress2.*
import kotlinx.android.synthetic.main.fragment_hydration.*
import java.lang.Float
import java.util.*


class calorieCalculatorFragment : Fragment() {
    private lateinit var viewModel: UserViewModel
    var progress_id: String? = null
    var dailyCalories: String? = null
    var goalProgressPercentage: String? = null
    var stepCounter: String? = null
    var waterIntake: Int? = null
    var dailyWeight: String? = null
    var weeklyWeight: String? = null
    var calorieCount: Int? = 0
    var goalCalorieCount: Int? = 2000
    var empty: Boolean = true
    var date: Long? = null
    var dateToday: Long? = null
    var session: Int? = null
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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        return inflater.inflate(R.layout.fragment_calorie_calculator, container, false)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        greennum.setText(goalCalorieCount.toString())



        getSession()

        Handler().postDelayed({
            Log.d("date", date.toString())
            Log.d("date", dateToday.toString())
            getDay()
            if(date!!.toString().compareTo(dateToday!!.toString()) != 0){
            date = dateToday
            calorieCount = 0
                (requireContext() as Activity).runOnUiThread {
                    val updated = Progress(progress_id!!.toInt(), DBUserID, calorieCount!!.toInt(),stepCounter!!.toInt(), waterIntake!!, goalProgressPercentage!!.toInt() , dailyWeight!!.toFloat(), weeklyWeight!!.toFloat(), date)
                    viewModel.updateWeight(updated)
                }
                } }, 200)


        Handler().postDelayed({ rednum.setText(calorieCount.toString()) }, 200)


        var ib_back : ImageButton = view.findViewById(R.id.ib_back)
        ib_back.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_calorieCalculatorFragment_to_menuFragment)
            if (empty == true){
            exitProgressCheck()
            exitSessionCheck()}
        }

        var addcalories: Button = view.findViewById(R.id.addcalories)
        addcalories.setOnClickListener {
            empty = false
            addCalories()
            rednum.setText(calorieCount.toString())
        }
    }


    private fun addCalories() {
        var calories = enter.text.toString()
        if(inputCheck(calories)) {
            dailyCalories = calories
            caloriesAdd()
            (requireContext() as Activity).runOnUiThread {
                val updated = Progress(progress_id!!.toInt(), DBUserID, calorieCount!!.toInt(),stepCounter!!.toInt(), waterIntake!!, goalProgressPercentage!!.toInt() , dailyWeight!!.toFloat(), weeklyWeight!!.toFloat(), date)
                viewModel.updateWeight(updated)
            }
        } else {
            Toast.makeText(requireContext(), "Fill out all the fields!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(dailyCalories: String): Boolean {
        return !(TextUtils.isEmpty(dailyCalories.trim()))
    }

    @SuppressLint("SuspiciousIndentation")
    private fun getSession(){
        SessionCheck = viewModel.getCurrentSession()!!
        observerSession = Observer { data ->
            val indexSession = data.toString().indexOf("loggedUser_id=")
            val endIndexSession = data.toString().indexOf(")", indexSession)
            session = data.toString().substring(indexSession + "loggedUser_id=".length, endIndexSession).toInt()

            /*val indexDate = data.toString().indexOf("date=")
            val endIndexDate = data.toString().indexOf(")", indexDate)
            Log.d("dateIndex", indexDate.toString())
             date = data.toString().substring(indexDate + "date=".length, endIndexDate).toLong()
            Log.d("dateSource", date.toString())*/

            DBUserID = session!!
            writeToDB(DBUserID!!)
        }
        SessionCheck.observe(requireActivity(), observerSession)
    }

    private fun writeToDB(user_id: Int){
        ProgressCheck = viewModel.getProgress(user_id)!!
        observerProgress = Observer { dataIDe ->
            calorieCount = dataIDe.toString().substring(dataIDe.toString().indexOf("dailyCalories=") + "dailyCalories=".length, dataIDe.toString().indexOf(",", dataIDe.toString().indexOf("dailyCalories="))).toInt()
            progress_id = dataIDe.toString().substring(dataIDe.toString().indexOf("progress_id=") + "progress_id=".length, dataIDe.toString().indexOf(",", dataIDe.toString().indexOf("progress_id=")))
            goalProgressPercentage = dataIDe.toString().substring(dataIDe.toString().indexOf("goalProgressPercentage=") + "goalProgressPercentage=".length, dataIDe.toString().indexOf(",", dataIDe.toString().indexOf("goalProgressPercentage=")))
            stepCounter = dataIDe.toString().substring(dataIDe.toString().indexOf("stepCount=") + "stepCount=".length, dataIDe.toString().indexOf(",", dataIDe.toString().indexOf("stepCount=")))
            waterIntake = dataIDe.toString().substring(dataIDe.toString().indexOf("waterIntake=") + "waterIntake=".length, dataIDe.toString().indexOf(",", dataIDe.toString().indexOf("waterIntake="))).toInt()
            dailyWeight = dataIDe.toString().substring(dataIDe.toString().indexOf("dailyWeight=") + "dailyWeight=".length, dataIDe.toString().indexOf(",", dataIDe.toString().indexOf("dailyWeight=")))
            date = dataIDe.toString().substring(dataIDe.toString().indexOf("date=") + "date=".length, dataIDe.toString().indexOf(")", dataIDe.toString().indexOf("date="))).toLong()
            weeklyWeight = dataIDe.toString().substring(dataIDe.toString().indexOf("weeklyWeight=") + "weeklyWeight=".length, dataIDe.toString().indexOf(",", dataIDe.toString().indexOf("weeklyWeight=")))
        }
        ProgressCheck.observe(requireActivity(), observerProgress)
    }

    private fun caloriesAdd(){
         calorieCount = calorieCount!!.toInt() + dailyCalories!!.toInt()
    }


    //************************************************************************************************************
    //************************************************************************************************************
    //************************************************************************************************************


    private fun exitProgressCheck(){
        ProgressCheck = viewModel.getProgress(15)!!
        observerProgress = Observer { data2 ->
        }
        ProgressCheck.observe(requireActivity(), observerProgress)
    }

    private fun exitSessionCheck(){
        SessionCheck = viewModel.getCurrentSession()!!
        observerSession = Observer { data ->
        }
        SessionCheck.observe(requireActivity(), observerSession)
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