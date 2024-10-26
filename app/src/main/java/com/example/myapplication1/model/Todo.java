package com.example.myapplication1.model;

// Todo.java
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "todo_table")
public class Todo {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String task;
    private boolean isCompleted;

    public Todo(String task, boolean isCompleted) {
        this.task = task;
        this.isCompleted = isCompleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}

