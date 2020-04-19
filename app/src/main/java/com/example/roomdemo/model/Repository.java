package com.example.roomdemo.model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.roomdemo.model.Sqltasks.DeleteAllTask;
import com.example.roomdemo.model.Sqltasks.DeleteTask;
import com.example.roomdemo.model.Sqltasks.InsertTask;
import com.example.roomdemo.model.Sqltasks.UpdateTask;

import java.util.List;

public class Repository {
    private LiveData<List<Word>> wordsLive;

    public LiveData<List<Word>> getWordsLive() {
        return wordsLive;
    }

    //用来调用数据库操作
    private WordDao wordDao;

    public Repository(Context context) {
        WordRoomDatabase wordRoomDatabase = WordRoomDatabase.getInstance(context.getApplicationContext());
        wordDao = wordRoomDatabase.getWordDao();
        wordsLive = wordDao.getAll();
    }
   public   void insert(Word... words){
        new InsertTask(wordDao).execute(words);

    }
 public    void delete(Word... words){
        new DeleteTask(wordDao).execute(words);

    }
 public    void update(Word... words){
        new UpdateTask(wordDao).execute(words);

    }
  public   void deleteAll(){
        new DeleteAllTask(wordDao).execute();

    }



}
