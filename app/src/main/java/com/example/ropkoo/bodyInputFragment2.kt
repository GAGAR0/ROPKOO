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
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.*
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.ropkoo.DB.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.LiveData
import android.os.Handler
import android.widget.ImageButton
import java.util.*

class bodyInputFragment2 : Fragment() {

    var email: String? = null
    var username: String? = null
    var password: String? = null
    var age: Int? = null
    var weight: Float? = null
    var height: Int? = null
    var gender: String? = null
    var bodyInput1: Int? = null
    var bodyInput2: Int? = null
    var dataId: Int? = null
    var dateFinal: Long? = null
    lateinit var nameCheck: LiveData<List<User>>
    lateinit var observerNe: Observer<List<User>>
    private lateinit var viewModel: UserViewModel

    override fun onStop() {
        super.onStop()
        nameCheck.removeObserver(observerNe)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        return inflater.inflate(R.layout.fragment_body_input2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener("CAF1") { _, bundle ->
            email = bundle.getString("email")
            password = bundle.getString("password")}

        setFragmentResultListener("CAF2") { _, bundle ->
             username = bundle.getString("username")
             age = bundle.getInt("age")
             weight = bundle.getFloat("weight")
             height = bundle.getInt("height")
             gender = bundle.getString("gender")}

       /* setFragmentResultListener("connectDatabases") { _, bundle ->
             connectDatabaseID = bundle.getInt("user_id")
        }*/

        var btn_goal1 : ImageButton = view.findViewById(R.id.btn_goal1)
        btn_goal1.setOnClickListener{
             bodyInput2 = 1;
            insertData()
            Handler().postDelayed({ Navigation.findNavController(view).navigate(R.id.action_bodyInputFragment2_to_mainFragment) }, 1000)
        }
        var btn_goal2 : ImageButton = view.findViewById(R.id.btn_goal2)
        btn_goal2.setOnClickListener{
             bodyInput2 = 2;
            insertData()
            Handler().postDelayed({ Navigation.findNavController(view).navigate(R.id.action_bodyInputFragment2_to_mainFragment) }, 1000)
        }
        var btn_goal3 : ImageButton = view.findViewById(R.id.btn_goal3)
        btn_goal3.setOnClickListener{
             bodyInput2 = 3;
            insertData()
            Handler().postDelayed({ Navigation.findNavController(view).navigate(R.id.action_bodyInputFragment2_to_mainFragment) }, 1000)
        }
        var btn_goal4 : ImageButton = view.findViewById(R.id.btn_goal4)
        btn_goal4.setOnClickListener{
             bodyInput2 = 4;
            insertData()
            Handler().postDelayed({ Navigation.findNavController(view).navigate(R.id.action_bodyInputFragment2_to_mainFragment) }, 1000)
        }
    }

    private fun insertData(){

        var goalId = getGoalId(bmi(weight!!.toFloat(), height!!.toFloat())!!, bodyInput2!!)
        var user = User(null, goalId, username.toString(), email.toString(), password.toString(), gender.toString(), age!!, weight!!, height!!)
        viewModel.addUser(user)
        var session = Session(1, null)
        viewModel.addSession(session)

        (requireContext() as Activity).runOnUiThread {
            connectDatabases(username!!, password!!)
        }

        /*connectDatabases(username.toString(), password.toString())
        setFragmentResultListener("connectDatabases") { _, bundle ->
            var connectDatabaseID = bundle.getString("user_id")!!.toInt()
        }*/
    }

    private fun bmi(weight: Float, height: Float): Int{
        val bmicalc = weight / ((height/100) * (height/100))
        if (bmicalc < 19){
            return 1
        }
        else if (bmicalc >= 19 && bmicalc < 25){
            return 2
        }
        else if (bmicalc >= 25 && bmicalc < 30){
            return 3
        }
        else return 4

    }
    @SuppressLint("SuspiciousIndentation")
    private fun getDay(){
         val today = Calendar.getInstance()
             today.set(Calendar.HOUR_OF_DAY, 0)
             today.set(Calendar.MINUTE, 0)
             today.set(Calendar.SECOND, 0)
             today.set(Calendar.MILLISECOND, 0)
        val date = today.time
        dateFinal = date.time

    }

    private fun getGoalId(Body1: Int, Body2: Int): Int{
        if(Body1 == 1 && Body2 == 1){
            val goalId = 1;
            return goalId
        }
        else if(Body1 == 1 && Body2 == 2){
            val goalId = 2;
            return goalId
        }
        else if(Body1 == 1 && Body2 == 3){
            val goalId = 3;
            return goalId
        }
        else if(Body1 == 1 && Body2 == 4){
            val goalId = 4;
            return goalId
        }
        else if(Body1 == 2 && Body2 == 1){
            val goalId = 5;
            return goalId
        }
        else if(Body1 == 2 && Body2 == 2){
            val goalId = 6;
            return goalId
        }
        else if(Body1 == 2 && Body2 == 3){
            val goalId = 7;
            return goalId
        }
        else if(Body1 == 2 && Body2 == 4){
            val goalId = 8;
            return goalId
        }
        else if(Body1 == 3 && Body2 == 1){
            val goalId = 9;
            return goalId
        }
        else if(Body1 == 3 && Body2 == 2){
            val goalId = 10;
            return goalId
        }
        else if(Body1 == 3 && Body2 == 3){
            val goalId = 11;
            return goalId
        }
        else if(Body1 == 3 && Body2 == 4){
            val goalId = 12;
            return goalId
        }
        else if(Body1 == 4 && Body2 == 1){
            val goalId = 13;
            return goalId
        }
        else if(Body1 == 4 && Body2 == 2){
            val goalId = 14;
            return goalId
        }
        else if(Body1 == 4 && Body2 == 3){
            val goalId = 15;
            return goalId
        }
        else if(Body1 == 4 && Body2 == 4){
            val goalId = 16;
            return goalId
        }
        else return 0
    }

   @SuppressLint("SuspiciousIndentation")
   private fun connectDatabases(username: String, password: String){
       nameCheck = viewModel.getLogin(username, password)!!
       getDay()
       Log.d("WriteDB1", "firstthingsfirst")
       observerNe = Observer { data ->
                 val indexName = data.toString().indexOf("name=")
           Log.d("WriteDB1", data.toString())
                 val endIndexName = data.toString().indexOf(",", indexName)
                    if(indexName != -1 && endIndexName != -1){
                        val dataName = data.toString().substring(indexName + "name=".length, endIndexName)
                        Log.d("WriteDB2", username)

                if (dataName == username) {
                   //val bundle1 = Bundle()
                    val indexId = data.toString().indexOf("user_id=")
                    val endIndexId = data.toString().indexOf(",", indexId)
                     dataId = data.toString().substring(indexId + "user_id=".length, endIndexId).toInt()
                    (requireContext() as Activity).runOnUiThread {
                        Log.d("WriteDB3", dataName)
                        val progress = Progress(null, dataId!!, 0, 0, 0, 0, 0.0F, 0.0F, dateFinal)
                        viewModel.addProgress(progress)
                    }
                    //bundle1.putString("user_id", dataId)
                    //setFragmentResult("connectDatabases", bundle1)
                }
        }
       }
       Log.d("WriteDB1", "lastthingslast")
       nameCheck.observe(requireActivity(), observerNe)
}
}