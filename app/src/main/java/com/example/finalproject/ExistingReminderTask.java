package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class ExistingReminderTask extends AppCompatActivity {
    String taskName;
    String taskType;
    String taskID;
    String objective;
    String content;
    int notification;
    int notificationDelay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_reminder_task);

        Intent intent = getIntent();
        taskName = intent.getStringExtra("name");
        taskType = intent.getStringExtra("type");
        taskID = intent.getStringExtra("id");
        objective = intent.getStringExtra("objective");
        content = intent.getStringExtra("content");
        notification = Integer.parseInt(intent.getStringExtra("notification"));
        notificationDelay = Integer.parseInt(intent.getStringExtra("notificationDelay"));
        TextView taskNameView = findViewById(R.id.taskName);
        taskNameView.setText(taskName);
        TextView objectiveView = findViewById(R.id.reminderObjective);
        objectiveView.setText(objective);
        TextView contentView = findViewById(R.id.reminderText);
        contentView.setText(content);
        CheckBox notificationView = (CheckBox) findViewById(R.id.reminderCheckBox);
        if (notification == 1){
            notificationView.setChecked(true);
        }
        TextView notificationDelayView = findViewById(R.id.reminderDelay);
        notificationDelayView.setText(notificationDelay + "");

        Button button = (Button) findViewById(R.id.alltasks);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}