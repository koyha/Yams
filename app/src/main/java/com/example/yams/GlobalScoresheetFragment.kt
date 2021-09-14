package com.example.yams

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GlobalScoresheetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GlobalScoresheetFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var playersName: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            playersName = it.getStringArrayList("playersName") as ArrayList<String>
        }
        println(arguments)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_global_scoresheet, container, false)

        for (player in playersName) {
            val scores = (context as GameActivity).getScores("")
            val table = v.findViewById<TableLayout>(R.id.scoresheet)
            val row: TableRow = table.getChildAt(0) as TableRow
            val text = TextView(context)
            text.text = player
            row.addView(text)
            for (i: Int in 1 until table.childCount) {
                val row: TableRow = table.getChildAt(i) as TableRow
                val tag = row.getChildAt(0).tag
                val text = TextView(context)

                text.tag = player + "_" + tag
                text.text = scores[getRightId(i)].toString()
                row.addView(text)
            }
        }

        return v
    }

    fun getRightId(row: Int): Int {
        return when (row) {
            1 -> R.id.one_score
            2 -> R.id.two_score
            3 -> R.id.three_score
            4 -> R.id.four_score
            5 -> R.id.five_score
            6 -> R.id.six_score
            7 -> R.id.bonus_score
            8 -> R.id.total_number_score
            9 -> R.id.three_kind_score
            10 -> R.id.four_kind_score
            11 -> R.id.full_house_score
            12 -> R.id.sm_straight_score
            13 -> R.id.lg_straight_score
            14 -> R.id.chance_score
            15 -> R.id.total_combination_score
            16 -> R.id.grand_total_score
            else -> 0
        }
    }

    fun setPlayers(playersList: ArrayList<String>) {
        this.playersName = playersList
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GlobalScoresheetFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(bundle: Bundle) =
            GlobalScoresheetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}