package com.example.yams

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
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

        val layoutInputBox: LinearLayout = findViewById(R.id.linear_layout_inputs_text)
        val buttonAddInputText: Button = findViewById(R.id.button_add_input_text)
        buttonAddInputText.setOnClickListener(){
            val inputText = TextInputEditText(this)
            inputText.hint = playerCount(playerString)
            layoutInputBox.addView(inputText)
        }

        val buttonStartGame: Button = findViewById(R.id.button_start_game)
        buttonStartGame.setOnClickListener(){
            var playersName: ArrayList<String> = ArrayList()
            for (index: Int in 0 until layoutInputBox.childCount) {
                val inputText = layoutInputBox.getChildAt(index) as TextInputEditText
                playersName.add(inputText.text.toString())
            }

            val intent = Intent(this, GameActivity::class.java)
            val bund = Bundle()
            bund.putStringArrayList("playersName", playersName)
            intent.putExtras(bund)
            startActivity(intent)
        }

        val backButton: ImageButton = findViewById (R.id.back_button)
        backButton.setOnClickListener() {
            finish()
        }
    }
}