package com.example.relationshipapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {
    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DatabaseManager(Context c) {
        context = c;
    }

    public DatabaseManager open() throws SQLException {
        dbHelper  = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String date, String type) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.DATE, date);
        contentValue.put(DatabaseHelper.DISPLAY_TYPE, type);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public int update(long _id, String date, String type) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.DATE, date);
        contentValue.put(DatabaseHelper.DISPLAY_TYPE, type);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValue, DatabaseHelper.ID + " =" + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.ID + " =" + _id, null);
        // When deleting or adding rows with AUTOINCREMENT, you can use this to reserve the biggest primary key in the table
        //database.delete("SQLITE_SEQUENCE", "NAME = ?", new String[] {DatabaseHelper.TABLE_NAME});
    }

    public Cursor selectAll() {
        String [] columns = new String[] {
                DatabaseHelper.ID,
                DatabaseHelper.DATE,
                DatabaseHelper.DISPLAY_TYPE,
        };

        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
}