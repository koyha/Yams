package com.example.yams

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView

class GameActivity : AppCompatActivity() {

    lateinit var  option: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val bund: Bundle? = this.intent.extras
        val playersName = bund?.getStringArrayList("playersName")

        val fragments : ArrayList<FragmentGrille> = ArrayList()
        val fragmentsHashMap : HashMap<String, FragmentGrille> = HashMap()

        if (playersName != null) {
            for (item in playersName)
                if (item != null) {
                    val fragment: FragmentGrille = FragmentGrille.newInstance(item,item)
                    fragments.add(fragment)
                    fragmentsHashMap.put(item, fragment)
                }
        }

        val score = InputScore()
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

                //fragments[position].onInputChange()
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }

    fun onUpdateListener() {
        //val fragment = supportFragmentManager.findFragmentById(R.id.score_table) as FragmentGrille
        //fragment.onInputChange()
    }
}