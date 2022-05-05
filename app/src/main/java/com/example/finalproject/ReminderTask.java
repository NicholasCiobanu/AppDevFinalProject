package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
        createNotificationChannel();
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
                EditText notificationDelay = (EditText) findViewById(R.id.reminderDelay);
                int notification;
                if (checkBox.isChecked()){
                    notification = 1;

                    Intent intent2 = new Intent(ReminderTask.this,ReminderBroadCast.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(ReminderTask.this,0,intent2,PendingIntent.FLAG_IMMUTABLE);

                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                    long timeAtButtonClick = System.currentTimeMillis();

                    alarmManager.set(AlarmManager.RTC_WAKEUP,timeAtButtonClick + Integer.parseInt(notificationDelay.getText().toString()) * 60000 ,pendingIntent);

                }
                else {
                    notification = 0;
                }

                if (!TextUtils.isEmpty(notificationDelay.getText().toString())){
                    DB.addReminder(Integer.parseInt(taskID),taskName,objective.getText().toString(),content.getText().toString(),notification,Integer.parseInt(notificationDelay.getText().toString()));
                    Intent intent = new Intent(v.getContext(), ExistingReminderTask.class);
                    intent.putExtra("id", taskID);
                    intent.putExtra("name", taskName);
                    intent.putExtra("type", taskType);
                    intent.putExtra("objective", objective.getText().toString());
                    intent.putExtra("content", content.getText().toString());
                    intent.putExtra("notification", notification + "");
                    intent.putExtra("notificationDelay", notificationDelay.getText().toString());

                    startActivity(intent);
                }
                else {
                    Toast.makeText(ReminderTask.this, "Inproper input", Toast.LENGTH_SHORT).show();

                }
                

            }
        });
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "reminderChannel";
            String description = "channel for the reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notify",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}

