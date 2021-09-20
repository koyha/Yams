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
 * Use the [ScoreGridFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScoreGridFragment : Fragment() {
    var inputScore: InputScore = InputScore()
    private var normalTotal: Int = 0
    private var specialTotal: Int = 0
    val scores = HashMap<Int, Int>()
    private var isPlayerTurn = false


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
            if (scores[cell.id] != null) {
                cell.tag = "score_set"
            }
        }, "clickable", view = v)
        
        updateTable(v)
        onInputChange(v)

        println(scores[R.id.chance_score])

        return v
    }

    private fun onCellClicked(it: TextView) {
        val value = (it.text as String).toInt()
        scores[it.id] =  value

        if ("normal" in (it.tag as String)) {
            normalTotal += value
            updateNormalTotal()
        }
        if ("special" in (it.tag as String)) {
            specialTotal += value
            updateSpecialTotal()
        }

        val grandTotalCell = requireView().findViewById<TextView>(R.id.grand_total_score)
        grandTotalCell.text = (normalTotal + specialTotal).toString()
        scores[grandTotalCell.id] = normalTotal + specialTotal

        it.tag = "score_set"
        it.isClickable = false

        val bund: Bundle? = this.arguments
        val playerName : String? = bund?.getString("player_name")

        if (playerName != null) {
            (context as GameActivity).nextFragment(playerName)
        }
    }

    fun onInputChange(view: View = requireView()) {
        if (this.isPlayerTurn) {
        onEachScoreCell({ cell ->
            if (inputScore.hasDice()) {
                cell.isClickable = true
                cell.text = updateScore(cell)
                cell.setTextColor(Color.BLUE)
            } else {
                updateTable(view)
            }
        }, "clickable", view)}
    }

    private fun updateTable(view: View = requireView()) {
        onEachScoreCell({

//          scores[it.id] = 0 //pour tester

            it.setTextColor(Color.BLACK)
            it.isClickable = false
            if (scores[it.id] == null) {
                it.text = "0"
            }
            else {
                it.text = scores[it.id].toString()
            }
        }, view = view)
    }

    private fun onEachScoreCell(f: (TextView) -> Unit, tag: String = "score", view: View = requireView()) {
        val layout: TableLayout = view.findViewById(R.id.tableLayout)
        for (i: Int in 0 until layout.childCount) {
            val row: TableRow = layout.getChildAt(i) as TableRow
            for (j: Int in 0 until row.childCount) {
                val cell: TextView = row.getChildAt(j) as TextView
                if (cell.tag != null && tag in (cell.tag as String)) {
                    f(cell)
                }
            }
        }
    }

    private fun updateScore(view: TextView): String {
        val a = when (view.id) {
            R.id.one_score -> inputScore.getNormalScores(1)
            R.id.two_score -> inputScore.getNormalScores(2)
            R.id.three_score -> inputScore.getNormalScores(3)
            R.id.four_score -> inputScore.getNormalScores(4)
            R.id.five_score -> inputScore.getNormalScores(5)
            R.id.six_score -> inputScore.getNormalScores(6)
            R.id.three_kind_score -> inputScore.getSameKind(3)
            R.id.four_kind_score -> inputScore.getSameKind(4)
            R.id.sm_straight_score -> inputScore.getSmallStraight()
            R.id.lg_straight_score -> inputScore.getLargeStraight()
            R.id.full_house_score -> inputScore.getFull()
            R.id.yahtzee_score -> inputScore.getYahtzee()
            R.id.chance_score -> inputScore.getChance()
            else -> 0
        }
        return a.toString()
    }

    private fun updateNormalTotal() {
        val bonusCell = requireView().findViewById<TextView>(R.id.bonus_score)
        val totalCell = requireView().findViewById<TextView>(R.id.total_number_score)

        if (normalTotal >= 63 && bonusCell.text == "0") {
            bonusCell.text = 35.toString()
            scores[bonusCell.id] = 35
            normalTotal += 35
        } else {
            scores[bonusCell.id] = 0
        }
        totalCell.text = normalTotal.toString()
        scores[totalCell.id] = normalTotal
    }

    private fun updateSpecialTotal() {
        val totalCell = requireView().findViewById<TextView>(R.id.total_combination_score)
        totalCell.text = specialTotal.toString()
        scores[totalCell.id] = specialTotal
    }

    fun setPlayerTurn(isPlayerTurn : Boolean){
        this.isPlayerTurn = isPlayerTurn
    }

    fun didPlayerFinishScoreSheet(): Boolean {
        var finishedScoreSheet: Boolean = true
        onEachScoreCell({ cell ->
            if (scores[cell.id] == null){
                finishedScoreSheet = false
            }
        })
        return finishedScoreSheet
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
            ScoreGridFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}