package com.example.ropkoo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.ropkoo.DB.Progress
import com.example.ropkoo.DB.Session
import com.example.ropkoo.DB.User
import com.example.ropkoo.DB.UserViewModel
import kotlinx.android.synthetic.main.fragment_goal_progress.*
import kotlinx.android.synthetic.main.fragment_hydration.*
import kotlinx.coroutines.delay
import org.w3c.dom.Text
import java.util.*


class hydrationFragment : Fragment() {
    private lateinit var viewModel: UserViewModel
    var progress_id: String? = null
    var dailyCalories: String? = null
    var goalProgressPercentage: String? = null
    var stepCounter: String? = null
    var waterIntake: Int? = 0
    var dailyWeight: String? = null
    var weeklyWeight: String? = null
    var finalWater: Int? = 0
    var date: Long? = null
    var session: String? = null
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
        return inflater.inflate(R.layout.fragment_hydration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getSession()
        Log.d("hydration", "enter")

        Handler().postDelayed({ finalWater = waterIntake
                                tv_num.setText(finalWater!!.toString()) }, 200)

        Log.d("hydration", "setText")

        /*Handler().postDelayed({ finalWater = waterIntake
            tv_num.setText(finalWater!!.toString()) }, 200)*/

        var ib_back : ImageButton = view.findViewById(R.id.ib_back)
        ib_back.setOnClickListener{
            (requireContext() as Activity).runOnUiThread {
                Log.d("hydration", "EXIT")
                val updated = Progress(progress_id!!.toInt(), DBUserID, dailyCalories!!.toInt(),stepCounter!!.toInt(), finalWater!!, goalProgressPercentage!!.toInt() , dailyWeight!!.toFloat(), weeklyWeight!!.toFloat(), date)
                viewModel.updateWeight(updated)
            }
            Handler().postDelayed({ Navigation.findNavController(view).navigate(R.id.action_hydrationFragment_to_menuFragment) }, 200)
                exitSessionCheck()
                exitProgressCheck()
        }

        var addbtn : Button = view.findViewById(R.id.addbtn)
        addbtn.setOnClickListener{
            addSmall()
            Log.d("hydration", "btnsmall")
            tv_num.setText(finalWater!!.toString())

        }

        var addbtn2 : Button = view.findViewById(R.id.addbtn2)
        addbtn2.setOnClickListener{
            addBig()
            Log.d("hydration", "btnbig")
            tv_num.setText(finalWater!!.toString())
        }


    }

    @SuppressLint("SuspiciousIndentation")
    private fun getSession(){
        SessionCheck = viewModel.getCurrentSession()!!
        observerSession = Observer { data ->
            val indexSession = data.toString().indexOf("loggedUser_id=")
            val endIndexSession = data.toString().indexOf(")", indexSession)
            if(indexSession != -1 && endIndexSession != -1){
                session = data.toString().substring(indexSession + "loggedUser_id=".length, endIndexSession)
                if (session != null && session != "null"){
            DBUserID = session!!.toInt()
            writeToDB(DBUserID!!)
        }}}
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

    private fun addSmall(){
        Log.d("hydration", "small")
        finalWater = finalWater!! + 300
    }

    private fun addBig(){
        Log.d("hydration", "big")
        finalWater = finalWater!! + 500

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
        Log.d("hydration", "exitSessionCheck")
        observerSession = Observer { data ->
        }
        SessionCheck.observe(requireActivity(), observerSession)
    }


}