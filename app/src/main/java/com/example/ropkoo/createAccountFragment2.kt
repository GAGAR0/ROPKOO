package com.example.ropkoo

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.ropkoo.DB.User
import com.example.ropkoo.DB.UserViewModel
import kotlinx.android.synthetic.main.fragment_create_account1.btn_continue
import java.lang.Float.parseFloat
import kotlinx.android.synthetic.main.fragment_create_account2.*



class createAccountFragment2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_account2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_continue.setOnClickListener{
            sendDataNext()
        }
    }

    private fun sendDataNext(){
        val username = et_username.text.toString().trim()
        val age = et_age.text.toString().trim().toInt()
        val weight = et_weight.text.toString().toFloat()
        val height = et_height.text.toString().trim().toInt()


        if(inputCheck(username, age, weight, height)){

            val bundle1 = Bundle()
            bundle1.putString("username", username)
            bundle1.putInt("age", age)
            bundle1.putFloat("weight", weight)
            bundle1.putInt("height", height)
            setFragmentResult("CAF2", bundle1)

            findNavController().navigate(R.id.action_createAccountFragment2_to_bodyInputFragment1)
        }
        else{
            Toast.makeText(requireContext(), "Fill out all the fields!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(username: String, age: Int, weight: Float, height: Int): Boolean{
        return !(TextUtils.isEmpty(username) || age == 0 || weight == 0.0f || height == 0)
    }
}