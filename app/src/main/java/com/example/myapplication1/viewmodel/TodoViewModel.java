package com.example.myapplication1.viewmodel;

// TodoViewModel.java
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication1.model.Todo;
import com.example.myapplication1.repository.TodoRepository;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {
    private TodoRepository repository;
    private LiveData<List<Todo>> allTodos;

    public TodoViewModel(@NonNull Application application) {
        super(application);
        repository = new TodoRepository(application);
        allTodos = repository.getAllTodos();
    }

    public void insert(Todo todo) {
        repository.insert(todo);
    }

    public void update(Todo todo) {
        repository.update(todo);
    }

    public void delete(Todo todo) {
        repository.delete(todo);
    }

    public LiveData<List<Todo>> getAllTodos() {
        return allTodos;
    }
}
