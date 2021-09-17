package com.example.yams

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlin.collections.HashMap

class GameActivity : AppCompatActivity() {

    lateinit var  option: Spinner
    private val fragmentsMap : HashMap<String, ScoreGridFragment> = HashMap()
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
                    val bundlePlayerName : Bundle = Bundle()
                    bundlePlayerName.putString("playerName",playerName)
                    val fragment: ScoreGridFragment = ScoreGridFragment.newInstance(playerName,playerName)
                    fragment.inputScore = score
                    fragment.apply { arguments = Bundle().apply{
                        putString("playerName", playerName)}
                    }
                    fragments.add(fragment)
                    fragmentsMap[playerName] = fragment

                }
        }
        val dice = supportFragmentManager.findFragmentById(R.id.input_dice) as InputDice
        dice.score = score

        option = findViewById<Spinner>(R.id.spinner)
        option.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, playersName as ArrayList<String>)

        option.onItemSelectedListener = object : AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                supportFragmentManager.beginTransaction().replace(R.id.score_table, fragments[position]).commit()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }
        }
        val playerTurn : TextView = findViewById<TextView>(R.id.player_turn)
        var textPlayerTurn : String = getString(R.string.player_turn)
        playerTurn.text = textPlayerTurn.plus(playersName[0])
    }


    fun onUpdateListener() {
        val fragment = supportFragmentManager.findFragmentById(R.id.score_table) as ScoreGridFragment
        fragment.onInputChange()
    }

    fun getScores(player: String): HashMap<Int, Int> {
        //TODO: change to the wanted player
        val fragment = supportFragmentManager.findFragmentById(R.id.score_table) as ScoreGridFragment
        return fragment.scores
    }

    fun nextFragment(playerName: String){

        val indexCurrentFragment: Int = fragments.indexOf(fragmentsMap[playerName] as ScoreGridFragment)
        val indexNextFragment : Int = if (indexCurrentFragment == (fragments.size - 1)){
            0
        } else {
            indexCurrentFragment + 1
        }
        val nextFragment : ScoreGridFragment = fragments[indexNextFragment]
        val dice = this.supportFragmentManager.findFragmentById(R.id.input_dice) as InputDice
        val playerTurn : TextView = findViewById<TextView>(R.id.player_turn)
        var textPlayerTurn : String = getString(R.string.player_turn)
        playerTurn.text = textPlayerTurn.plus(fragmentsMap.keys.elementAt(indexNextFragment))

        dice.clearDiceList()
        supportFragmentManager.beginTransaction().replace(R.id.score_table, nextFragment).commit()
        option.setSelection(indexNextFragment)
    }
}