package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class ExistingProgressTask extends AppCompatActivity {
    String taskName;
    String taskType;
    String taskID;
    int min;
    int max;
    String objective;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_progress_task);


        Intent intent = getIntent();
        taskName = intent.getStringExtra("name");
        taskType = intent.getStringExtra("type");
        taskID = intent.getStringExtra("id");
        min = Integer.parseInt(intent.getStringExtra("min"));
        max = Integer.parseInt(intent.getStringExtra("max"));
        objective = intent.getStringExtra("objective");

        TextView taskNameView = findViewById(R.id.taskName);
        taskNameView.setText(taskName);
        TextView minView = findViewById(R.id.minimum);
        minView.setText(min + "");
        TextView maxView = findViewById(R.id.maximum);
        maxView.setText(max + "");
        TextView objectiveView = findViewById(R.id.objective);
        objectiveView.setText(objective);


        Button button = (Button) findViewById(R.id.alltasks);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}