package com.example.myapplication1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication1.adapter.TodoAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TodoAdapter todoAdapter;
    private List<String> todoList;
    private EditText editTextTask;
    private Button buttonAdd;
    private SharedPreferences sharedPreferences;

    private static final String PREFS_NAME = "todo_prefs";
    private static final String TODO_LIST_KEY = "todo_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // UI bileşenlerini başlat
        editTextTask = findViewById(R.id.edit_text_task);
        buttonAdd = findViewById(R.id.button_add);
        recyclerView = findViewById(R.id.recycler_view);

        // SharedPreferences nesnesini başlat
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        todoList = loadTodoList(); // Önceden kaydedilen Todo öğelerini yükle
        todoAdapter = new TodoAdapter(todoList, this, sharedPreferences); // SharedPreferences'ı iletin
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(todoAdapter);

        // Butona tıklandığında yeni görev ekle
        buttonAdd.setOnClickListener(view -> {
            String task = editTextTask.getText().toString();
            if (!TextUtils.isEmpty(task)) {
                todoList.add(task);
                editTextTask.setText("");
                todoAdapter.notifyItemInserted(todoList.size() - 1);
                recyclerView.scrollToPosition(todoList.size() - 1); // Yeni öğeye kaydır
                saveTodoList(); // Güncellenmiş listeyi kaydet
            }
        });

        // ItemTouchHelper oluşturun ve RecyclerView'a ekleyin
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false; // Sürükle-bırak devre dışı bırakıldı
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                String task = todoList.get(position);

                // Görevi listeden ve SharedPreferences'tan kaldırın
                todoList.remove(position);
                todoAdapter.notifyItemRemoved(position);
                saveTodoList();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(task);
                editor.apply();

                Toast.makeText(MainActivity.this, "Task removed", Toast.LENGTH_SHORT).show();
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    // Todo listini kaydet
    public void saveTodoList() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        StringBuilder stringBuilder = new StringBuilder();
        for (String task : todoList) {
            stringBuilder.append(task).append(",");
        }
        editor.putString(TODO_LIST_KEY, stringBuilder.toString());
        editor.apply();
    }

    // Todo listini yükle
    private List<String> loadTodoList() {
        List<String> tasks = new ArrayList<>();
        String savedTasks = sharedPreferences.getString(TODO_LIST_KEY, "");
        if (!TextUtils.isEmpty(savedTasks)) {
            String[] tasksArray = savedTasks.split(",");
            for (String task : tasksArray) {
                if (!TextUtils.isEmpty(task)) {
                    tasks.add(task);
                }
            }
        }
        return tasks;
    }
}