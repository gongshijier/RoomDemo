package com.example.roomdemo.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {Word.class},version = 1,exportSchema = false)
public abstract  class WordRoomDatabase extends RoomDatabase {
    public abstract WordDao getWordDao();
    private static  WordRoomDatabase INSTANCE;
    public  static  WordRoomDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (WordRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WordRoomDatabase.class, "word_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
