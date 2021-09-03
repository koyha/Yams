package com.example.yams

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class GameCreationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_creation)

        val backButton: ImageButton = findViewById (R.id.back_button)
        backButton.setOnClickListener() {
            finish()
        }

        val createGameButton: Button = findViewById(R.id.button_game_creation_to_homepage)
        createGameButton.setOnClickListener() {
            val intent = Intent(this, Game::class.java)
            startActivity(intent)
        }
    }
}