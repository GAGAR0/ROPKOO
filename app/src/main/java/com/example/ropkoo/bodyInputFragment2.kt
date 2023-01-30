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

class bodyInputFragment2 : Fragment() {

    var email: String? = null
    var username: String? = null
    var password: String? = null
    var age: Int? = null
    var weight: Float? = null
    var height: Int? = null
    var bodyInput1: Int? = null
    var bodyInput2: Int? = null
    lateinit var nameCheck: LiveData<List<User>>
    lateinit var observer: Observer<List<User>>
    private lateinit var viewModel: UserViewModel

    override fun onStop() {
        super.onStop()
        nameCheck.removeObserver(observer)
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
             height = bundle.getInt("height")}

        setFragmentResultListener("BIF1") { _, bundle ->
             bodyInput1 = bundle.getInt("bodyInput1") }

       /* setFragmentResultListener("connectDatabases") { _, bundle ->
             connectDatabaseID = bundle.getInt("user_id")
        }*/

        var btn_goal1 : Button = view.findViewById(R.id.btn_goal1)
        btn_goal1.setOnClickListener{
             bodyInput2 = 1;
            insertData()
            findNavController().navigate(R.id.action_bodyInputFragment2_to_mainFragment)
            requireActivity().supportFragmentManager.popBackStack()
        }
        var btn_goal2 : Button = view.findViewById(R.id.btn_goal2)
        btn_goal2.setOnClickListener{
             bodyInput2 = 2;
            insertData()
            Navigation.findNavController(view).navigate(R.id.action_bodyInputFragment2_to_mainFragment)
        }
        var btn_goal3 : Button = view.findViewById(R.id.btn_goal3)
        btn_goal3.setOnClickListener{
             bodyInput2 = 3;
            insertData()
            Navigation.findNavController(view).navigate(R.id.action_bodyInputFragment2_to_mainFragment)
        }
        var btn_goal4 : Button = view.findViewById(R.id.btn_goal4)
        btn_goal4.setOnClickListener{
             bodyInput2 = 4;
            insertData()
            Navigation.findNavController(view).navigate(R.id.action_bodyInputFragment2_to_mainFragment)
        }
    }

    private fun insertData(){
        var goalId = getGoalId(bodyInput1!!, bodyInput2!!)
        var user = User(null, goalId, username.toString(), email.toString(), password.toString(), age!!, weight!!, height!!)
        viewModel.addUser(user)
        connectDatabases(username!!, password!!)
        /*connectDatabases(username.toString(), password.toString())
        setFragmentResultListener("connectDatabases") { _, bundle ->
            var connectDatabaseID = bundle.getString("user_id")!!.toInt()
        }*/
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
       observer = Observer { data ->


                 val indexName = data.toString().indexOf("name=")
                 val endIndexName = data.toString().indexOf(",", indexName)
                    if(indexName != -1 && endIndexName != -1){
                        val dataName = data.toString().substring(indexName + "name=".length, endIndexName)
                        Log.d("data", data.toString())
                        Log.d("InputData", username)

                if (dataName == username) {
                   //val bundle1 = Bundle()
                    val indexId = data.toString().indexOf("user_id=")
                    val endIndexId = data.toString().indexOf(",", indexId)
                    val dataId = data.toString().substring(indexId + "user_id=".length, endIndexId)
                    (requireContext() as Activity).runOnUiThread {
                        Log.d("WriteDB", dataName)
                        val progress = Progress(null, dataId.toInt(), 0, 0, 0, 0, 0.0F, 0.0F)
                        viewModel.addProgress(progress)
                    }
                    //bundle1.putString("user_id", dataId)
                    //setFragmentResult("connectDatabases", bundle1)
                }
        }
       }
       nameCheck.observe(requireActivity(), observer)
}
}