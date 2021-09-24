package com.example.yams

import android.content.Context
import androidx.fragment.app.Fragment
import java.io.*
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDateTime
import java.util.stream.Collectors

class Storage {

    //TODO: Changer le nom du file que l'on save
    public fun saveFragmentObject(context: Context, myFrag : GlobalScoresheetFragment) {
        try
        {
            val currentTime = LocalDateTime.now().toString()
            val fos = context.openFileOutput(currentTime, Context.MODE_PRIVATE);
            val oos = ObjectOutputStream(fos);
            oos.writeObject(myFrag);
            oos.close();
        } catch (e : IOException) {
            e.printStackTrace();
        }
    }

    public fun loadFragmentObject(context: Context, file: String): GlobalScoresheetFragment {
        //TODO: ajouter un try catch ?
        var myFrag: GlobalScoresheetFragment
        val fileInputStream = FileInputStream((context.getFilesDir()).toString().plus("/filename"));
        val objectInputStream = ObjectInputStream(fileInputStream);
        myFrag = objectInputStream.readObject() as GlobalScoresheetFragment
        return myFrag
    }

    public  fun getStoredFragmentsList(context: Context): ArrayList<String> {
        val list = ArrayList<String>()
        val path = context.fileList()
        for (file in path){
            list.add(file)
        }
        return list
    }

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

}