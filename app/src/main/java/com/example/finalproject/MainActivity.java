package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //public static final String name = "com.example.myfirstapp.MESSAGE";
    Context context;
    String newTaskType = "";
    String taskName = "";
    DBHelper DB;
    RecyclerView recyclerView;
    RecyclerAdapter adapter;

    ArrayList<String[]> allTasks = new ArrayList<>();

    String[] tasks = {
            "January", "February", "March",
            "April", "May", "June",
            "July", "August", "September",
            "October", "November", "December"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // this.context = context;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DB = new DBHelper(this);
        Cursor res = DB.getTasks();

        if(res.getCount() == 0) {
            Toast.makeText(MainActivity.this, "No Entry Exists", Toast.LENGTH_SHORT).show();

        }

        StringBuffer buffer = new StringBuffer();

        while(res.moveToNext()) {
            allTasks.add(new String[]{ res.getString(0), res.getString(1), res.getString(2)});
            /*
            buffer.append("Name: " + res.getString(0) + "\n");
            buffer.append("Type: " + res.getString(1) + "\n");
            buffer.append("Date of Birth: " + res.getString(2) + "\n\n");
            */
        }

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapter(this, allTasks);
        recyclerView.setAdapter(adapter);



        FloatingActionButton addTaskButton = (FloatingActionButton ) findViewById(R.id.addTask);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String newActivityType = "";
                EditText name = findViewById(R.id.newTaskName);
                taskName = name.getText().toString();
                if (!taskName.equals("")) {
                    Intent intent = null;
                    switch (newTaskType) {
                        case "list":
                            intent = new Intent(v.getContext(), ListTask.class);
                            createNewTask(intent, "list");
                            break;
                        case "progress":
                            intent = new Intent(v.getContext(),  ProgressTask.class);
                            createNewTask(intent, "progress");
                            break;
                        case "reminder":
                            intent = new Intent(v.getContext(), ReminderTask.class);
                            createNewTask(intent, "reminder");
                            break;
                        default:
                            Toast.makeText(v.getContext(), "No list type selected", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
                else Toast.makeText(v.getContext(), "Task name cannot be empty", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void createNewTask(Intent intent, String type){

    DB.addTask(type, taskName);
    Cursor res = DB.getTask(taskName);
    res.moveToNext();
    intent.putExtra("id", res.getString(0));
    intent.putExtra("name", taskName);
    intent.putExtra("type", type);

    startActivity(intent);

    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.listTask:
                if (checked)
                    newTaskType = "list";
                    break;
            case R.id.progressTask:
                if (checked)
                    newTaskType = "progress";
                    break;
            case R.id.reminderTask:
                if (checked)
                    newTaskType = "reminder";
                    break;

        }
    }
}