package com.example.yams

import android.graphics.Color
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import java.io.Serializable


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ScoreGridFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScoreGridFragment() : Fragment(), Serializable, Parcelable {
    var inputScore: InputScore = InputScore()
    private var normalTotal: Int = 0
    private var specialTotal: Int = 0
    var scores = HashMap<Int, Int>()
    private var isPlayerTurn = false
    var player: String = ""

    constructor(parcel: Parcel) : this() {
        normalTotal = parcel.readInt()
        specialTotal = parcel.readInt()
        isPlayerTurn = parcel.readByte() != 0.toByte()
        player = parcel.readString() ?: ""
        scores = parcel.readHashMap(ClassLoader.getSystemClassLoader()) as HashMap<Int, Int>
    }

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

        (context as GameActivity).nextFragment(this)
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

            it.isClickable = false
            if (scores[it.id] == null) {
                it.text = "0"
                it.setTextColor(Color.DKGRAY)
            }
            else {
                it.text = scores[it.id].toString()
                it.setTextColor(Color.BLACK)
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

    fun getPlayerTurn(): Boolean {
        return this.isPlayerTurn
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

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(normalTotal)
        parcel.writeInt(specialTotal)
        parcel.writeByte(if (isPlayerTurn) 1 else 0)
        parcel.writeString(player)
        parcel.writeMap(scores)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ScoreGridFragment> {
        override fun createFromParcel(parcel: Parcel): ScoreGridFragment {
            return ScoreGridFragment(parcel)
        }

        override fun newArray(size: Int): Array<ScoreGridFragment?> {
            return arrayOfNulls(size)
        }
    }
}