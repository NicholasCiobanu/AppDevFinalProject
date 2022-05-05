package com.example.finalproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
                if (data.get(position)[1].equals("reminder")){
                    DBHelper DB = new DBHelper(view.getContext());
                    Cursor res = DB.getTask(position + 1);
                    res.moveToFirst();
                    Cursor res2 = DB.getReminder(Integer.parseInt(res.getString(0)));
                    res2.moveToFirst();
                    Intent intent = new Intent(view.getContext(), ExistingReminderTask.class);
                    intent.putExtra("id", res2.getString(0));
                    intent.putExtra("name", res2.getString(1));
                    intent.putExtra("type", data.get(position)[1]);
                    intent.putExtra("objective", res2.getString(2));
                    intent.putExtra("content", res2.getString(3));
                    intent.putExtra("notification", res2.getString(4));
                    intent.putExtra("notificationDelay", res2.getString(5));
                    context.startActivity(intent);
                }
                if (data.get(position)[1].equals("progress")){
                    DBHelper DB = new DBHelper(view.getContext());
                    Cursor res = DB.getTask(position + 1);
                    res.moveToNext();
                    Cursor res2 = DB.getProgress(Integer.parseInt(res.getString(0)));
                    res2.moveToFirst();
                    Intent intent = new Intent(view.getContext(), ExistingProgressTask.class);
                    intent.putExtra("id", res2.getString(0));
                    intent.putExtra("name", res2.getString(1));
                    intent.putExtra("type", data.get(position)[1]);
                    intent.putExtra("min", res2.getString(2));
                    intent.putExtra("max", res2.getString(3));
                    intent.putExtra("objective", res2.getString(4));
                    intent.putExtra("progress", res2.getString(5));
                    context.startActivity(intent);
                }
                if (data.get(position)[1].equals("list")){
                    DBHelper DB = new DBHelper(view.getContext());
                    Cursor res = DB.getTask(position + 1);
                    res.moveToFirst();
                    Cursor res2 = DB.getList(Integer.parseInt(res.getString(0)));
                    res2.moveToFirst();
                    Intent intent = new Intent(view.getContext(), ExistingListTask.class);
                    intent.putExtra("id", res2.getString(0));
                    intent.putExtra("content", res2.getString(1));
                    intent.putExtra("name", data.get(position)[2]);
                    context.startActivity(intent);
                }

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
