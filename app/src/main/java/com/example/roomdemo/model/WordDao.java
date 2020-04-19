package com.example.roomdemo.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WordDao {
    @Insert
    void insertWord(Word... words);

    @Update
    void update(Word... words);
    @Delete
    void delete(Word... words);
    @Query("DELETE FROM WORD")
    void deleteAll();
    @Query("SELECT * FROM WORD ORDER BY ID DESC")
    LiveData<List<Word>> getAll();
}
