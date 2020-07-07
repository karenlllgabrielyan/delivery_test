package com.example.delivery.databases;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

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
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

//------------------------------------------------------------------------------------------------------------- POPULATE
    private static RoomDatabase.Callback  roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(instance).execute();
        }
    };
    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void>{

        private MarkDao markDao;

        private PopulateDBAsyncTask(MarkDatabase db){
            markDao = db.markDao();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            markDao.insert(new Mark("Moskovyan 29/4", "Yerevan", "25486", "09:45", "08:00 - 10:00", "40.187905, 44.515924",2));
            return null;
        }
    }
}
