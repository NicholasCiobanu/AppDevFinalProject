package com.example.finalproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ExistingProgressTask extends AppCompatActivity {
    String taskName;
    String taskType;
    int taskID;
    int min;
    int max;
    int objective;
    int progress;
    DBHelper DB;
    int newprogress = progress;
    ProgressBar progressBar;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_progress_task);
        DB = new DBHelper(this.getApplicationContext());

        Intent intent = getIntent();
        taskName = intent.getStringExtra("name");
        taskType = intent.getStringExtra("type");
        taskID = Integer.parseInt(intent.getStringExtra("id"));
        min = Integer.parseInt(intent.getStringExtra("min"));
        max = Integer.parseInt(intent.getStringExtra("max"));
        objective = Integer.parseInt(intent.getStringExtra("objective"));
        progress = Integer.parseInt(intent.getStringExtra("progress"));
        TextView taskNameView = findViewById(R.id.taskName);
        taskNameView.setText(taskName);
        TextView minView = findViewById(R.id.minimum);
        minView.setText(min + "");
        TextView maxView = findViewById(R.id.maximum);
        maxView.setText(max + "");
        TextView objectiveView = findViewById(R.id.objective);
        objectiveView.setText(objective + "");
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMin(min);
        progressBar.setMax(max);
        progressBar.setProgress(progress);
        Button button = (Button) findViewById(R.id.alltasks);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton b2 = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (progress >= max){
                    Toast.makeText(ExistingProgressTask.this, "TASK ACCOMPLISHED", Toast.LENGTH_SHORT).show();
                } else{
                    DB.updateProgress(taskID,progress + objective);
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }

            }
        });
        Button done = (Button) findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper DB = new DBHelper(view.getContext());
                DB.deleteTask(taskID);
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                view.getContext().startActivity(intent);
            }
        });
    }
}