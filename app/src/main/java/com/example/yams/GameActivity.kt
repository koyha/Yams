package com.example.yams

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.FragmentContainerView

class GameActivity : AppCompatActivity() {

    lateinit var  option: Spinner
    val fragments : ArrayList<ScoreGridFragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val bund: Bundle? = this.intent.extras
        val playersName = bund?.getStringArrayList("playersName")

        val score = InputScore()

        val globalScore = supportFragmentManager.findFragmentById(R.id.global_scoresheet) as GlobalScoresheetFragment
        if (playersName != null) {
            globalScore.setPlayers(playersName)
        }

        if (playersName != null) {
            for (playerName in playersName)
                if (playerName != null) {
                    val fragment = ScoreGridFragment()
                    fragment.player = playerName
                    fragment.inputScore = score

                    fragments.add(fragment)
                }
        }
        val dice = supportFragmentManager.findFragmentById(R.id.input_dice) as InputDice
        dice.score = score

        option = findViewById(R.id.spinner)
        option.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, playersName as ArrayList<String>)

        option.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                supportFragmentManager.beginTransaction().replace(R.id.score_table, fragments[position]).commit()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        val playerTurn = findViewById<TextView>(R.id.player_turn)
        val textPlayerTurn : String = getString(R.string.player_turn)
        playerTurn.text = textPlayerTurn.plus(playersName[0])
        fragments[0].setPlayerTurn(true)

        playerTurn.setOnClickListener {
            var activeFragment: ScoreGridFragment? = null
            for (fragment in fragments) {
                if (fragment.getPlayerTurn())
                    activeFragment = fragment
            }
            if (activeFragment != null) {
                supportFragmentManager.beginTransaction().replace(R.id.score_table, activeFragment).commit()
                option.setSelection(fragments.indexOf(activeFragment))
            }
        }

        val scoresheetButton = findViewById<Button>(R.id.global_scoresheet_button)
        scoresheetButton.setOnClickListener {
            showGlobalScoresheet()
        }
    }

    fun onUpdateListener() {
        val fragment = supportFragmentManager.findFragmentById(R.id.score_table) as ScoreGridFragment
        fragment.onInputChange()
    }

    fun nextFragment(currentFragment: ScoreGridFragment){
        // Clear dices
        val dice = this.supportFragmentManager.findFragmentById(R.id.input_dice) as InputDice
        dice.clearDiceList()

        // Change active player
        val indexCurrentFragment: Int = fragments.indexOf(currentFragment)
        val indexNextFragment : Int = if (indexCurrentFragment == (fragments.size - 1)){
            0
        } else {
            indexCurrentFragment + 1
        }
        val nextFragment : ScoreGridFragment = fragments[indexNextFragment]

        currentFragment.setPlayerTurn(false)
        nextFragment.setPlayerTurn(true)

        supportFragmentManager.beginTransaction().replace(R.id.score_table, nextFragment).commit()
        option.setSelection(indexNextFragment)

        val playerTurnTextView = findViewById<TextView>(R.id.player_turn)
        val textPlayerTurn = getString(R.string.player_turn)
        playerTurnTextView.text = textPlayerTurn.plus(nextFragment.player)

        // Update global scores
        val globalScoresheet = supportFragmentManager.findFragmentById(R.id.global_scoresheet) as GlobalScoresheetFragment
        globalScoresheet.updateCell(currentFragment.player, currentFragment.scores)
    }

    private fun showGlobalScoresheet() {
        val scoresheet = findViewById<FragmentContainerView>(R.id.global_scoresheet)
        if (scoresheet.visibility == View.VISIBLE) {
            scoresheet.visibility = View.GONE
        } else {
            scoresheet.visibility = View.VISIBLE
        }
    }
}