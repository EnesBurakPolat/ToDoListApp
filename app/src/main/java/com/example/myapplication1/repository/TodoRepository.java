package com.example.myapplication1.repository;

// TodoRepository.java
import android.app.Application;
import androidx.lifecycle.LiveData;

import com.example.myapplication1.database.TodoDao;
import com.example.myapplication1.database.TodoDatabase;
import com.example.myapplication1.model.Todo;

import java.util.List;

public class TodoRepository {
    private TodoDao todoDao;
    private LiveData<List<Todo>> allTodos;

    public TodoRepository(Application application) {
        TodoDatabase database = TodoDatabase.getInstance(application);
        todoDao = database.todoDao();
        allTodos = todoDao.getAllTodos();
    }

    public void insert(Todo todo) {
        new Thread(() -> todoDao.insert(todo)).start();
    }

    public void update(Todo todo) {
        new Thread(() -> todoDao.update(todo)).start();
    }

    public void delete(Todo todo) {
        new Thread(() -> todoDao.delete(todo)).start();
    }

    public LiveData<List<Todo>> getAllTodos() {
        return allTodos;
    }
}
