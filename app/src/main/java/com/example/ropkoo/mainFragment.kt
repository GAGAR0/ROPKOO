package com.example.ropkoo

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_create_account1.*
import kotlinx.android.synthetic.main.fragment_main.*


class mainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var btn_signIn : Button = view.findViewById(R.id.btn_signIn)
        btn_signIn.setOnClickListener{
            sendDataNext()
        }
        var btn_register : Button = view.findViewById(R.id.btn_register)
        btn_register.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_createAccountFragment1)
        }
    }

    private fun sendDataNext(){
        val username = et_mainUName.text.toString()
        val password = et_mainPass.text.toString()

        if(inputCheck(username, password)){
            findNavController().navigate(R.id.action_mainFragment_to_menuFragment)
        }
        else{
            Toast.makeText(requireContext(), "Fill out all the fields!", Toast.LENGTH_SHORT).show()}
    }

    private fun inputCheck(username: String, password: String): Boolean{
        return !(TextUtils.isEmpty(username) || TextUtils.isEmpty(password))
    }
}


