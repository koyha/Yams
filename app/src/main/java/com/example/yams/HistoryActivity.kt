package com.example.yams

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setMargins
import androidx.fragment.app.FragmentContainerView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.collections.ArrayList

class HistoryActivity : AppCompatActivity() {
    private val storage = Storage()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history_stats)

        val backButton: ImageButton = findViewById (R.id.back_button)
        backButton.setOnClickListener() {
            finish()
        }

        // dummy fragment to initialize the container
        val glo = GlobalScoresheetFragment()
        val players = ArrayList<String>()
        players.add("")
        glo.setPlayers(players)
        supportFragmentManager.beginTransaction().replace(R.id.history_global_scoresheet, glo).commit()

        // loop that gets files(the games) one by one
        for (file in storage.getStoredFragmentsList(this)) {
            addGame(file)
        }

        val v = findViewById<ConstraintLayout>(R.id.history_root)
        v?.findViewById<ConstraintLayout>(R.id.history_root)?.setOnClickListener {
            val scoresheet = findViewById<FragmentContainerView>(R.id.history_global_scoresheet)

            if (scoresheet != null && scoresheet.visibility == View.VISIBLE) {
                scoresheet.visibility = View.GONE
            }
        }
    }

    private fun addGame(filename : String) {
        // add a game in the linearLayout of the scrollview
        val scrollView : ScrollView = findViewById(R.id.scrollview_history)
        val layoutScrollGames: LinearLayout = findViewById(R.id.linear_layout_history)

        val gameTextView = TextView(this)
        val date = LocalDateTime.parse(filename)
        val formatted = date.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT))
        gameTextView.text = formatted
        gameTextView.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f)
        (gameTextView.layoutParams as ViewGroup.MarginLayoutParams).setMargins(15)

        layoutScrollGames.addView(gameTextView)

        gameTextView.setOnClickListener{
            showGlobalScoresheet(storage.loadFragmentObject(this, filename) as GlobalScoresheetFragment, scrollView,layoutScrollGames)
        }
    }

    private fun showGlobalScoresheet(globalScoresheetFragment : GlobalScoresheetFragment, scrollView: ScrollView ,linear: LinearLayout) {
        // Display the clicked gamed of the history list, click on the scoresheet to close it
        supportFragmentManager.beginTransaction().replace(R.id.history_global_scoresheet, globalScoresheetFragment).commit()

        val scoresheet = findViewById<FragmentContainerView>(R.id.history_global_scoresheet)
        scoresheet.visibility = View.VISIBLE
        scoresheet.setOnClickListener {  }
    }
}