package com.example.ropkoo

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation


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
            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_menuFragment)
        }
        var btn_register : Button = view.findViewById(R.id.btn_register)
        btn_register.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_createAccountFragment1)
        }
    }
}

    /*var btn_register = findViewById<Button>(R.id.btn_register)
    btn_register.setOnClickListener{
        val intent = Intent(this, createAccount::class.java)
        startActivity(intent)
    }*/
