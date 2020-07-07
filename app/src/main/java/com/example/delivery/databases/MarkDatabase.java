package com.example.delivery.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.delivery.dao.MarkDao;
import com.example.delivery.templates.Mark;

@Database(entities = Mark.class, version = 1)
public abstract class MarkDatabase extends RoomDatabase {

    private static MarkDatabase instance;

    public abstract MarkDao markDao();

    public static synchronized MarkDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    MarkDatabase.class, "name_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
