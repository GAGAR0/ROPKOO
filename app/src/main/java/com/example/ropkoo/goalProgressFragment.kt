package com.example.ropkoo

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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.ropkoo.DB.UserViewModel
import kotlinx.android.synthetic.main.fragment_goal_progress.*


class goalProgressFragment : Fragment() {

    var dailyWeight: String? = null
    var DBWeight: String? = null
    var DBHeight: String? = null
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_goal_progress, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = view.findViewById(R.id.pb_progress)
        progressBar.progress = 55

        setFragmentResultListener("goalProgressWeight") { _, bundle ->
            dailyWeight = bundle.getString("dailyWeight")

        setFragmentResultListener("MF") { _, bundle ->
            DBWeight = bundle.getString("DBWeight")
            DBHeight = bundle.getString("DBHeight")

            Log.d("progressBar>1", dailyWeight.toString())
            Log.d("progressBar>2", DBWeight.toString())
            Log.d("progressBar>3", DBHeight.toString())
            var bmiStart = calculate(DBWeight!!.toFloat(), DBHeight!!.toFloat())
            Log.d("progressBar>4", bmiStart.toString())
            var bmiCurrent = calculate(dailyWeight!!.toFloat(), DBHeight!!.toFloat())
            Log.d("progressBar>5", bmiCurrent.toString())
            val bmiGoal = bmiStart - 5
            var percentageProgress = ((bmiStart - bmiCurrent) / bmiGoal) * 100
            Log.d("progressBar>6", percentageProgress.toString())

        }}
        var ib_back : ImageButton = view.findViewById(R.id.ib_back)
        ib_back.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_goalProgressFragment_to_menuFragment)
        }

        ib_gpRight.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_goalProgressFragment_to_goalProgressFragment2)
        }
    }

    private fun calculate(weight: Float, height: Float): Float{
        val bmicalc = weight / ((height/100) * (height/100))
        return bmicalc
    }
}