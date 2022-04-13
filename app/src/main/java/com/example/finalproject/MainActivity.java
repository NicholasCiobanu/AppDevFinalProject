package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    Context context;
    String newTaskType = "";
    String taskName = "";
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // this.context = context;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DB = new DBHelper(this);
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
                            createNewTask(intent);
                            break;
                        case "progress":
                            intent = new Intent(v.getContext(),  ProgressTask.class);
                            createNewTask(intent);
                            break;
                        case "reminder":
                            intent = new Intent(v.getContext(), ReminderTask.class);
                            createNewTask(intent);
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

    private void createNewTask(Intent intent){

    intent.putExtra(EXTRA_MESSAGE, taskName);
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