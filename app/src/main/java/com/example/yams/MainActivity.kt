package com.example.yams

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import com.example.yams.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val buttonPlay: Button = findViewById(R.id.button_play)
        buttonPlay.setOnClickListener(){
            val intent = Intent(this, GameCreationActivity::class.java)
            startActivity(intent)
        }

        val buttonStats: Button = findViewById (R.id.button_homepage_to_stats)
        buttonStats.setOnClickListener() {
            val intent = Intent(this, StatsActivity::class.java)
            startActivity(intent)
        }
    }
}