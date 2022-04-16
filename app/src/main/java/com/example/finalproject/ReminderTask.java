package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class ReminderTask extends AppCompatActivity {
    String taskName;
    String taskType;
    String taskID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_task);

        Intent intent = getIntent();
        taskName = intent.getStringExtra("name");
        taskType = intent.getStringExtra("type");
        taskID = intent.getStringExtra("id");
        TextView textView = findViewById(R.id.reminderName);
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
                EditText objective = (EditText) findViewById(R.id.reminderObjective);
                EditText content = (EditText) findViewById(R.id.reminderText);
                CheckBox checkBox = (CheckBox) findViewById(R.id.reminderCheckBox);
                int notification;
                if (checkBox.isChecked()){
                    notification = 1;
                }
                else
                    notification = 0;
                EditText notificationDelay = (EditText) findViewById(R.id.reminderDelay);
                DB.addReminder(Integer.parseInt(taskID),taskName,objective.getText().toString(),content.getText().toString(),notification,Integer.parseInt(notificationDelay.getText().toString()));
                Intent intent = new Intent(v.getContext(), ExistingReminderTask.class);
                intent.putExtra("id", taskID);
                intent.putExtra("name", taskName);
                intent.putExtra("type", taskType);
                intent.putExtra("objective", objective.getText().toString());
                intent.putExtra("content", content.getText().toString());
                intent.putExtra("notification", notification);
                intent.putExtra("notificationDelay", Integer.parseInt(notificationDelay.getText().toString()));
                startActivity(intent);

            }
        });
    }
}