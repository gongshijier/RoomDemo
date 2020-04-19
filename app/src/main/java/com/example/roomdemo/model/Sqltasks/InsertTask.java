package com.example.roomdemo.model.Sqltasks;

import android.os.AsyncTask;

import com.example.roomdemo.model.Word;
import com.example.roomdemo.model.WordDao;

public class InsertTask extends AsyncTask<Word,Void,Void> {
    WordDao wordDao;

    public InsertTask(WordDao wordDao) {
        this.wordDao = wordDao;
    }

    @Override
    protected Void doInBackground(Word... words) {
        wordDao.insertWord(words);
        return null;
    }
}
