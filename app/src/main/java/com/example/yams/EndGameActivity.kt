package com.example.yams

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class EndGameActivity : AppCompatActivity() {
    private var globalScoresheet : GlobalScoresheetFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_game)

        val bund = this.intent.extras
        globalScoresheet = bund?.getParcelable("global")

        if (globalScoresheet != null) {
            supportFragmentManager.beginTransaction().replace(R.id.end_game_global_score_sheet, globalScoresheet!!).commit()
        }

        val buttonChangePlayers: Button = findViewById(R.id.button_change_players)
        buttonChangePlayers.setOnClickListener(){
            val intent = Intent(this, GameCreationActivity::class.java)
            finishAffinity()
            startActivity(intent)
        }

        val buttonHome: Button = findViewById(R.id.button_endgame_to_homepage)
        buttonHome.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            finishAffinity()
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()

        showWinnerName()

        val storage = Storage()
        val frag = supportFragmentManager.findFragmentById(R.id.end_game_global_score_sheet) as GlobalScoresheetFragment
        storage.saveFragmentObject(this.applicationContext, frag)
    }

    private fun showWinnerName(){
        val winnerTextView = findViewById<TextView>(R.id.textview_winner)
        val playersGrandTotal : HashMap<String, Int> = HashMap()

        val scoreMap = globalScoresheet!!.getScores()

        for (playerName in scoreMap.keys) {
            playersGrandTotal[playerName] = scoreMap[playerName]!!.last()
        }

        val highestScore = playersGrandTotal.maxOf { it.value }
        val winner = playersGrandTotal.filter { highestScore == it.value }.keys.toList()

        if (winner.size >= 2){
            var txt = ""
            for(w in winner){
                txt = "$txt $w"
            }
            winnerTextView.text = txt + " " +getString(R.string.several_winners)
        }
        else{
            winnerTextView.text = winner[0].plus(" "+getString(R.string.one_winner))
        }
    }
}