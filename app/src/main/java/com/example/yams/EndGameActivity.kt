package com.example.yams

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

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
    }
}