package com.example.yams

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlin.collections.HashMap

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val bund: Bundle? = this.intent.extras
        val playersName = bund?.getStringArrayList("playersName")

        val globalScore = supportFragmentManager.findFragmentById(R.id.global_scoresheet) as GlobalScoresheetFragment
        if (playersName != null) {
            globalScore.setPlayers(playersName)
        }

        if (playersName != null) {
            for (item in playersName)
                if (item != null) {
                    val playerName = TextView(this)
                    playerName.text = item
                }
        }
    }

    override fun onStart() {
        super.onStart()

        val score = InputScore()
        val fragment = supportFragmentManager.findFragmentById(R.id.score_table) as ScoreGridFragment
        fragment.inputInputScore = score
        val dice = supportFragmentManager.findFragmentById(R.id.input_dice) as InputDice
        dice.score = score
    }

    fun onUpdateListener() {
        val fragment = supportFragmentManager.findFragmentById(R.id.score_table) as ScoreGridFragment
        fragment.onInputChange()
    }

    fun getScores(player: String): HashMap<Int, Int> {
        //TODO: change to the wanted player
        val fragment = supportFragmentManager.findFragmentById(R.id.score_table) as ScoreGridFragment
        return fragment.scores
    }
}