package com.example.ropkoo

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_create_account1.btn_continue
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
        val username = et_username.text.toString()
        val age = et_age.text
        val weight =  et_weight.text
        val height = et_height.text

        if(inputCheck(username, age, weight, height)){
            findNavController().navigate(R.id.action_createAccountFragment2_to_bodyInputFragment1)
        }
        else{
            Toast.makeText(requireContext(), "Fill out all the fields!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(username: String, age: Editable, weight: Editable, height: Editable): Boolean{
        return !(TextUtils.isEmpty(username) || age.isEmpty() || TextUtils.isEmpty(weight.toString()) || height.isEmpty())
    }
}