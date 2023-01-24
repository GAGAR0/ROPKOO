package com.example.ropkoo

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.example.ropkoo.DB.Goals
import com.example.ropkoo.DB.Progress
import com.example.ropkoo.DB.User
import com.example.ropkoo.DB.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class bodyInputFragment2 : Fragment() {

    var email: String? = null
    var username: String? = null
    var password: String? = null
    var age: Int? = null
    var weight: Float? = null
    var height: Int? = null
    var bodyInput1: Int? = null
    var bodyInput2: Int? = null

    private lateinit var viewModel: UserViewModel

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



        var btn_goal1 : Button = view.findViewById(R.id.btn_goal1)
        btn_goal1.setOnClickListener{
             bodyInput2 = 1;
            insertData()
            Navigation.findNavController(view).navigate(R.id.action_bodyInputFragment2_to_mainFragment)
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
        val user = User(null, this.username.toString(), this.email.toString(), this.password.toString(), this.age!!, this.weight!!, this.height!!)
        viewModel.addUser(user)
        val goal = Goals(null, null ,this.bodyInput1!!, this.bodyInput2!!)
        viewModel.addGoals(goal)
        var progress = Progress(null,null,0,0,0,0,0.0F,0.0F)
        viewModel.addProgress(progress)
        //viewModel.getUserWithGoals()
    }
}