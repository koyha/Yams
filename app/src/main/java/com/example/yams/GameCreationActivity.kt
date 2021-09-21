package com.example.yams

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.google.android.material.textfield.TextInputEditText
import android.widget.*

class GameCreationActivity : AppCompatActivity() {
    private var playerCount = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_creation)

        // For production: Remove player inputs in XML
        // Then add next line
        // addPlayerInput()

        // Also remove all that
        val deleteButton: Button = findViewById(R.id.delete_button)
        deleteButton.setOnClickListener {
            val playerBox = it.parent as View
            (playerBox.parent as ViewGroup).removeView(playerBox)
        }
        val deleteButton2: Button = findViewById(R.id.delete_button2)
        deleteButton2.setOnClickListener {
            val playerBox = it.parent as View
            (playerBox.parent as ViewGroup).removeView(playerBox)
        }


        val layoutInputBox: LinearLayout = findViewById(R.id.linear_layout_inputs_text)
        val buttonAddInputText: Button = findViewById(R.id.button_add_input_text)
        buttonAddInputText.setOnClickListener(){
            addPlayerInput()
        }

        val buttonStartGame: Button = findViewById(R.id.button_start_game)
        buttonStartGame.setOnClickListener(){
            val playersName: ArrayList<String> = ArrayList()
            for (index: Int in 0 until layoutInputBox.childCount) {
                val inputText = (layoutInputBox.getChildAt(index) as LinearLayout).getChildAt(0) as TextInputEditText
                when (val name = inputText.text.toString()) {
                    "" -> {
                        Toast.makeText(applicationContext,getString(R.string.error_empty), Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    in playersName -> {
                        Toast.makeText(applicationContext,getString(R.string.error_duplicate), Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    else -> {
                        playersName.add(name)
                    }
                }
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

    private fun getInputPlaceholder(): String {
        this.playerCount += 1
        return getString(R.string.player, this.playerCount)
    }

    private fun addPlayerInput() {
        val layoutInputBox: LinearLayout = findViewById(R.id.linear_layout_inputs_text)
        val playerBox = LinearLayout(this)
        playerBox.orientation = LinearLayout.HORIZONTAL

        val inputText = TextInputEditText(this)
        inputText.hint = getInputPlaceholder()
        inputText.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f)
        playerBox.addView(inputText)

        val deleteButton = Button(this)
        deleteButton.text = "-"
        deleteButton.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 5f)
        deleteButton.setOnClickListener {
            layoutInputBox.removeView(playerBox)
        }
        playerBox.addView(deleteButton)

        layoutInputBox.addView(playerBox)
    }
}