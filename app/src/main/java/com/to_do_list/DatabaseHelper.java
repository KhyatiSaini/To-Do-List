package com.to_do_list;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_Name = "task_Database";
    private static final String Table_Name = "Task_List";
    private static final String Column_Name = "Task";
    private static final int version = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_Name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+ Table_Name +"(ID INTEGER PRIMARY KEY AUTOINCREMENT,"+ Column_Name +" TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String query = "DROP TABLE IF EXISTS "+ Table_Name +";";
        db.execSQL(query);
        onCreate(db);
    }

    public void insert_new_task(String task)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(Column_Name,task);
        db.insertWithOnConflict(Table_Name,null, contentvalues, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void delete_task(String task){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Table_Name,Column_Name + "= ?",new String[]{task});
        db.close();
    }

    public ArrayList<String> getTaskList(){
        ArrayList<String> list_Of_task = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Table_Name,new String[]{Column_Name},null,null,null,null,null);
        while(cursor.moveToNext()){
            int index = cursor.getColumnIndex(Column_Name);
            list_Of_task.add(cursor.getString(index));
        }
        cursor.close();
        db.close();
        return list_Of_task;
    }

}
