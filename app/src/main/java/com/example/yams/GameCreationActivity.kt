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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_creation)

        val playerString: String = getString(R.string.player)
        val inputTextOne: TextInputEditText = findViewById(R.id.player_one)
        inputTextOne.hint = playerString.plus(" 1")
        val inputTextTwo: TextInputEditText = findViewById(R.id.player_two)
        inputTextTwo.hint = playerString.plus(" 2")
        val inputTextThree: TextInputEditText = findViewById(R.id.player_three)
        inputTextThree.hint = playerString.plus(" 3")
        // TODO: Implémenter fonction count et la call 3 fois au set up

        val inputBox: LinearLayout = findViewById(R.id.linear_layout_inputs_text)
        var counter: Int = 3
        val buttonAddInputText: Button = findViewById(R.id.button_add_input_text)
        buttonAddInputText.setOnClickListener(){
            counter += 1
            val inputText = TextInputEditText(this)
            inputText.hint = playerString.plus(" $counter")

            inputBox.addView(inputText)
        }


        val buttonHomepage: Button = findViewById (R.id.button_game_creation_to_homepage)
        buttonHomepage.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}