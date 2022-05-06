package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    //public static final String name = "com.example.myfirstapp.MESSAGE";
    Context context;
    String newTaskType = "";
    String taskName = "";
    DBHelper DB;
    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    private GoogleMap mMap;
    ArrayList<String[]> allTasks = new ArrayList<>();
    ArrayList<String[]> allMarkers = new ArrayList<>();
    int mMarkerCount = 0;
    Marker mMarker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

       // this.context = context;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DB = new DBHelper(this);
        Cursor res = DB.getTasks();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);
        if(res.getCount() == 0) {
            Toast.makeText(MainActivity.this, "You Have No Tasks", Toast.LENGTH_SHORT).show();
        }

        while(res.moveToNext()) {
            allTasks.add(new String[]{ res.getString(0), res.getString(1), res.getString(2)});
            allMarkers.add(new String[]{res.getString(2), res.getString(3), res.getString(4)});
        }

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapter(this, allTasks);
        recyclerView.setAdapter(adapter);



        FloatingActionButton addTaskButton = (FloatingActionButton ) findViewById(R.id.addTask);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String newActivityType = "";
                EditText name = findViewById(R.id.newTaskName);
                taskName = name.getText().toString();
                if (!taskName.equals("")) {
                    Intent intent = null;
                    switch (newTaskType) {
                        case "list":
                            intent = new Intent(v.getContext(), ListTask.class);
                            createNewTask(intent, "list");
                            break;
                        case "progress":
                            intent = new Intent(v.getContext(),  ProgressTask.class);
                            createNewTask(intent, "progress");
                            break;
                        case "reminder":
                            intent = new Intent(v.getContext(), ReminderTask.class);
                            createNewTask(intent, "reminder");
                            break;
                        default:
                            Toast.makeText(v.getContext(), "No list type selected", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
                else Toast.makeText(v.getContext(), "Task name cannot be empty", Toast.LENGTH_LONG).show();
            }
        });


    }
    @Override
    public void onBackPressed() {

    }
    private void createNewTask(Intent intent, String type){

    DB.addTask(type, taskName, mMarker.getPosition().latitude, mMarker.getPosition().longitude);
    Cursor res = DB.getLastTask();
    res.moveToNext();
    intent.putExtra("id", res.getString(0));
    intent.putExtra("name", taskName);
    intent.putExtra("type", type);

    startActivity(intent);

    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.listTask:
                if (checked)
                    newTaskType = "list";
                    break;
            case R.id.progressTask:
                if (checked)
                    newTaskType = "progress";
                    break;
            case R.id.reminderTask:
                if (checked)
                    newTaskType = "reminder";
                    break;

        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        Random random = new Random();
        for (String[] marker : allMarkers){
            mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(marker[1]), Double.parseDouble(marker[1]))).title("Location for " + marker[0]));

        }
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                try {
                    Toast.makeText(getApplicationContext(), marker.getTitle(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){

                }
                return false;
            }
        });
/*
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                // Clears the previously touched position
                googleMap.clear();

                // Animating to the touched position
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                googleMap.addMarker(markerOptions);
            }
        });*/


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latlng) {
                if(mMarkerCount > 0){
                    if(mMarker != null){
                        mMarker.remove();
                    }
                }

                mMarker = mMap.addMarker(new MarkerOptions().position(latlng));
                mMarkerCount++;
            }
        });
    }

    }


