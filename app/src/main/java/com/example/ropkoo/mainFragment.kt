package com.example.ropkoo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.provider.SyncStateContract.Helpers.insert
import android.text.TextUtils
import android.util.Log
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
import android.widget.ListAdapter
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.*
import com.example.ropkoo.DB.*
import kotlinx.android.synthetic.main.fragment_create_account2.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.*


class mainFragment : Fragment() {
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var btn_signIn: Button = view.findViewById(R.id.btn_signIn)
        btn_signIn.setOnClickListener {
            sendDataNext()
        }
        var btn_register: Button = view.findViewById(R.id.btn_register)
        btn_register.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_mainFragment_to_createAccountFragment1)
        }
    }

    private fun sendDataNext() {
        val username = et_mainUName.text.toString()
        val password = et_mainPass.text.toString()

        if (inputCheck(username, password)) {
            checkPassword(username, password)
        } else {
            Toast.makeText(requireContext(), "Fill out all the fields!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(username: String, password: String): Boolean {
        return !(TextUtils.isEmpty(username) || TextUtils.isEmpty(password))
    }

    /*@SuppressLint("SuspiciousIndentation")
    private fun checkPassword(username: String, password: String) {
        var nameCheck = viewModel.getLogin(username, password)
        if (nameCheck.toString().isNotEmpty()) {
        Log.d("Login attempt>", nameCheck.toString())
        nameCheck.observe(requireActivity(), Observer { data ->

                val indexName = data.toString().indexOf("name=")
                val endIndexName = data.toString().indexOf(",", indexName)
                val dataName = data.toString().substring(indexName + "name=".length, endIndexName)

                if (dataName == username) {
                    (requireContext() as Activity).runOnUiThread {
                        findNavController().navigate(R.id.action_mainFragment_to_menuFragment)
                    }
                    val bundle1 = Bundle()
                    //bundle1.putString("DBUsername", nameCheck.toString())
                    bundle1.putString("DBUsername", data.toString().substring(data.toString().indexOf("name=") + "name=".length, data.toString().indexOf(",", data.toString().indexOf("name="))))
                    bundle1.putString("DBAge", data.toString().substring(data.toString().indexOf("age=") + "age=".length, data.toString().indexOf(",", data.toString().indexOf("age="))))
                    bundle1.putString("DBWeight", data.toString().substring(data.toString().indexOf("weight=") + "weight=".length, data.toString().indexOf(",", data.toString().indexOf("weight="))))
                    bundle1.putString("DBHeight", data.toString().substring(data.toString().indexOf("height=") + "height=".length, data.toString().indexOf(")", data.toString().indexOf("height="))))
                    setFragmentResult("MF", bundle1)
                }
            } else {
                (requireContext() as Activity).runOnUiThread {
                    Toast.makeText(
                        requireContext(),
                        "This account doesnt exist, try Registering!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }*/


    @SuppressLint("SuspiciousIndentation")
    private fun checkPassword(username: String, password: String) {
        var nameCheck = viewModel.getLogin(username, password)!!
            nameCheck.observe(requireActivity(), Observer { data ->

                val indexName = data.toString().indexOf("name=")
                val endIndexName = data.toString().indexOf(",", indexName)
                if(indexName != -1 && endIndexName != -1){
                val dataName = data.toString().substring(indexName + "name=".length, endIndexName)

                    if (dataName == username) {
                       (requireContext() as Activity).runOnUiThread {
                           Log.d("Login >", dataName)
                           findNavController().navigate(R.id.action_mainFragment_to_menuFragment)
                       }
                       val bundle1 = Bundle()
                       //bundle1.putString("DBUsername", nameCheck.toString())
                        bundle1.putString("DBUsername", data.toString().substring(data.toString().indexOf("name=") + "name=".length, data.toString().indexOf(",", data.toString().indexOf("name="))))
                       bundle1.putString("DBAge", data.toString().substring(data.toString().indexOf("age=") + "age=".length, data.toString().indexOf(",", data.toString().indexOf("age="))))
                      bundle1.putString("DBWeight", data.toString().substring(data.toString().indexOf("weight=") + "weight=".length, data.toString().indexOf(",", data.toString().indexOf("weight="))))
                      bundle1.putString("DBHeight", data.toString().substring(data.toString().indexOf("height=") + "height=".length, data.toString().indexOf(")", data.toString().indexOf("height="))))
                      setFragmentResult("MF", bundle1)

                    }
                }else {
                    (requireContext() as Activity).runOnUiThread {
                        Toast.makeText(
                            requireContext(),
                            "This account doesnt exist, try Registering!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        })
    }
   /* private fun setUser() {
        viewModel.readAllData.observe(viewLifecycleOwner, Observer { userList ->
                var getAllUserData = viewModel.readAllData.value
                val bundle1 = Bundle()
                bundle1.putString("DBUsername", getAllUserData.toString())
                //bundle1.putString("DBUsername", getAllUserData.toString().substring(getAllUserData.toString().indexOf("name=") + "name=".length, getAllUserData.toString().indexOf(",", getAllUserData.toString().indexOf("name="))))
                //bundle1.putString("DBAge", getAllUserData.toString().substring(getAllUserData.toString().indexOf("age=") + "age=".length, getAllUserData.toString().indexOf(",", getAllUserData.toString().indexOf("age="))))
                //bundle1.putString("DBWeight", getAllUserData.toString().substring(getAllUserData.toString().indexOf("weight=") + "weight=".length, getAllUserData.toString().indexOf(",", getAllUserData.toString().indexOf("weight="))))
                //bundle1.putString("DBHeight", getAllUserData.toString().substring(getAllUserData.toString().indexOf("height=") + "height=".length, getAllUserData.toString().indexOf(",", getAllUserData.toString().indexOf("height="))))
            viewModel.viewModelScope.launch(Dispatchers.IO) {
                withContext(Dispatchers.Main) {
                    setFragmentResult("MF", bundle1)
                }
            }
        })
    }*/

    /*if(inputCheck(username, age, weight, height)){

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
    }*/
}



