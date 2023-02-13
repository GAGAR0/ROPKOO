package com.example.ropkoo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.ropkoo.DB.Progress
import com.example.ropkoo.DB.Session
import com.example.ropkoo.DB.User
import com.example.ropkoo.DB.UserViewModel
import kotlinx.android.synthetic.main.fragment_goal_progress.*


class goalProgressFragment : Fragment() {

    var DBUserID: Int? = null
    var goalProgressPercentage: Int? = null
    private lateinit var progressBar: ProgressBar

    lateinit var SessionCheck: LiveData<List<Session>>
    lateinit var observer: Observer<List<Session>>

    lateinit var IDCheck: LiveData<List<Progress>>
    lateinit var observerProgress: Observer<List<Progress>>

    private lateinit var viewModel: UserViewModel

    override fun onStop() {
        super.onStop()
        SessionCheck.removeObserver(observer)
        IDCheck.removeObserver(observerProgress)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        return inflater.inflate(R.layout.fragment_goal_progress, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = view.findViewById(R.id.pb_progress)


       /*/* setFragmentResultListener("goalProgressWeight") { _, bundle ->
            dailyWeight = bundle.getString("dailyWeight")

        setFragmentResultListener("MF") { _, bundle ->
            DBWeight = bundle.getString("DBWeight")
            DBHeight = bundle.getString("DBHeight")*/

            //Log.d("progressBar>1", dailyWeight.toString())
            //Log.d("progressBar>2", DBWeight.toString())
            //Log.d("progressBar>3", DBHeight.toString())
            var bmiStart = calculate(DBWeight!!.toFloat(), DBHeight!!.toFloat())
           // Log.d("progressBar>4", bmiStart.toString())
            var bmiCurrent = calculate(dailyWeight!!.toFloat(), DBHeight!!.toFloat())
           // Log.d("progressBar>5", bmiCurrent.toString())
            val bmiGoal = bmiStart - 5
            var percentageProgress = ((bmiStart - bmiCurrent) / bmiGoal) * 100
            //Log.d("progressBar>6", percentageProgress.toString())*/

        getSession()

        var ib_back : ImageButton = view.findViewById(R.id.ib_back)
        ib_back.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_goalProgressFragment_to_menuFragment)
        }

        ib_gpRight.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_goalProgressFragment_to_goalProgressFragment2)
        }
    }

   /*private fun calculate(weight: Float, height: Float): Float{
        val bmicalc = weight / ((height/100) * (height/100))
        return bmicalc
    }*/

    @SuppressLint("SuspiciousIndentation")
    private fun getSession(){
        SessionCheck = viewModel.getCurrentSession()!!
        observer = Observer { data ->
            val indexSession = data.toString().indexOf("loggedUser_id=")
            val endIndexSession = data.toString().indexOf(")", indexSession)
            if(indexSession != -1 && endIndexSession != -1){

                val session = data.toString().substring(indexSession + "loggedUser_id=".length, endIndexSession)
                DBUserID = session.toInt()
                writeToDB(DBUserID!!)

        }}
        SessionCheck.observe(requireActivity(), observer)
    }

    private fun writeToDB(user_id: Int){
        IDCheck = viewModel.getProgress(user_id)!!
        observerProgress = Observer { dataIDe ->


           // val indexGoal = dataIDe.toString().indexOf("loggedUser_id=")
            //val endIndexGoal = dataIDe.toString().indexOf(")", indexGoal)

                //if(indexGoal != -1 && endIndexGoal != -1) {
                //goalProgressPercentage = dataIDe.toString().substring(indexGoal + "loggedUser_id=".length, endIndexGoal).toInt()
            goalProgressPercentage = dataIDe.toString().substring(dataIDe.toString().indexOf("goalProgressPercentage=") + "goalProgressPercentage=".length, dataIDe.toString().indexOf(",", dataIDe.toString().indexOf("goalProgressPercentage="))).toInt()
                    progressBar.progress = goalProgressPercentage!!
                    tv_percent.setText(goalProgressPercentage!!.toString())
           /* }else {
                    progressBar.progress = 0
                    tv_percent.setText("0")

            }*/
                //(requireContext() as Activity).runOnUiThread {


            //}
        }
        IDCheck.observe(requireActivity(), observerProgress)
    }
}