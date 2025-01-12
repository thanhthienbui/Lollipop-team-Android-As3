package com.example.relationshipapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class NotesDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;

    // Table name and columns
    public static final String TABLE_NOTES = "notes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_GIFT_TYPE = "gift_type";
    public static final String COLUMN_NOTE = "note";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    // Create table query
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NOTES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_GIFT_TYPE + " INTEGER NOT NULL, " +
                    COLUMN_NOTE + " TEXT NOT NULL, " +
                    COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP)";

    public NotesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }

    // Add a new note
    public long addNote(int giftType, String note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_GIFT_TYPE, giftType);
        values.put(COLUMN_NOTE, note);
        return db.insert(TABLE_NOTES, null, values);
    }

    // Get all notes for a specific gift type
    public List<String> getNotes(int giftType) {
        List<String> notes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        
        String[] projection = {COLUMN_NOTE};
        String selection = COLUMN_GIFT_TYPE + " = ?";
        String[] selectionArgs = {String.valueOf(giftType)};
        String sortOrder = COLUMN_TIMESTAMP + " DESC";

        Cursor cursor = db.query(
                TABLE_NOTES,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        while (cursor.moveToNext()) {
            String note = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTE));
            notes.add(note);
        }
        cursor.close();

        return notes;
    }

    // Delete all notes for a specific gift type
    public void deleteAllNotes(int giftType) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_GIFT_TYPE + " = ?";
        String[] selectionArgs = {String.valueOf(giftType)};
        db.delete(TABLE_NOTES, selection, selectionArgs);
    }
} 