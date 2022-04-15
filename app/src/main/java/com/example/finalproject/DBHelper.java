package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "Userdata.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("PRAGMA foreign_keys=ON;");
        DB.execSQL("create Table allTasks(taskID INTEGER primary key, type TEXT, name TEXT)");
        //DB.execSQL("create Table listTasks(taskID INTEGER primary key, contact TEXT, dob TEXT,FOREIGN KEY(taskID) REFERENCES allTasks(taskID))");
        DB.execSQL("create Table reminderTasks(taskID INTEGER primary key, name TEXT, objective TEXT,content TEXT,notification INTEGER,notificationDelay INTEGER," +
                "FOREIGN KEY(taskID) REFERENCES allTasks(taskID) ON DELETE CASCADE)");
        //DB.execSQL("create Table progressTasks(taskID INTEGER primary key, contact TEXT, dob TEXT,FOREIGN KEY(taskID) REFERENCES allTasks(taskID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists allTasks");
        DB.execSQL("drop Table if exists reminderTasks");
    }

    public Boolean addTask(String type, String name){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("type", type);
        contentValues.put("name", name);

        long result = DB.insert("allTasks", null, contentValues);

        if(result == -1)
            return false;
        else
            return true;
    }

    public Boolean deleteTask(Integer taskID){

        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        Cursor cursor = DB.rawQuery("Select * from allTasks where taskID = ?", new String[] { taskID + "" } );

        if(cursor.getCount() > 0) {
            long result = DB.delete("allTasks", "taskID=?", new String[] { taskID + "" } );

            if(result == -1)
                return false;
            else
                return true;

        } else {
            return false;
        }
    }

    public Cursor getTasks() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from allTasks", null);

        return cursor;
    }
    public Cursor getTask(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from allTasks where name = ?", new String[] { name + "" } );

        return cursor;
    }


    public Boolean addReminder(int taskID, String name, String objective, String content, int notification, int notificationDelay){

        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("taskID", taskID);
        contentValues.put("name", name);
        contentValues.put("objective", objective);
        contentValues.put("content", content);
        contentValues.put("notification", notification);
        contentValues.put("notificationDelay", notificationDelay);

        long result = DB.insert("reminderTasks", null, contentValues);

        if(result == -1)
            return false;
        else
            return true;
    }

    public Boolean updateReminder(int taskID,String name, String objective, String content, int notification, int notificationDelay){

        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("taskID", taskID);
        contentValues.put("name", name);
        contentValues.put("objective", objective);
        contentValues.put("content", content);
        contentValues.put("notification", notification);
        contentValues.put("notificationDelay", notificationDelay);

        Cursor cursor = DB.rawQuery("Select * from reminderTasks where taskID = ?", new String[] { taskID + "" } );

        if(cursor.getCount() > 0) {
            long result = DB.update("reminderTasks", contentValues, "taskID=?", new String[] { taskID + "" } );

            if(result == -1)
                return false;
            else
                return true;

        } else {
            return false;
        }

    }

    public Boolean deleteReminder(Integer taskID){

        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        Cursor cursor = DB.rawQuery("Select * from reminderTasks where taskID = ?", new String[] { taskID + "" } );

        if(cursor.getCount() > 0) {
            long result = DB.delete("reminderTasks", "taskID=?", new String[] { taskID + "" } );

            if(result == -1)
                return false;
            else
                return true;

        } else {
            return false;
        }
    }

    public Cursor getReminder(Integer taskID){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from reminderTasks where taskID = ?", new String[] { taskID + "" } );

        return cursor;
    }

    public Cursor getdata() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails", null);

        return cursor;
    }
}
