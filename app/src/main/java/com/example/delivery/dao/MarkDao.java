package com.example.delivery.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.delivery.templates.Mark;

import java.util.ArrayList;

@Dao
public interface MarkDao {

    @Insert
    void insert(Mark mark);

    @Update
    void update(Mark mark);

    @Delete
    void  delete(Mark mark);

    @Query("DELETE FROM mark_table")
    void deleteAllMarks();

    @Query("SELECT * FROM mark_table ORDER BY id DESC")
    LiveData<ArrayList<Mark>> getAllMarks();
}
