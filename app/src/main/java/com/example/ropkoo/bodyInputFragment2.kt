package com.example.ropkoo

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation


class bodyInputFragment2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_body_input2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var btn_goal1 : Button = view.findViewById(R.id.btn_goal1)
        btn_goal1.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_bodyInputFragment2_to_menuFragment)
        }
        var btn_goal2 : Button = view.findViewById(R.id.btn_goal2)
        btn_goal2.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_bodyInputFragment2_to_menuFragment)
        }
        var btn_goal3 : Button = view.findViewById(R.id.btn_goal3)
        btn_goal3.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_bodyInputFragment2_to_menuFragment)
        }
        var btn_goal4 : Button = view.findViewById(R.id.btn_goal4)
        btn_goal4.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_bodyInputFragment2_to_menuFragment)
        }
    }
}