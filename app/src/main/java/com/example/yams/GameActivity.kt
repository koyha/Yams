package com.example.yams

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val bund: Bundle? = this.intent.extras
        val playersName = bund?.getStringArrayList("playersName")

        val linearLayout: LinearLayout = findViewById(R.id.player_list_layout)

        if (playersName != null) {
            for (item in playersName)
                if (item != null) {
                    val inputText = TextView(this)
                    inputText.text = item
                    linearLayout.addView(inputText)
                }
        }

    }
}