package com.example.yams

import android.content.Context
import androidx.fragment.app.Fragment
import java.io.*
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDateTime
import java.util.stream.Collectors

class Storage {

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

    public fun loadFragmentObject(context: Context, file: String): GlobalScoresheetFragment? {
        try {
            var myFrag: GlobalScoresheetFragment
            val fis = FileInputStream((context.getFilesDir()).toString().plus("/"+file));
            val objectInputStream = ObjectInputStream(fis);
            myFrag = objectInputStream.readObject() as GlobalScoresheetFragment
            return myFrag
        } catch (e : IOException){
            e.printStackTrace()
            return null
        }
    }

    public  fun getStoredFragmentsList(context: Context): ArrayList<String> {
        val list = ArrayList<String>()
        val files = context.fileList()
        for (file in files){
            list.add(file)
        }
        return list
    }
}