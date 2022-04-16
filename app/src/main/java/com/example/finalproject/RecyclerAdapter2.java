package com.example.finalproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class RecyclerAdapter2 extends RecyclerView.Adapter<RecyclerAdapter2.ViewHolder> {
    Context context;
    String data;
    int id;
    public RecyclerAdapter2(Context context, String data, int id) {
        this.context = context;
        this.data = data;
        this.id = id;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,@SuppressLint("RecyclerView") int position) {
        String[] section = data.split("\n");
        if (data.length() > 1){


        String[] names = section[0].split(",");
        String[] details = section[1].split(",");
        holder.name.setText(names[position]);
        holder.details.setText(details[position]);

        holder.button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DBHelper DB = new DBHelper(v.getContext());


                String[] finalNames = new String[names.length - 1];
                for (int i = 0, k = 0; i < names.length; i++) {

                    // if the index is
                    // the removal element index
                    if (i == position) {
                        continue;
                    }

                    // if the index is not
                    // the removal element index
                    finalNames[k++] = names[i];
                }
                String[] finalDetails = new String[details.length - 1];
                for (int i = 0, k = 0; i < details.length; i++) {

                    // if the index is
                    // the removal element index
                    if (i == position) {
                        continue;
                    }

                    // if the index is not
                    // the removal element index
                    finalDetails[k++] = details[i];
                }
                String content = Arrays.toString(finalNames).replace("[", "").replace("]", "") + "\n" + Arrays.toString(finalDetails).replace("[", "").replace("]", "");
                DB.updateList(id, content);
                //remove name and details and redo string and update db
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                context.startActivity(intent);
            }
        });
        }else {
            DBHelper DB = new DBHelper(context);
            DB.deleteTask(id);
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        }
    }


    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView details;
        Button button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            details = itemView.findViewById(R.id.details);
            button = itemView.findViewById(R.id.delete);
        }
    }
}