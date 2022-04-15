package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ExistingListTask extends AppCompatActivity {
    Context context;
    String taskName;
    String taskType;
    String taskID;
    RecyclerView recyclerView;
    RecyclerAdapter2 adapter;
    String content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_list_task);
        Intent intent = getIntent();
        taskName = intent.getStringExtra("name");
        taskType = intent.getStringExtra("type");
        taskID = intent.getStringExtra("id");
        content = intent.getStringExtra("content");
        TextView taskNameView = findViewById(R.id.taskName);
        taskNameView.setText(taskName);
        Button button = (Button) findViewById(R.id.alltasks);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapter2(this, content, Integer.parseInt(taskID));
        recyclerView.setAdapter(adapter);

    }
}