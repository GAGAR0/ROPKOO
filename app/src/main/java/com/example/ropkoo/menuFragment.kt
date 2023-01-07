package com.example.ropkoo

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.navigation.Navigation


class menuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var iv_mainLogo : ImageButton = view.findViewById(R.id.iv_mainLogo)
        iv_mainLogo.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_menuFragment_to_profileFragment)
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