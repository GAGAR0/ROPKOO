package com.example.ropkoo

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.ropkoo.DB.User
import kotlinx.android.synthetic.main.fragment_create_account1.*


class createAccountFragment1 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_account1, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_continue.setOnClickListener{
            sendDataNext()
        }
    }

    private fun sendDataNext(){
        val db_email = et_email.text.toString()
        val db_password = et_passw.text.toString()
        val db_passwordCheck = et_passw2.text.toString()

        if(inputCheck(db_email, db_password, db_passwordCheck)){

            val bundle = Bundle()
            bundle.putString("email", db_email)
            bundle.putString("password", db_password)
            setFragmentResult("CAF1", bundle)

            findNavController().navigate(R.id.action_createAccountFragment1_to_createAccountFragment2)
        }
        else{
            Toast.makeText(requireContext(), "Fill out all the fields!", Toast.LENGTH_SHORT).show()}
    }

    private fun inputCheck(db_email: String, db_password: String, db_passwordCheck: String): Boolean{
        return !(TextUtils.isEmpty(db_email) || TextUtils.isEmpty(db_password) || TextUtils.isEmpty(db_passwordCheck))
    }
}