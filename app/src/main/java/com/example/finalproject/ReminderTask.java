package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
                if (!TextUtils.isEmpty(notificationDelay.getText().toString())){
                    DB.addReminder(Integer.parseInt(taskID),taskName,objective.getText().toString(),content.getText().toString(),notification,Integer.parseInt(notificationDelay.getText().toString()));
                    Intent intent = new Intent(v.getContext(), ExistingReminderTask.class);
                    intent.putExtra("id", taskID);
                    intent.putExtra("name", taskName);
                    intent.putExtra("type", taskType);
                    intent.putExtra("objective", objective.getText().toString());
                    intent.putExtra("content", content.getText().toString());
                    intent.putExtra("notification", notification);
                    intent.putExtra("notificationDelay", Integer.parseInt(notificationDelay.getText().toString()));

                    CountDownTimer cdt = new CountDownTimer(3000, 1000) {

                        @Override
                        public void onTick(long millisUntilFinished) {
                            //code for regular intervals or nothing
                        }

                        @Override
                        public void onFinish() {
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(ReminderTask.this, "My Notification");
                            builder.setContentTitle("A TASK TIMER IS DONE");
                            builder.setContentText("Reminder for" + taskName);
                            builder.setSmallIcon(R.drawable.ic_launcher_background);
                            builder.setAutoCancel(true);

                            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(ReminderTask.this);
                            managerCompat.notify(1, builder.build());

                        }
                    };

                    cdt.start();


                    startActivity(intent);
                }
                else {
                    Toast.makeText(ReminderTask.this, "Inproper input", Toast.LENGTH_SHORT).show();
                }
                

            }
        });
    }
}

