package com.example.yams

import android.graphics.Typeface
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.DisplayMetrics
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import java.io.Serializable
import java.util.ArrayList
import java.util.*
import kotlin.collections.HashMap

class GlobalScoresheetFragment() : Fragment(), Serializable,  Parcelable {
    private var playersName: ArrayList<String> = ArrayList()
    private var scores : HashMap<String, ArrayList<Int>> = HashMap()

    constructor(parcel: Parcel) : this() {
        playersName = parcel.readArrayList(ClassLoader.getSystemClassLoader()) as ArrayList<String>
        scores = parcel.readHashMap(ClassLoader.getSystemClassLoader()) as HashMap<String, ArrayList<Int>>
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_global_scoresheet, container, false)

        val displayMetrics = DisplayMetrics()
        this.activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        val cellWidth = if (playersName.size > 4) width/6 else 0

        for (player in playersName) {
            val table = v.findViewById<TableLayout>(R.id.scoresheet)
            var row: TableRow = table.getChildAt(0) as TableRow
            var text = TextView(context)

            text.background = ResourcesCompat.getDrawable(resources, R.drawable.outline_bottom, null)
            text.layoutParams = TableRow.LayoutParams(cellWidth, TableRow.LayoutParams.MATCH_PARENT)
            text.gravity = Gravity.CENTER_VERTICAL
            text.setPadding(resources.getDimensionPixelSize(R.dimen.text_padding), 0, resources.getDimensionPixelSize(R.dimen.text_padding), 0)
            text.maxLines = 1

            text.text = player
            row.addView(text)
            for (i: Int in 1 until table.childCount) {
                row = table.getChildAt(i) as TableRow
                text = TextView(context)

                val drawable = when (i) {
                    8, 16, 17 -> R.drawable.total_row
                    else -> R.drawable.outline_bottom_right
                }

                text.background = ResourcesCompat.getDrawable(resources, drawable, null)
                text.layoutParams = TableRow.LayoutParams(cellWidth, TableRow.LayoutParams.MATCH_PARENT)
                text.gravity = Gravity.CENTER_VERTICAL
                text.setPadding(resources.getDimensionPixelSize(R.dimen.text_padding), 0, 0, 0)

                row.addView(text)
            }
        }

        showTable(v)
        return v
    }

    fun updateCell(player: String, scores: HashMap<Int, Int>) {
        for (key in scores.keys) {
            this.scores[player]?.set(getRow(key) - 1, scores[key]!!)
        }
        showTable(requireView())
    }

    fun getWinners(): ArrayList<String> {
        var winners = ArrayList<String>()
        winners.add("")

        for (player in playersName) {
            val currentTotal = this.scores[player]!!.last()
            val maxTotal = this.scores[winners[0]]?.last() ?: 0
            if (winners.isEmpty() || currentTotal > maxTotal) {
                winners = ArrayList()
                winners.add(player)
            } else if (currentTotal == maxTotal) {
                winners.add(player)
            }
        }

        return winners
    }

    private fun showTable(view: View) {
        val table = view.findViewById<TableLayout>(R.id.scoresheet)
        for (playerIndex in playersName.indices) {
            for (i: Int in 1 until table.childCount) {
                val row = table.getChildAt(i) as TableRow
                val text = row.getChildAt(playerIndex) as TextView

                text.text = (this.scores[playersName[playerIndex]]?.get(i - 1)).toString()
            }
        }

        for (player in getWinners()) {
            val lastRow = table.getChildAt(table.childCount-1) as TableRow
            val text = lastRow.getChildAt(playersName.indexOf(player)) as TextView?
            text?.typeface = Typeface.DEFAULT_BOLD
        }
    }

    private fun getRow(id: Int): Int {
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
        for (player in playersList) {
            scores[player] = ArrayList(Collections.nCopies(17, 0))
        }
    }

    fun getScores(): HashMap<String, ArrayList<Int>> {
        return scores
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeArray(playersName.toArray())
        parcel.writeMap(scores)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GlobalScoresheetFragment> {
        override fun createFromParcel(parcel: Parcel): GlobalScoresheetFragment {
            return GlobalScoresheetFragment(parcel)
        }

        override fun newArray(size: Int): Array<GlobalScoresheetFragment?> {
            return arrayOfNulls(size)
        }
    }
}