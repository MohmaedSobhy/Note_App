package com.example.noteapp.Model;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Note.class},version = 1)
public abstract class MyDataBase extends RoomDatabase {
public abstract Access access();
public static MyDataBase database;

     public static MyDataBase getInstance(Context context)
     {
        if(database==null)
        {
           database= Room.databaseBuilder(context,MyDataBase.class,"MyDataBase")
                   .allowMainThreadQueries()
                   .build();

        }
        return database;
      }

}
