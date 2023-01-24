package com.example.ropkoo

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_profile.*
import org.w3c.dom.Text


class profileFragment : Fragment() {

    var DBusername: String? = null
    var DBAge: String? = null
    var DBHeight: String? = null
    var DBWeight: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = view.findViewById<TextView>(R.id.name)
        val age = view.findViewById<TextView>(R.id.age)
        val weight = view.findViewById<TextView>(R.id.weight)
        val height = view.findViewById<TextView>(R.id.height)
        val tv_bmi = view.findViewById<TextView>(R.id.BMI)

        setFragmentResultListener("MF") { _, bundle ->
            DBusername = bundle.getString("DBUsername")
            DBAge = bundle.getString("DBAge")
            DBWeight = bundle.getString("DBWeight")
            DBHeight = bundle.getString("DBHeight")

            name.setText(DBusername)
            age.setText(DBAge)
            weight.setText(DBWeight)
            height.setText(DBHeight)
            val result = String.format("%.1f", calculate(DBWeight!!.toFloat(), DBHeight!!.toFloat()))
            tv_bmi.setText(result)

            var ib_back: ImageButton = view.findViewById(R.id.ib_back)
            ib_back.setOnClickListener {
                Navigation.findNavController(view)
                    .navigate(R.id.action_profileFragment_to_menuFragment)
            }
        }
    }

    private fun calculate(weight: Float, height: Float): Float{
        val bmicalc = weight / ((height/100) * (height/100))
        return bmicalc
    }
}