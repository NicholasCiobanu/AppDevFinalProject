package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "Data.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("PRAGMA foreign_keys=ON;");
        DB.execSQL("create Table allTasks(taskID INTEGER primary key, type TEXT, name TEXT)");
        DB.execSQL("create Table listTasks(taskID INTEGER primary key, content TEXT, FOREIGN KEY(taskID) REFERENCES allTasks(taskID) ON DELETE CASCADE)");
        DB.execSQL("create Table reminderTasks(taskID INTEGER primary key, name TEXT, objective TEXT,content TEXT,notification INTEGER,notificationDelay INTEGER," +
                "FOREIGN KEY(taskID) REFERENCES allTasks(taskID) ON DELETE CASCADE)");
        DB.execSQL("create Table progressTasks(taskID INTEGER primary key, name TEXT, min INTEGER,max INTEGER, objective TEXT," +
                " FOREIGN KEY(taskID) REFERENCES allTasks(taskID) ON DELETE CASCADE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists allTasks");
        DB.execSQL("drop Table if exists reminderTasks");
        DB.execSQL("drop Table if exists progressTasks");
    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
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

    public Boolean addProgress(int taskID, String name, int min, int max, String objective){

        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("taskID", taskID);
        contentValues.put("name", name);
        contentValues.put("min", min);
        contentValues.put("max", max);
        contentValues.put("objective", objective);


        long result = DB.insert("progressTasks", null, contentValues);

        if(result == -1)
            return false;
        else
            return true;
    }
    public Boolean addList(int taskID, String content){

        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("taskID", taskID);
        contentValues.put("content", content);


        long result = DB.insert("listTasks", null, contentValues);

        if(result == -1)
            return false;
        else
            return true;
    }
    /*public Boolean updateReminder(int taskID,String name, String objective, String content, int notification, int notificationDelay){

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

    }*/

    public Boolean updateList(int taskID,String content){

        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("taskID", taskID);
        contentValues.put("content", content);

        Cursor cursor = DB.rawQuery("Select * from listTasks where taskID = ?", new String[] { taskID + "" } );

        if(cursor.getCount() > 0) {
            long result = DB.update("listTasks", contentValues, "taskID=?", new String[] { taskID + "" } );

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
    public Boolean deleteProgress(Integer taskID){

        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        Cursor cursor = DB.rawQuery("Select * from progressTasks where taskID = ?", new String[] { taskID + "" } );

        if(cursor.getCount() > 0) {
            long result = DB.delete("progressTasks", "taskID=?", new String[] { taskID + "" } );

            if(result == -1)
                return false;
            else
                return true;

        } else {
            return false;
        }
    }
    public Boolean deleteList(Integer taskID){

        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        Cursor cursor = DB.rawQuery("Select * from listTasks where taskID = ?", new String[] { taskID + "" } );

        if(cursor.getCount() > 0) {
            long result = DB.delete("listTasks", "taskID=?", new String[] { taskID + "" } );

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
    public Cursor getProgress(Integer taskID){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from progressTasks where taskID = ?", new String[] { taskID + "" } );

        return cursor;
    }
    public Cursor getList(Integer taskID){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from listTasks where taskID = ?", new String[] { taskID + "" } );

        return cursor;
    }

}
