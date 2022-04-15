package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Map;
import java.util.TreeMap;

public class ListTask extends AppCompatActivity {
    Context context;
    String taskName;
    String taskType;
    String taskID;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
      //  this.context = context;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_task);
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

        Button button2 = (Button) findViewById(R.id.addItem);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DBHelper DB = new DBHelper(v.getContext());
                EditText nameView = (EditText) findViewById(R.id.name);
                EditText detailsView = (EditText) findViewById(R.id.details);
                String name = nameView.getText().toString();
                String details = detailsView.getText().toString();
                String content = name + "\n" + details;
                DB.addList(Integer.parseInt(taskID), content);
                Intent intent = new Intent(v.getContext(), ExistingListTask.class);
                intent.putExtra("id", taskID);
                intent.putExtra("name", taskName);
                intent.putExtra("type", taskType);
                intent.putExtra("content", content);
                startActivity(intent);

            }
        });
    }

}