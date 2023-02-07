package com.example.ropkoo

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
import com.example.ropkoo.DB.User
import com.example.ropkoo.DB.UserViewModel
import kotlinx.android.synthetic.main.fragment_goal_progress2.*
import kotlinx.android.synthetic.main.fragment_main.*


class goalProgressFragment2 : Fragment() {
    private lateinit var viewModel: UserViewModel
    var DBUserID: String? = null
    lateinit var IDCheck: LiveData<List<Progress>>
    lateinit var observer: Observer<List<Progress>>

    override fun onStop() {
        super.onStop()
        IDCheck.removeObserver(observer)
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

        setFragmentResultListener("MF") { _, bundle ->
            DBUserID = bundle.getString("DBUserID") }

        var ib_back: ImageButton = view.findViewById(R.id.ib_back)
        ib_back.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_goalProgressFragment2_to_goalProgressFragment)
        }

        var bt_confirm: Button = view.findViewById(R.id.bt_confirm)
        bt_confirm.setOnClickListener {
            addWeight()
        }
    }

    private fun addWeight() {
        var dailyWeight = et_current.text.toString()
        if (inputCheck(dailyWeight)) {
            Log.d("getProgress>", "inputcheck")
            writeToDB(dailyWeight.toFloat())
        } else {
            Toast.makeText(requireContext(), "Fill out all the fields!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(dailyWeight: String): Boolean {
        return !(TextUtils.isEmpty(dailyWeight))
    }

    private fun writeToDB(dailyWeight: Float?) {
        Log.d("getProgress>", "1")
        IDCheck = viewModel.getProgress(DBUserID!!.toInt())!!
        Log.d("getProgress>", "2")
        observer = Observer { dataIDe ->
            Log.d("getProgress>", "3")
            Log.d("getProgress>", dataIDe.toString())
            val indexID = dataIDe.toString().indexOf("userId=")
            val endIndexID = dataIDe.toString().indexOf(",", indexID)
            if(indexID != -1 && endIndexID != -1){
                val dataID = dataIDe.toString().substring(indexID + "userId=".length, endIndexID)
                Log.d("getProgress>", dailyWeight!!.toString())
                if (dataID.toInt() == DBUserID!!.toInt()) {
                    var progress_id = dataIDe.toString().substring(dataIDe.toString().indexOf("progress_id=") + "progress_id=".length, dataIDe.toString().indexOf(",", dataIDe.toString().indexOf("progress_id=")))
                    val dailyCalories: String = dataIDe.toString().substring(dataIDe.toString().indexOf("dailyCalories=") + "dailyCalories=".length, dataIDe.toString().indexOf(",", dataIDe.toString().indexOf("dailyCalories=")))
                    val stepCounter: String = dataIDe.toString().substring(dataIDe.toString().indexOf("stepCount=") + "stepCount=".length, dataIDe.toString().indexOf(",", dataIDe.toString().indexOf("stepCount=")))
                    val waterIntake: String = dataIDe.toString().substring(dataIDe.toString().indexOf("waterIntake=") + "waterIntake=".length, dataIDe.toString().indexOf(",", dataIDe.toString().indexOf("waterIntake=")))
                    val goalProgressPercentage: String = dataIDe.toString().substring(dataIDe.toString().indexOf("goalProgressPercentage=") + "goalProgressPercentage=".length, dataIDe.toString().indexOf(",", dataIDe.toString().indexOf("goalProgressPercentage=")))
                    val weeklyWeight: String = dataIDe.toString().substring(dataIDe.toString().indexOf("weeklyWeight=") + "weeklyWeight=".length, dataIDe.toString().indexOf(")", dataIDe.toString().indexOf("weeklyWeight=")))

                    Log.d("getProgress>", dailyCalories)
                    Log.d("getProgress>", stepCounter)
                    Log.d("getProgress>", waterIntake)
                    Log.d("getProgress>", dailyWeight.toString())
                    var avgWeight = weeklyWeight.toFloat() + dailyWeight!!
                    val bundle1 = Bundle()
                    bundle1.putString("dailyWeight", dailyWeight!!.toString())
                    setFragmentResult("goalProgressWeight", bundle1)
                    //val progress_id = DBUserID!!
                    (requireContext() as Activity).runOnUiThread {



                        Log.d("getProgress>", "HOLY TRANSACTION "+dailyWeight!!.toString())
                    val updated = Progress(progress_id.toInt(), dataID.toInt(), dailyCalories.toInt(),stepCounter.toInt(), waterIntake.toInt(), goalProgressPercentage.toInt(), dailyWeight!!, avgWeight)
                    viewModel.updateWeight(updated)

                        Log.d("getProgress>", updated.toString())
                        }
                }
        } }
        IDCheck.observe(requireActivity(), observer)

    }
}


