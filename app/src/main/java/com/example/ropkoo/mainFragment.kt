package com.example.ropkoo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
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
import androidx.lifecycle.Observer
import com.example.ropkoo.DB.*
import kotlinx.android.synthetic.main.fragment_calorie_calculator.*
import kotlinx.android.synthetic.main.fragment_create_account2.*
import kotlinx.android.synthetic.main.fragment_hydration.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.*
import java.util.*


class mainFragment : Fragment() {
    private lateinit var viewModel: UserViewModel
    private lateinit var parcelable: Parcelable
    lateinit var SessionCheck: LiveData<List<Session>>
    lateinit var observerSession: Observer<List<Session>>
    var isSignedIn : String? = null
    override fun onStop() {
        super.onStop()
        SessionCheck.removeObserver(observerSession)
    }

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
        staySignedIn()
        Handler().post(object : Runnable {
            override fun run() {
                if(isSignedIn == "1"){
                    findNavController().navigate(R.id.action_mainFragment_to_menuFragment)
                } else {
                    Handler().post(this) // Reschedule the code to run again if the condition is not met
                }
            }
        })


        var btn_signIn: Button = view.findViewById(R.id.btn_signIn)
        btn_signIn.setOnClickListener {
            sendDataNext()
        }
        var btn_register: Button = view.findViewById(R.id.btn_register)
        btn_register.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_createAccountFragment1)
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
                                findNavController().navigate(R.id.action_mainFragment_to_menuFragment)
                            }
                       val bundle1 = Bundle()
                       //bundle1.putString("DBUsername", nameCheck.toString())
                        bundle1.putString("DBUserID", data.toString().substring(data.toString().indexOf("user_id=") + "user_id=".length, data.toString().indexOf(",", data.toString().indexOf("user_id="))))
                        bundle1.putString("DBUsername", data.toString().substring(data.toString().indexOf("name=") + "name=".length, data.toString().indexOf(",", data.toString().indexOf("name="))))
                        bundle1.putString("DBPlan", data.toString().substring(data.toString().indexOf("goals_id=") + "goals_id=".length, data.toString().indexOf(",", data.toString().indexOf("goals_id="))))
                        bundle1.putString("DBGender", data.toString().substring(data.toString().indexOf("gender=") + "gender=".length, data.toString().indexOf(",", data.toString().indexOf("gender="))))
                        bundle1.putString("DBAge", data.toString().substring(data.toString().indexOf("age=") + "age=".length, data.toString().indexOf(",", data.toString().indexOf("age="))))
                        bundle1.putString("DBWeight", data.toString().substring(data.toString().indexOf("weight=") + "weight=".length, data.toString().indexOf(",", data.toString().indexOf("weight="))))
                        bundle1.putString("DBHeight", data.toString().substring(data.toString().indexOf("height=") + "height=".length, data.toString().indexOf(")", data.toString().indexOf("height="))))
                        setFragmentResult("MF", bundle1)
                        viewModel.userIdModel.value = data.toString().substring(data.toString().indexOf("user_id=") + "user_id=".length, data.toString().indexOf(",", data.toString().indexOf("user_id=")))

                        var session = Session(1, if(cb_staySignedIn.isChecked){1}else{null}, data.toString().substring(data.toString().indexOf("user_id=") + "user_id=".length, data.toString().indexOf(",", data.toString().indexOf("user_id="))).toInt())
                        viewModel.updateSession(session)


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


    private fun staySignedIn(){
        SessionCheck = viewModel.getCurrentSession()!!
        observerSession = Observer { data ->
            val index = data.toString().indexOf("staySignedIn=")
            val endIndex = data.toString().indexOf(",", index)
            if(index != -1 && endIndex != -1) {
                isSignedIn = data.toString().substring(index + "staySignedIn=".length, endIndex)
            }
        }
        SessionCheck.observe(requireActivity(), observerSession)
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



