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
import androidx.navigation.Navigation
import com.example.ropkoo.DB.User
import com.example.ropkoo.DB.UserViewModel
import kotlinx.android.synthetic.main.fragment_create_account1.*
import kotlinx.android.synthetic.main.fragment_create_account2.*
import kotlinx.android.synthetic.main.fragment_body_input1.*
import kotlinx.android.synthetic.main.fragment_create_account2.height
import kotlinx.android.synthetic.main.fragment_create_account2.weight
import kotlinx.android.synthetic.main.fragment_profile.*


class bodyInputFragment2 : Fragment() {

    var email: String? = null
    var username: String? = null
    var password: String? = null
    var age: Int? = null
    var weight: Float? = null
    var height: Int? = null
    var bodyInput1: String? = null

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
             bodyInput1 = bundle.getString("bodyInput1") }

        var bodyInput2 = 0;

        var btn_goal1 : Button = view.findViewById(R.id.btn_goal1)
        btn_goal1.setOnClickListener{
            var bodyInput2 = 1;
            insertData()
            Navigation.findNavController(view).navigate(R.id.action_bodyInputFragment2_to_menuFragment)
        }
        var btn_goal2 : Button = view.findViewById(R.id.btn_goal2)
        btn_goal2.setOnClickListener{
            var bodyInput2 = 2;
            insertData()
            Navigation.findNavController(view).navigate(R.id.action_bodyInputFragment2_to_menuFragment)
        }
        var btn_goal3 : Button = view.findViewById(R.id.btn_goal3)
        btn_goal3.setOnClickListener{
            var bodyInput2 = 3;
            insertData()
            Navigation.findNavController(view).navigate(R.id.action_bodyInputFragment2_to_menuFragment)
        }
        var btn_goal4 : Button = view.findViewById(R.id.btn_goal4)
        btn_goal4.setOnClickListener{
            var bodyInput2 = 4;
            insertData()
            Navigation.findNavController(view).navigate(R.id.action_bodyInputFragment2_to_menuFragment)
        }
    }

    private fun insertData(){
        val user = User(0, this.username.toString(), this.email.toString(), this.password.toString(), this.age!!, this.weight!!, this.height!!)
        viewModel.addUser(user)
    }
}