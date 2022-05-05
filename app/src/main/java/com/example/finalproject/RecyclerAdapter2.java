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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class RecyclerAdapter2 extends RecyclerView.Adapter<RecyclerAdapter2.ViewHolder> {
    Context context;
    String data;
    int id;
    String[] item;
    String[] names;
    String[] details;
    public RecyclerAdapter2(Context context, String data, int id) {
        this.context = context;
        this.data = data;
        this.id = id;
        item = data.split("\n");
        names = new String[item.length];
        details = new String[item.length];
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

        if (item.length > 0){
            String[] temp;
        for (int i = 0 ; i < item.length ; i++){

            temp = item[i].split(",");
            for (int j = 0 ; j < temp.length ; j++){
                names[i] = temp[0];
                details[i] = temp[1];
            }

        }

        holder.name.setText(names[position]);
        holder.details.setText(details[position]);

        holder.button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(item.length == 1) {

                    DBHelper DB = new DBHelper(context);
                    DB.deleteTask(id);
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }

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
                String content = "";
                for (int i = 0 ; i < finalDetails.length ; i++){
                    if (i == finalDetails.length - 1){
                        content += finalNames[i] + "," +finalDetails[i];
                    }else{
                        content += finalNames[i] + "," +finalDetails[i] + "\n";
                    }

                }
                DB.updateList(id, content);
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                context.startActivity(intent);
            }
        });

        } else
         {
            Toast.makeText(context, "list is empty", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public int getItemCount() {
        return item.length;
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
