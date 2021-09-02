package com.example.yams

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import com.google.android.material.textfield.TextInputEditText

private val Button.add: Unit
    get() {}

class GameCreationActivity : AppCompatActivity() {
    companion object {
        var count: Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_creation)

        fun playerCount(playerString: String): String{
            count += 1
            return playerString.plus(" $count")
        }
        count = 0

        val playerString: String = getString(R.string.player)
        val inputTextOne: TextInputEditText = findViewById(R.id.player_one)
        inputTextOne.hint = playerCount(playerString)
        val inputTextTwo: TextInputEditText = findViewById(R.id.player_two)
        inputTextTwo.hint = playerCount(playerString)
        val inputTextThree: TextInputEditText = findViewById(R.id.player_three)
        inputTextThree.hint = playerCount(playerString)


        val inputBox: LinearLayout = findViewById(R.id.linear_layout_inputs_text)
        val buttonAddInputText: Button = findViewById(R.id.button_add_input_text)
        buttonAddInputText.setOnClickListener(){
            val inputText = TextInputEditText(this)
            inputText.hint = playerCount(playerString)
            inputBox.addView(inputText)
        }

        val buttonHomepage: Button = findViewById (R.id.button_game_creation_to_homepage)
        buttonHomepage.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

}