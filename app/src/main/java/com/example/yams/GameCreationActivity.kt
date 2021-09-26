package com.example.yams

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import com.google.android.material.textfield.TextInputEditText
import android.widget.*
import androidx.core.content.res.ResourcesCompat

class GameCreationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_creation)

         addPlayerInput()

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

    private fun addPlayerInput() {
        val layoutInputBox: LinearLayout = findViewById(R.id.linear_layout_inputs_text)
        val playerBox = LinearLayout(this)
        playerBox.orientation = LinearLayout.HORIZONTAL

        val inputText = TextInputEditText(this)
        inputText.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f)
        playerBox.addView(inputText)

        val deleteButton = Button(this)
        deleteButton.text = "-"
        deleteButton.setTextColor(getColor(R.color.white))
        deleteButton.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 5f)
        deleteButton.background = ResourcesCompat.getDrawable(resources, R.drawable.button, null)
        (deleteButton.layoutParams as ViewGroup.MarginLayoutParams).setMargins(0, 10, 0, 10)
        deleteButton.setOnClickListener {
            layoutInputBox.removeView(playerBox)
            updateHints()
        }
        playerBox.addView(deleteButton)

        layoutInputBox.addView(playerBox)

        updateHints()
        inputText.requestFocus()
        findViewById<ScrollView>(R.id.scrollView).scrollToDescendant(playerBox)
    }

    private fun updateHints() {
        val layout = findViewById<LinearLayout>(R.id.linear_layout_inputs_text)
        for (i in 0 until layout.childCount) {
            val text = (layout.getChildAt(i) as ViewGroup).getChildAt(0) as TextInputEditText
            text.hint = getString(R.string.player, i + 1)
        }
    }
}