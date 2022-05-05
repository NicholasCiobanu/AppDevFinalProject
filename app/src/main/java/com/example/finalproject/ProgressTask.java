package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ProgressTask extends AppCompatActivity {

    String taskName;
    String taskType;
    String taskID;
    int min;
    int max;
    int dailyObjective;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_task);

        Intent intent = getIntent();
        taskName = intent.getStringExtra("name");
        taskType = intent.getStringExtra("type");
        taskID = intent.getStringExtra("id");
        TextView textView = findViewById(R.id.taskName);
        textView.setText(taskName);
        Button button = (Button) findViewById(R.id.alltasks);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        Button button2 = (Button) findViewById(R.id.createTask);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DBHelper DB = new DBHelper(v.getContext());

                EditText min = (EditText) findViewById(R.id.minimum);
                EditText max = (EditText) findViewById(R.id.maximum);
                EditText objective = (EditText) findViewById(R.id.objective);
                if (TextUtils.isEmpty(min.getText().toString())
                        || TextUtils.isEmpty(max.getText().toString())
                        || TextUtils.isEmpty(objective.getText().toString())
                        || Integer.parseInt(min.getText().toString()) > Integer.parseInt(max.getText().toString())
                        || Integer.parseInt(objective.getText().toString()) > Integer.parseInt(max.getText().toString())) {
                    Toast.makeText(ProgressTask.this, "All fields must be filled correctly", Toast.LENGTH_SHORT).show();


                }else{
                    DB.addProgress(Integer.parseInt(taskID), taskName, Integer.parseInt(min.getText().toString()), Integer.parseInt(max.getText().toString()), objective.getText().toString());
                    Intent intent = new Intent(v.getContext(), ExistingProgressTask.class);
                    intent.putExtra("id", taskID);
                    intent.putExtra("name", taskName);
                    intent.putExtra("type", taskType);
                    intent.putExtra("min", Integer.parseInt(min.getText().toString()));
                    intent.putExtra("max", Integer.parseInt(max.getText().toString()));
                    intent.putExtra("objective", objective.getText().toString());
                    startActivity(intent);
                }
            }
        });


    }
}