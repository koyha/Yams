package com.example.yams

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentGrille.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentGrille : Fragment() {
    var inputInputScore: InputScore = InputScore()
    private var normalTotal: Int = 0
    private var specialTotal: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_score_grid, container, false)

        onEachScoreCell({ cell ->
            cell.setOnClickListener {
                onCellClicked(it as TextView)
            }
        }, v)

        return v
    }

    private fun onCellClicked(it: TextView) {
        // WIP
        it.setTextColor(Color.BLACK)
        if ("normal" in (it.tag as String)) {
            normalTotal += (it.text as String).toInt()
            updateNormalTotal()
        }
        if ("special" in (it.tag as String)) {
            specialTotal += (it.text as String).toInt()
            updateSpecialTotal()
        }

        val grandTotalCell = requireView().findViewById<TextView>(R.id.grand_total_score)
        grandTotalCell.text = (normalTotal + specialTotal).toString()

        it.tag = "score_set"
    }

    fun onInputChange() {
        onEachScoreCell({ cell ->
            cell.text = updateScore(cell)
            cell.setTextColor(Color.BLUE)
        })
    }

    private fun onEachScoreCell(f: (TextView) -> Unit, view: View = requireView()) {
        val layout: TableLayout = view.findViewById(R.id.tableLayout)
        for (i: Int in 0 until layout.childCount) {
            val row: TableRow = layout.getChildAt(i) as TableRow
            for (j: Int in 0 until row.childCount) {
                val cell: TextView = row.getChildAt(j) as TextView
                if (cell.tag != null && "clickable" in (cell.tag as String)) {
                    f(cell)
                }
            }
        }
    }

    private fun updateScore(view: TextView): String {
        val a = when (view.id) {
            R.id.one_score -> inputInputScore.getNormalScores(1)
            R.id.two_score -> inputInputScore.getNormalScores(2)
            R.id.three_score -> inputInputScore.getNormalScores(3)
            R.id.four_score -> inputInputScore.getNormalScores(4)
            R.id.five_score -> inputInputScore.getNormalScores(5)
            R.id.six_score -> inputInputScore.getNormalScores(6)
            R.id.three_kind_score -> inputInputScore.getSameKind(3)
            R.id.four_kind_score -> inputInputScore.getSameKind(4)
            // TODO: change those
            R.id.sm_street_score -> inputInputScore.getSmallStraight()
            R.id.lg_street_score -> inputInputScore.getLargeStraight()
            R.id.full_house_score -> inputInputScore.getFull()
            R.id.yahtzee_score -> inputInputScore.getYahtzee()
            R.id.chance_score -> inputInputScore.getChance()
            else -> 0
        }
        return a.toString()
    }

    private fun updateNormalTotal() {
        val bonusCell = requireView().findViewById<TextView>(R.id.bonus_score)
        val totalCell = requireView().findViewById<TextView>(R.id.total_number_score)

        if (normalTotal >= 63 && bonusCell.text == "0") {
            bonusCell.text = 35.toString()
            normalTotal += 35
        }
        totalCell.text = normalTotal.toString()
    }

    private fun updateSpecialTotal() {
        val totalCell = requireView().findViewById<TextView>(R.id.total_combination_score)
        totalCell.text = specialTotal.toString()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentGrille.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentGrille().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}