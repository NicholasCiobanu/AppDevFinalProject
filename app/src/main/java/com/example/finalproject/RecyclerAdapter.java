package com.example.finalproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    Context context;
    ArrayList<String[]> data;

    public RecyclerAdapter(Context context, ArrayList<String[]> data) {
        this.context = context;
        this.data = data;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.task_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.textView.setText(data.get(position)[0] +"  "+ data.get(position)[2] +"  "+ data.get(position)[1]);
        holder.button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               DBHelper DB = new DBHelper(v.getContext());
               DB.deleteTask(Integer.parseInt(data.get(position)[0]));
               Intent intent = new Intent(v.getContext(), MainActivity.class);
               context.startActivity(intent);
            }
        });

        holder.textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DBHelper DB.get typt
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

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        Button button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textNames);
            button = itemView.findViewById(R.id.delete);
        }
    }
}
