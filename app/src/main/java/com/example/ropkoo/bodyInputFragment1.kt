package com.example.ropkoo

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.setFragmentResult
import androidx.navigation.Navigation


class bodyInputFragment1 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_body_input1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var bodyInput1 = 0;

        var btn_current1 : Button = view.findViewById(R.id.btn_current1)
        btn_current1.setOnClickListener{
            bodyInput1 = 1;
            val bundle = Bundle()
            bundle.putString("bodyInput1", "bodyInput1")
            setFragmentResult("BIF1", bundle)
            Navigation.findNavController(view).navigate(R.id.action_bodyInputFragment1_to_bodyInputFragment2)
        }
        var btn_current2 : Button = view.findViewById(R.id.btn_current2)
        btn_current2.setOnClickListener{
            bodyInput1 = 2;
            val bundle = Bundle()
            bundle.putString("bodyInput1", "bodyInput1")
            setFragmentResult("BIF1", bundle)
            Navigation.findNavController(view).navigate(R.id.action_bodyInputFragment1_to_bodyInputFragment2)
        }
        var btn_current3 : Button = view.findViewById(R.id.btn_current3)
        btn_current3.setOnClickListener{
            bodyInput1 = 3;
            val bundle = Bundle()
            bundle.putString("bodyInput1", "bodyInput1")
            setFragmentResult("BIF1", bundle)
            Navigation.findNavController(view).navigate(R.id.action_bodyInputFragment1_to_bodyInputFragment2)
        }
        var btn_current4 : Button = view.findViewById(R.id.btn_current4)
        btn_current4.setOnClickListener{
            bodyInput1 = 4;
            val bundle = Bundle()
            bundle.putString("bodyInput1", "bodyInput1")
            setFragmentResult("BIF1", bundle)
            Navigation.findNavController(view).navigate(R.id.action_bodyInputFragment1_to_bodyInputFragment2)
        }
    }
}