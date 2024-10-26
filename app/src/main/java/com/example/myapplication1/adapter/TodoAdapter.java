package com.example.myapplication1.adapter;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.MainActivity;
import com.example.myapplication1.R;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    private List<String> todoList;
    private MainActivity mainActivity;
    private SharedPreferences sharedPreferences;

    public TodoAdapter(List<String> todoList, MainActivity mainActivity, SharedPreferences sharedPreferences) {
        this.todoList = todoList;
        this.mainActivity = mainActivity;
        this.sharedPreferences = sharedPreferences;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_item, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        String task = todoList.get(position);
        holder.textViewTask.setText(task);

        // Tik durumunu SharedPreferences'tan yükleyin
        boolean isCompleted = sharedPreferences.getBoolean(task, false);
        holder.checkBoxCompleted.setChecked(isCompleted);

        holder.checkBoxCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Tik durumunu SharedPreferences'a kaydedin
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(task, isChecked);
            editor.apply();
        });

        holder.itemView.setOnLongClickListener(v -> {
            // Uzun tıklama ile öğeyi sil
            todoList.remove(position);
            notifyItemRemoved(position);
            mainActivity.saveTodoList(); // Listeyi güncelle ve kaydet

            // Silinen görevin tik durumunu SharedPreferences'tan kaldırın
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(task);
            editor.apply();

            Toast.makeText(mainActivity, "Task removed", Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    static class TodoViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTask;
        CheckBox checkBoxCompleted;

        public TodoViewHolder(View itemView) {
            super(itemView);
            textViewTask = itemView.findViewById(R.id.text_view_task);
            checkBoxCompleted = itemView.findViewById(R.id.checkbox_completed);
        }
    }
}