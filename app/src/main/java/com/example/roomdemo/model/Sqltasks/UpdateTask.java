package com.example.roomdemo.model.Sqltasks;

import android.os.AsyncTask;

import com.example.roomdemo.model.Word;
import com.example.roomdemo.model.WordDao;

public class UpdateTask extends AsyncTask<Word,Void,Void> {
    WordDao wordDao ;

    public UpdateTask(WordDao wordDao) {
        this.wordDao = wordDao;
    }

    @Override
    protected Void doInBackground(Word... words) {
        wordDao.update(words);
        return null;
    }
}
