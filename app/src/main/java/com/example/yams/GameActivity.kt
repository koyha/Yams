package com.example.yams

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import android.widget.TextView
import kotlin.collections.HashMap
import kotlin.collections.indexOf as collectionsIndexOf

class GameActivity : AppCompatActivity() {

    lateinit var  option: Spinner
    private val fragmentsMap : HashMap<String, ScoreGridFragment> = HashMap()
    val fragments : ArrayList<ScoreGridFragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val bund: Bundle? = this.intent.extras
        val playersName = bund?.getStringArrayList("playersName")

        //val fragments : ArrayList<ScoreGridFragment> = ArrayList()
        val score = InputScore()

        val globalScore = supportFragmentManager.findFragmentById(R.id.global_scoresheet) as GlobalScoresheetFragment
        if (playersName != null) {
            globalScore.setPlayers(playersName)
        }

        if (playersName != null) {
            for (playerName in playersName)
                if (playerName != null) {
                    val fragment: ScoreGridFragment = ScoreGridFragment.newInstance(playerName,playerName)
                    fragment.inputInputScore = score
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
        println(fragments[0])
        println(fragmentsMap["q"])
        print(playerName)
        println("Je suis ton ERREUUUURRRRR")

        val indexCurrentFragment: Int = fragments.indexOf(fragmentsMap[playerName] as ScoreGridFragment)

        if (indexCurrentFragment == null){
            println("WUUUUUUUUUUUUUUUUUUUUTTTTTTTTTTTTTTTTTTTTTTTTT")
        }
        val nextFragment : ScoreGridFragment
        if (indexCurrentFragment == fragments.size){
            nextFragment = fragments[0]
        }
        else {
            nextFragment = fragments[indexCurrentFragment+1]
        }
        supportFragmentManager.beginTransaction().replace(R.id.score_table, nextFragment).commit()
    }
}