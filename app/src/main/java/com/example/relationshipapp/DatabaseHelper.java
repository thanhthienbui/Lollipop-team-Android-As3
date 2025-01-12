package com.example.relationshipapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "relationshipDate.DB";
    private static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "date";
    public static final String ID = "_id";
    public static final String DATE = "date";
    public static final String DISPLAY_TYPE = "type"; // day month year


    private static final String CREATE_TABLE = "create table " + TABLE_NAME + " (" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + DATE + " TEXT NOT NULL,"
            + DISPLAY_TYPE + " TEXT NOT NULL)";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}