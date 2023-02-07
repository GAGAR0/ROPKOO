package com.example.ropkoo

import android.content.Intent
import android.os.Bundle
import android.text.Layout.Directions
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.ropkoo.DB.User
import com.example.ropkoo.DB.UserViewModel


class menuFragment : Fragment() {
    var userId: Int? = null
    var plan: Int? = null
    var username: String? = null
    var age: Int? = null
    var weight: Float? = null
    var height: Int? = null
    var gender: String? = null
    var email: String? = null
   /* lateinit var getUserId: String
    lateinit var observer: Observer<String>*/

   /* override fun onStop() {
        super.onStop()
        viewModel.userIdModel.removeObserver(observer)
    }*/
    private val args by navArgs<menuFragmentArgs>()

    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("menu1> ", "1")
        setFragmentResultListener("MF") { _, bundle ->
            Log.d("menu1> ", "2")
            //if(bundle != null) {
            Log.d("menu1> ", "3")
            userId = bundle.getString("DBUserID")!!.toInt()
            username = bundle.getString("DBUsername")!!
            plan = bundle.getString("DBPlan")!!.toInt()
            age = bundle.getString("DBAge")!!.toInt()
            weight = bundle.getString("DBWeight")!!.toFloat()
            height = bundle.getString("DBHeight")!!.toInt()
            gender = bundle.getString("DBGender")!!
            //}
        }
        //getUserId = viewModel.userIdModel.value.toString()
        //viewModel.userIdModel.value = userId.toString()
        //Log.d("viewModelUserID1> ", getUserId)
        //getUserId = viewModel.userIdModel.value!!
        //Log.d("viewModelUserID> ", getUserId)
        /*getUserId = viewModel.userIdModel.value.toString()
        Log.d("viewModelIDFIRST>", getUserId)*/
        /*viewModel.userIdModel.value = userId.toString()
        observer = Observer { data ->
            getUserId = data
            Log.d("viewModelDATA>", data)
            getUserId = viewModel.userIdModel.value.toString()
            Log.d("viewModelUserID3> ", getUserId)
        }
        viewModel.userIdModel.observe(requireActivity(), observer)*/
        /*getUserId.observe(requireActivity(), observer)*/
        /*var userIdView: String? = viewModel.userIdModel.value
        viewModel.userIdModel.observe(viewLifecycleOwner, Observer { data ->
            Log.d("viewModelUserID> ", data)
            userIdView = data
        })*/
        /*getUserId.observe(requireActivity(), observer)*/
        /*userId = args.user.user_id!!.toInt()
        username = args.user.name!!
        plan = args.user.goals_id!!.toInt()
        age = args.user.age!!.toInt()
        weight = args.user.weight!!.toFloat()
        height = args.user.height!!.toInt()
        gender = args.user.gender!!*/
        Log.d("menu1> ", "5")
        var iv_mainLogo : ImageButton = view.findViewById(R.id.iv_mainLogo)
        iv_mainLogo.setOnClickListener{
            var user = User(userId, plan, username, null, null, gender, age, weight, height)
            val action = menuFragmentDirections.actionMenuFragmentToProfileFragment(user)
            Navigation.findNavController(view).navigate(action)
        }
        var btn_main1 : Button = view.findViewById(R.id.btn_main1)
        btn_main1.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_menuFragment_to_calorieCalculatorFragment)
        }
        var btn_main2 : Button = view.findViewById(R.id.btn_main2)
        btn_main2.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_menuFragment_to_goalProgressFragment)
        }
        var btn_main3 : Button = view.findViewById(R.id.btn_main3)
        btn_main3.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_menuFragment_to_stepCounterFragment)
        }
        var btn_main4 : Button = view.findViewById(R.id.btn_main4)
        btn_main4.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_menuFragment_to_hydrationFragment)
        }

    }

}