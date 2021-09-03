package com.example.yams

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.collections.HashMap

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InputDice.newInstance] factory method to
 * create an instance of this fragment.
 */

class InputDice : Fragment() {
    private var diceList = HashMap<ImageButton, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input_dice, container, false)
    }

    override fun onStart() {
        super.onStart()

        val layout: ConstraintLayout = requireView().findViewById(R.id.input_dice_box)

        for (index: Int in 0 until layout.childCount) {
            val button = layout.getChildAt(index)
            button.setOnClickListener {
                addSelectedDice(index + 1)
            }
        }
    }

    private fun getDiceImage(i: Int): Int {
        when (i) {
            1 -> return R.drawable.dice_1
            2 -> return R.drawable.dice_2
            3 -> return R.drawable.dice_3
            4 -> return R.drawable.dice_4
            5 -> return R.drawable.dice_5
            6 -> return R.drawable.dice_6
            else -> return R.drawable.dice_1
        }
    }

    private fun addSelectedDice(number: Int) {
        if (diceList.size < 5 && number >= 1 && number <= 6) {
            var dice = ImageButton(this.context)
            dice.setImageResource(getDiceImage(number))
            dice.layoutParams = ViewGroup.LayoutParams(200, 250)
            dice.scaleY = 0.75f
            dice.scaleX = 0.75f
            dice.background = null

            val layout = requireView().findViewById<LinearLayout>(R.id.chosen_box)
            layout.addView(dice)
            diceList[dice] = number

            dice.setOnClickListener {
                diceList.remove(it)
                layout.removeView(it)
            }
        }
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InputDice.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InputDice().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}