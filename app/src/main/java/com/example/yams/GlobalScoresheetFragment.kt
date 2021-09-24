package com.example.yams

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import java.io.Serializable
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
class GlobalScoresheetFragment: Fragment(), Serializable {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var playersName: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_global_scoresheet, container, false)

        for (player in playersName) {
            val table = v.findViewById<TableLayout>(R.id.scoresheet)
            var row: TableRow = table.getChildAt(0) as TableRow
            var text = TextView(context)
            text.text = player
            row.addView(text)
            for (i: Int in 1 until table.childCount) {
                row = table.getChildAt(i) as TableRow
                text = TextView(context)

                text.text = getString(R.string.score_default)
                row.addView(text)
            }
        }

        return v
    }

    fun updateCell(player: String, scores: HashMap<Int, Int>) {
        val table = requireView().findViewById<TableLayout>(R.id.scoresheet)
        val columnIndex = playersName.indexOf(player) + 1
        for (key in scores.keys) {
            val row = table.getChildAt(getRow(key)) as TableRow
            val score = row.getChildAt(columnIndex) as TextView
            score.text = scores[key].toString()
        }
    }

    fun getRow(id: Int): Int {
        return when (id) {
            R.id.one_score -> 1
            R.id.two_score -> 2
            R.id.three_score -> 3
            R.id.four_score -> 4
            R.id.five_score -> 5
            R.id.six_score -> 6
            R.id.bonus_score -> 7
            R.id.total_number_score -> 8
            R.id.three_kind_score -> 9
            R.id.four_kind_score -> 10
            R.id.full_house_score -> 11
            R.id.sm_straight_score -> 12
            R.id.lg_straight_score ->13
            R.id.yahtzee_score -> 14
            R.id.chance_score -> 15
            R.id.total_combination_score -> 16
            R.id.grand_total_score -> 17
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