package com.example.ropkoo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.system.Os.remove
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ropkoo.DB.Progress
import com.example.ropkoo.DB.Session
import com.example.ropkoo.DB.User
import com.example.ropkoo.DB.UserViewModel
import kotlinx.android.synthetic.main.fragment_hydration.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_profile.*
import org.w3c.dom.Text


class profileFragment : Fragment() {


    var DBUsername: String? = null
    var DBAge: Int? = null
    var DBHeight: Int? = null
    var DBWeight: Float? = null
    var DBGender: String? = null
    var DBPlan: Int? = null
    var session: String? = null
    var dailyWeight: String? = null

    lateinit var SessionCheck: LiveData<List<Session>>
    lateinit var observer: Observer<List<Session>>

    lateinit var ProgressCheck: LiveData<List<Progress>>
    lateinit var observerProgress: Observer<List<Progress>>

    lateinit var UserCheck: LiveData<List<User>>
    lateinit var observerUser: Observer<List<User>>
    private lateinit var viewModel: UserViewModel

    override fun onStop() {
        super.onStop()
        SessionCheck.removeObserver(observer)
        UserCheck.removeObserver(observerUser)
        ProgressCheck.removeObserver(observerProgress)
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            getSession()

            var ib_back: ImageButton = view.findViewById(R.id.ib_back)
            ib_back.setOnClickListener {
                Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_menuFragment)
            }

            var btn_signOut: Button = view.findViewById(R.id.btn_signOut)
            btn_signOut.setOnClickListener {
                var session = Session(1, 0, session!!.toInt())
                viewModel.updateSession(session)
                Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_mainFragment)

            }

        }


    private fun calculate(weight: Float, height: Float): Float{
        val bmicalc = weight / ((height/100) * (height/100))
        return bmicalc
    }

    private fun setText(){
        name.setText(DBUsername)
        age.setText(DBAge.toString())
        height.setText(DBHeight.toString())
        weight.setText(dailyWeight.toString())
        gender.setText(DBGender)
        plan.setText(plan(DBPlan))
        val result = calculate(dailyWeight!!.toFloat(), DBHeight!!.toFloat())
        BMI.setText(String.format("%.1f",result))
    }

    private fun plan(planID: Int?): String{
        lateinit var planResult: String
        if (planID in 1..4){
            val firstPart = "Skinny"
            if (planID == 1){
                val secondPart = "Lose Weight"
                planResult = secondPart
            }
            else if (planID == 2){
                val secondPart = "Gain Weight"
                planResult = secondPart
            }
            else if (planID == 3){
                val secondPart = "Maintain Weight"
                planResult = secondPart
            }
            else{
                val secondPart = "Gain Muscle"
                planResult = secondPart
            }
        }
        else if (planID in 5..8){
            var firstPart = "Normal"
            if (planID == 5){
                val secondPart = "Lose Weight"
                planResult = secondPart
            }
            else if (planID == 6){
                val secondPart = "Gain Weight"
                planResult = secondPart
            }
            else if (planID == 7){
                val secondPart = "Maintain Weight"
                planResult = secondPart
            }
            else{
                val secondPart = "Gain Muscle"
                planResult = secondPart
            }
        }
        else if (planID in 9..12){
            var firstPart = "Plump"
            if (planID == 9){
                val secondPart = "Lose Weight"
                planResult = secondPart
            }
            else if (planID == 10){
                val secondPart = "Gain Weight"
                planResult = secondPart
            }
            else if (planID == 11){
                val secondPart = "Maintain Weight"
                planResult = secondPart
            }
            else{
                val secondPart = "Gain Muscle"
                planResult = secondPart
            }
        }
        else if (planID in 13..16){
            var firstPart = "Overweight"
            if (planID == 13){
                val secondPart = "Lose Weight"
                planResult = secondPart
            }
            else if (planID == 14){
                val secondPart = "Gain Weight"
                planResult = secondPart
            }
            else if (planID == 15){
                val secondPart = "Maintain Weight"
                planResult = secondPart
            }
            else{
                val secondPart = "Gain Muscle"
                planResult = secondPart
            }
        }
        return planResult
    }

    @SuppressLint("SuspiciousIndentation")
    private fun getSession(){
        SessionCheck = viewModel.getCurrentSession()!!
        observer = Observer { data ->
                val indexSession = data.toString().indexOf("loggedUser_id=")
                val endIndexSession = data.toString().indexOf(")", indexSession)
                if(indexSession != -1 && endIndexSession != -1){
                    session = data.toString().substring(indexSession + "loggedUser_id=".length, endIndexSession)
                    Handler().postDelayed({writeToDB(session!!.toInt())}, 100)
                    Handler().postDelayed({getUserID(session!!.toInt())}, 100)
            }
        }
        SessionCheck.observe(requireActivity(), observer)
    }

    private fun getUserID(user_id: Int){
        UserCheck = viewModel.getUserFromID(user_id)!!
        observerUser = Observer { data2 ->
                val indexId = data2.toString().indexOf("user_id=")
                val endIndexId = data2.toString().indexOf(",", indexId)
                val dataId = data2.toString().substring(indexId + "user_id=".length, endIndexId)

                    val username = data2.toString().substring(data2.toString().indexOf("name=") + "name=".length, data2.toString().indexOf(",", data2.toString().indexOf("name=")))
                    val goalsID = data2.toString().substring(data2.toString().indexOf("goals_id=") + "goals_id=".length, data2.toString().indexOf(",", data2.toString().indexOf("goals_id=")))
                    val gender = data2.toString().substring(data2.toString().indexOf("gender=") + "gender=".length, data2.toString().indexOf(",", data2.toString().indexOf("gender=")))
                    val age = data2.toString().substring(data2.toString().indexOf("age=") + "age=".length, data2.toString().indexOf(",", data2.toString().indexOf("age=")))
                    val height = data2.toString().substring(data2.toString().indexOf("height=") + "height=".length, data2.toString().indexOf(")", data2.toString().indexOf("height=")))
                    if(dailyWeight == null || dailyWeight == "0.0" || dailyWeight == ""){
                        dailyWeight = data2.toString().substring(data2.toString().indexOf("weight=") + "weight=".length, data2.toString().indexOf(",", data2.toString().indexOf("weight=")))
                    }
                     DBUsername = username
                     DBAge = age.toInt()
                     DBHeight = height.toInt()
                     DBGender = gender
                     DBPlan = goalsID.toInt()
            Handler().postDelayed({
                DBWeight = dailyWeight!!.toFloat()
                setText() }, 100)


        }
        UserCheck.observe(requireActivity(), observerUser)
    }

    private fun writeToDB(user_id: Int){
        ProgressCheck = viewModel.getProgress(user_id)!!
        Log.d("hydration", "1")
        observerProgress = Observer { dataIDe ->
            dailyWeight = dataIDe.toString().substring(dataIDe.toString().indexOf("dailyWeight=") + "dailyWeight=".length, dataIDe.toString().indexOf(",", dataIDe.toString().indexOf("dailyWeight=")))
            Log.d("hydration", "3")
        }
        Log.d("hydration", "2")
        ProgressCheck.observe(requireActivity(), observerProgress)
    }

}
