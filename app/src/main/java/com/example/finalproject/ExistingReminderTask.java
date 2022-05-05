package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
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
    String notification;
    String notificationDelay;
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
        notificationDelay = intent.getStringExtra("notificationDelay");
        notification = intent.getStringExtra("notification");

        TextView taskNameView = findViewById(R.id.taskName);
        taskNameView.setText(taskName);
        TextView objectiveView = findViewById(R.id.reminderObjective);
        objectiveView.setText(objective);
        TextView contentView = findViewById(R.id.reminderText);
        contentView.setText(content);
        CheckBox notificationView = (CheckBox) findViewById(R.id.reminderCheckBox);
        if (Integer.parseInt(notification) == 1){
            notificationView.setChecked(true);
        }
        notificationView.setEnabled(false);
        TextView notificationDelayView = findViewById(R.id.reminderDelay);
        notificationDelayView.setText("You set the reminder to:" + notificationDelay + "minutes");

        Button button = (Button) findViewById(R.id.alltasks);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        Button done = (Button) findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper DB = new DBHelper(view.getContext());
                DB.deleteTask(Integer.parseInt(taskID));
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                view.getContext().startActivity(intent);
            }
        });
    }
}