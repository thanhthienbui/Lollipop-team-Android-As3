package com.example.relationshipapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "location_reviews.db";
    private static final int DATABASE_VERSION = 1;

    // Table and column names
    private static final String TABLE_REVIEWS = "reviews";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_LOCATION_NAME = "location_name";
    private static final String COLUMN_RATING = "rating";
    private static final String COLUMN_REVIEW = "review";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_REVIEWS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LOCATION_NAME + " TEXT, " +
                COLUMN_RATING + " FLOAT, " +
                COLUMN_REVIEW + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REVIEWS);
        onCreate(db);
    }

    // Insert a review
    public void insertReview(String locationName, float rating, String review) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_LOCATION_NAME, locationName);
        values.put(COLUMN_RATING, rating);
        values.put(COLUMN_REVIEW, review);

        db.insert(TABLE_REVIEWS, null, values);
        db.close();
    }

    // Get the review for a location
    public Cursor getReview(String locationName) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_REVIEWS, null, COLUMN_LOCATION_NAME + "=?",
                new String[]{locationName}, null, null, null);
    }
}
