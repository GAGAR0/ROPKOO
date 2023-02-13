package com.example.ropkoo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.ropkoo.DB.Progress
import com.example.ropkoo.DB.Session
import com.example.ropkoo.DB.User
import com.example.ropkoo.DB.UserViewModel
import kotlinx.android.synthetic.main.fragment_goal_progress2.*
import kotlinx.android.synthetic.main.fragment_main.*
import java.lang.Float.parseFloat


class goalProgressFragment2 : Fragment() {
    private lateinit var viewModel: UserViewModel
    var DBUserID: Int? = null
    var dailyWeight: Float? = null
    var weeklyWeight: Float? = null
    var startWeight: Float? = null
    var height: Int? = null
    var percentageProgress: Float? = null
    var empty: Boolean = true

    lateinit var IDCheck: LiveData<List<Progress>>
    lateinit var observer: Observer<List<Progress>>

    lateinit var SessionCheck: LiveData<List<Session>>
    lateinit var observerSession: Observer<List<Session>>

    lateinit var UserCheck: LiveData<List<User>>
    lateinit var observerUser: Observer<List<User>>



    override fun onStop() {
        super.onStop()
        IDCheck.removeObserver(observer)
        SessionCheck.removeObserver(observerSession)
        UserCheck.removeObserver(observerUser)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        return inflater.inflate(R.layout.fragment_goal_progress2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var ib_back: ImageButton = view.findViewById(R.id.ib_back)
        ib_back.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_goalProgressFragment2_to_goalProgressFragment)
            if (empty == true){
            exitIdCheck()
            exitSessionCheck()
            exitUserCheck()}
        }

        var bt_confirm: Button = view.findViewById(R.id.bt_confirm)
        bt_confirm.setOnClickListener {
            addWeight()
            empty = false
        }
    }

    private fun addWeight() {
        dailyWeight = parseFloat(et_current.text.toString())
        if (inputCheck(dailyWeight.toString())) {
            getSession()
        } else {
            Toast.makeText(requireContext(), "Fill out all the fields!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(dailyWeight: String): Boolean {
        return !(TextUtils.isEmpty(dailyWeight))
    }

    @SuppressLint("SuspiciousIndentation")
    private fun getSession(){
        SessionCheck = viewModel.getCurrentSession()!!
        observerSession = Observer { data ->
            val indexSession = data.toString().indexOf("loggedUser_id=")
            val endIndexSession = data.toString().indexOf(")", indexSession)
            if(indexSession != -1 && endIndexSession != -1){
                val session = data.toString().substring(indexSession + "loggedUser_id=".length, endIndexSession)
                DBUserID = session.toInt()
                getUserID(DBUserID!!)
                writeToDB(DBUserID!!)
            }
        }
        SessionCheck.observe(requireActivity(), observerSession)
    }

    private fun getUserID(user_id: Int){
        UserCheck = viewModel.getUserFromID(user_id)!!
        observerUser = Observer { data2 ->

             startWeight = data2.toString().substring(data2.toString().indexOf("weight=") + "weight=".length, data2.toString().indexOf(",", data2.toString().indexOf("weight="))).toFloat()
             height = data2.toString().substring(data2.toString().indexOf("height=") + "height=".length, data2.toString().indexOf(")", data2.toString().indexOf("height="))).toInt()

        }
        UserCheck.observe(requireActivity(), observerUser)
    }

    private fun writeToDB(user_id: Int){
        IDCheck = viewModel.getProgress(user_id)!!
        observer = Observer { dataIDe ->

            var progress_id = dataIDe.toString().substring(dataIDe.toString().indexOf("progress_id=") + "progress_id=".length, dataIDe.toString().indexOf(",", dataIDe.toString().indexOf("progress_id=")))
            var dailyCalories = dataIDe.toString().substring(dataIDe.toString().indexOf("dailyCalories=") + "dailyCalories=".length, dataIDe.toString().indexOf(",", dataIDe.toString().indexOf("dailyCalories=")))
            var stepCounter = dataIDe.toString().substring(dataIDe.toString().indexOf("stepCount=") + "stepCount=".length, dataIDe.toString().indexOf(",", dataIDe.toString().indexOf("stepCount=")))
            var waterIntake = dataIDe.toString().substring(dataIDe.toString().indexOf("waterIntake=") + "waterIntake=".length, dataIDe.toString().indexOf(",", dataIDe.toString().indexOf("waterIntake=")))
            var goalProgressPercentage = dataIDe.toString().substring(dataIDe.toString().indexOf("goalProgressPercentage=") + "goalProgressPercentage=".length, dataIDe.toString().indexOf(",", dataIDe.toString().indexOf("goalProgressPercentage=")))
             weeklyWeight = dataIDe.toString().substring(dataIDe.toString().indexOf("weeklyWeight=") + "weeklyWeight=".length, dataIDe.toString().indexOf(")", dataIDe.toString().indexOf("weeklyWeight="))).toFloat()
             Log.d("SHOWOUT1", goalProgressPercentage.toString())
            progressCalc()
            Log.d("SHOWOUT2", percentageProgress.toString())
            (requireContext() as Activity).runOnUiThread {
                val updated = Progress(progress_id.toInt(), DBUserID, dailyCalories.toInt(),stepCounter.toInt(), waterIntake.toInt(), percentageProgress!!.toInt() , dailyWeight!!, weeklyWeight!!)
                viewModel.updateWeight(updated)
            }
        }
        IDCheck.observe(requireActivity(), observer)
    }

    private fun bmi(weight: Float, height: Float): Float{
        var bmicalc = weight / ((height/100) * (height/100))
        return bmicalc
    }

    private fun currentBmi(weight: Float, height: Float): Float{
        var bmicalc = weight / ((height/100) * (height/100))
        return bmicalc
    }

    private fun progressCalc(){
        var bmi = bmi(startWeight!!.toFloat(), height!!.toFloat())
        Log.d("SHOWOUT3", bmi.toString())
        var currentBmi = currentBmi(dailyWeight!!.toFloat(), height!!.toFloat())
        Log.d("SHOWOUT4", currentBmi.toString())
        var goalbmi = bmi - 3
        Log.d("SHOWOUT5", goalbmi.toString())
        var fullpercent = bmi - goalbmi
        var yourpercent = currentBmi - goalbmi

        percentageProgress = 100 - ((yourpercent / fullpercent) * 100)
        Log.d("SHOWOUT6", percentageProgress.toString())
    }

    //**********************************************************************************************************************
    //**********************************************************************************************************************
    //**********************************************************************************************************************


    private fun exitIdCheck(){
        IDCheck = viewModel.getProgress(15)!!
        observer = Observer { dataIDe ->

        }
        IDCheck.observe(requireActivity(), observer)
    }

    private fun exitUserCheck(){
        UserCheck = viewModel.getUserFromID(15)!!
        observerUser = Observer { data2 ->
        }
        UserCheck.observe(requireActivity(), observerUser)
    }

    private fun exitSessionCheck(){
        SessionCheck = viewModel.getCurrentSession()!!
        observerSession = Observer { data ->
        }
        SessionCheck.observe(requireActivity(), observerSession)
    }


}


