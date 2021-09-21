package com.example.yams

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class EndGameActivity : AppCompatActivity() {

    private var bund : Bundle? = null
    private var playersName : ArrayList<String>? = null
    private var playersFragment : ArrayList<ScoreGridFragment>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_game)

        bund = this.intent.extras
        playersName = bund?.getStringArrayList("playersName")
        playersFragment = bund?.getParcelableArrayList<ScoreGridFragment>("playersFragment")

        val globalScore = supportFragmentManager.findFragmentById(R.id.end_game_global_score_sheet) as GlobalScoresheetFragment
        if (playersName != null) {
            globalScore.setPlayers(playersName as ArrayList<String>)
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

        val globalScore = supportFragmentManager.findFragmentById(R.id.end_game_global_score_sheet) as GlobalScoresheetFragment
        if ( playersFragment != null) {
            for (playerFragment in playersFragment!!){
                globalScore.updateCell(playerFragment.player, playerFragment.scores)
            }
        }
        showWinnerName()
    }

    private fun showWinnerName(){
        val winnerTextView = findViewById<TextView>(R.id.textview_winner)
        var playersGrandTotal : HashMap<String, Int> = HashMap()

        for (playerFragment in playersFragment!!) {
            playersGrandTotal[playerFragment.player] = playerFragment.scores[R.id.grand_total_score]!!
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