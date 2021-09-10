package com.example.yams

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val bund: Bundle? = this.intent.extras
        val playersName = bund?.getStringArrayList("playersName")

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
        val fragment = supportFragmentManager.findFragmentById(R.id.score_table) as FragmentGrille
        fragment.inputInputScore = score
        val dice = supportFragmentManager.findFragmentById(R.id.input_dice) as InputDice
        dice.score = score
    }

    fun onUpdateListener() {
        val fragment = supportFragmentManager.findFragmentById(R.id.score_table) as FragmentGrille
        fragment.onInputChange()
    }
}