package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ExistingListTask extends AppCompatActivity {
    Context context;
    String taskName;
    String taskType;
    String taskID;
    RecyclerView recyclerView;
    RecyclerAdapter2 adapter;
    String content;
    Button add;
    String[] names;
    String[] aisles;
    EditText newItem;
    EditText newDetails;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_list_task);
        DB = new DBHelper(getApplicationContext());
        Intent intent = getIntent();
        taskName = intent.getStringExtra("name");
        taskType = intent.getStringExtra("type");
        taskID = intent.getStringExtra("id");
        content = intent.getStringExtra("content");
        newItem = (EditText) findViewById(R.id.inputItemName);
        newDetails = (EditText) findViewById(R.id.inputItemDetails);
        TextView taskNameView = findViewById(R.id.taskName);
        taskNameView.setText(taskName);
        Button add = (Button) findViewById(R.id.add);
        registerForContextMenu(add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToList(newItem.getText().toString(),newDetails.getText().toString());
            }
        });
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

        Button done = (Button) findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper DB = new DBHelper(view.getContext());
                DB.deleteTask(Integer.parseInt(taskID));
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                context.startActivity(intent);
            }
        });

        names = new String[10];
        aisles = new String[10];
        try {
            DownloadTask task = new DownloadTask();

            task.execute("https://api.spoonacular.com/food/ingredients/search?query=cookie&metaInformation=true&number=10&sort=calories&sortDirection=desc&apiKey=2b9f12ec169b491b9f58b06494c24237");

            InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "There was an error", Toast.LENGTH_SHORT).show();
        }



    }


    private void addToList(String name, String description){

        String addedContent = "\n" + name + "," + description;
        DB.updateList(Integer.parseInt(taskID), content + addedContent);
        startActivity(new Intent(getApplicationContext(),MainActivity.class));


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Choose your option");
        getMenuInflater().inflate(R.menu.groceries_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option_1:
                addToList(names[0],aisles[0]);
                return true;
            case R.id.option_2:
                addToList(names[1],aisles[1]);
                return true;
            case R.id.option_3:
                addToList(names[2],aisles[2]);
                return true;
            case R.id.option_4:
                addToList(names[3],aisles[3]);
                return true;
            case R.id.option_5:
                addToList(names[4],aisles[4]);
                return true;
            case R.id.option_6:
                addToList(names[5],aisles[5]);
                return true;
            case R.id.option_7:
                addToList(names[6],aisles[6]);
                return true;
            case R.id.option_8:
                addToList(names[7],aisles[7]);
                return true;
            case R.id.option_9:
                addToList(names[8],aisles[8]);
                return true;
            case R.id.option_10:
                addToList(names[9],aisles[9]);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpsURLConnection urlConnection = null;

            try {

                url = new URL(urls[0]);
                urlConnection = (HttpsURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

                return result;

            } catch (Exception e) {
                e.printStackTrace();
                //Toast.makeText(getApplicationContext(), "There was an error", Toast.LENGTH_SHORT).show();

                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //resultTextView.setText(s);
            Log.i("JSON", s);

            try {
                //I can convert the data to a json object
                 /*
                JSONArray ja = new JSONArray(s);
                JSONObject jsonObject = ja.getJSONObject(0);
                String test = jsonObject.toString();
                Log.i("JSONFIRST", test);
                //I can get specific data from the object
                String id = jsonObject.getString("id");
                // then I would just loop through every object and send it to the Recycler adapter
                 */
                JSONObject jsonObject = new JSONObject(s);
                String results = jsonObject.getString("results");
                Log.i("JSONFIRST", results);
                JSONArray arr = new JSONArray(results);
                for (int i = 0; i < 10; i++) {
                    JSONObject jsonPart = arr.getJSONObject(i);
                    names[i] = jsonPart.getString("name");
                    aisles[i] = jsonPart.getString("aisle");

                }
            } catch (Exception e) {
                e.printStackTrace();
                //Toast.makeText(getApplicationContext(), "There was an error", Toast.LENGTH_SHORT).show();

            }

        }
    }
}