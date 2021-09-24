package com.example.yams

import android.content.Context
import androidx.fragment.app.Fragment
import java.io.FileInputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class Storage {

    public fun saveObject(context: Context, myHashMap : HashMap<Int, String>) {
        try
        {
            val fos = context.openFileOutput("filenameHashMap.ser", Context.MODE_PRIVATE);
            val oos = ObjectOutputStream(fos);
            oos.writeObject(myHashMap);
            oos.close();
        } catch (e : IOException) {
            e.printStackTrace();
        }
    }

    public fun loadObject(context: Context) {
        try
        {
            val fileInputStream = FileInputStream((context.getFilesDir()).toString().plus("/filenameHashMap.ser"));
            val objectInputStream = ObjectInputStream(fileInputStream);
            val myHashMap = objectInputStream.readObject() as Map<*, *>
            println(myHashMap)
        }
        catch(e : IOException){
            e.printStackTrace();
        }
    }


    //TODO: Changer le nom du file que l'on save
    public fun saveFragmentObject(context: Context, myFrag : GlobalScoresheetFragment) {
        try
        {
            val fos = context.openFileOutput("filename.ser", Context.MODE_PRIVATE);
            val oos = ObjectOutputStream(fos);
            oos.writeObject(myFrag);
            oos.close();
        } catch (e : IOException) {
            e.printStackTrace();
        }
    }

    public fun loadFragmentObject(context: Context): GlobalScoresheetFragment {
        //TODO: ajouter un try catch ?
        var myFrag: GlobalScoresheetFragment
        val fileInputStream = FileInputStream((context.getFilesDir()).toString().plus("/filename.ser"));
        val objectInputStream = ObjectInputStream(fileInputStream);
        myFrag = objectInputStream.readObject() as GlobalScoresheetFragment
        return myFrag
    }
}