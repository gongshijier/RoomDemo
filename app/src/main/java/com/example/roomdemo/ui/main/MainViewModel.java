package com.example.roomdemo.ui.main;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.roomdemo.model.Repository;
import com.example.roomdemo.model.Word;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    // ViewModel 没有很好的方法来获得一个Context 需要继承AndroidViewModel
    Repository repository;

    public MainViewModel(Application application) {
        super(application);
        this.repository = new Repository(application);
    }

  public   LiveData<List<Word>> getAll(){
        return repository.getWordsLive();
    }

   public void insert(Word... words){
        repository.insert(words);
    }

   public void update(Word... words){
        repository.update(words);
    }

   public void delete(Word... words){
        repository.delete(words);
    }

   public void deleteAll(){
        repository.deleteAll();
    }

}
