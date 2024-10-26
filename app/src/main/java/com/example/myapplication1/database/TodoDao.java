package com.example.myapplication1.database;

// TodoDao.java
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication1.model.Todo;

import java.util.List;

@Dao
public interface TodoDao {
    @Insert
    void insert(Todo todo);

    @Update
    void update(Todo todo);

    @Delete
    void delete(Todo todo);

    @Query("SELECT * FROM todo_table ORDER BY id DESC")
    LiveData<List<Todo>> getAllTodos();
}
