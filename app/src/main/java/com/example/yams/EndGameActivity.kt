package com.example.yams

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class EndGameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_game)

        val buttonReplay: Button = findViewById(R.id.button_replay)
        buttonReplay.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
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
}