package com.example.roomdemo.model.Sqltasks;

import android.os.AsyncTask;

import com.example.roomdemo.model.WordDao;

public class DeleteAllTask extends AsyncTask<Void,Void,Void> {
    WordDao wordDao ;

    public DeleteAllTask(WordDao wordDao) {
        this.wordDao = wordDao;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        wordDao.deleteAll();
        return null;
    }
}
