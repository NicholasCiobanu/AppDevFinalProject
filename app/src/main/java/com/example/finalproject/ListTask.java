package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ListTask extends AppCompatActivity {
    Context context;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
      //  this.context = context;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_task);
        Intent intent = getIntent();
        String taskName = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = findViewById(R.id.taskName);
        textView.setText(taskName);
        Button button = (Button) findViewById(R.id.alltasks);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

}