package com.example.relationshipapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "location_reviews.db";
    private static final int DATABASE_VERSION = 2;

    // Table and column names for locations
    public static final String TABLE_LOCATIONS = "locations";
    public static final String COLUMN_LOCATION_ID = "id";
    public static final String COLUMN_LOCATION_NAME = "location_name";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_OPEN_HOURS = "open_hours";
    public static final String COLUMN_CLOSE_HOURS = "close_hours";
    public static final String COLUMN_PRICE_RANGE = "price_range";
    public static final String COLUMN_IS_POPULAR = "is_popular";

    // Table and column names for reviews
    private static final String TABLE_REVIEWS = "reviews";
    private static final String COLUMN_REVIEW_ID = "review_id";
    private static final String COLUMN_REVIEW_LOCATION_ID = "location_id";
    private static final String COLUMN_RATING = "rating";
    private static final String COLUMN_REVIEW = "review";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create locations table with auto-generated primary key for locationId
        String CREATE_LOCATIONS_TABLE = "CREATE TABLE " + TABLE_LOCATIONS + " (" +
                COLUMN_LOCATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LOCATION_NAME + " TEXT, " +
                COLUMN_LATITUDE + " REAL, " +
                COLUMN_LONGITUDE + " REAL, " +
                COLUMN_TYPE + " TEXT, " +
                COLUMN_OPEN_HOURS + " TEXT, " +
                COLUMN_CLOSE_HOURS + " TEXT, " +
                COLUMN_PRICE_RANGE + " TEXT, " +
                COLUMN_IS_POPULAR + " INTEGER)";
        db.execSQL(CREATE_LOCATIONS_TABLE);

        // Create reviews table
        String CREATE_REVIEWS_TABLE = "CREATE TABLE " + TABLE_REVIEWS + " (" +
                COLUMN_REVIEW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_REVIEW_LOCATION_ID + " INTEGER, " +
                COLUMN_RATING + " FLOAT, " +
                COLUMN_REVIEW + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_REVIEW_LOCATION_ID + ") REFERENCES " + TABLE_LOCATIONS + "(" + COLUMN_LOCATION_ID + "))";
        db.execSQL(CREATE_REVIEWS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REVIEWS);
        onCreate(db);
    }

    // Insert a location into the locations table (no need to pass locationId)
    public void insertLocation(String name, double latitude, double longitude, String type,
                               String openHours, String closeHours, String priceRange, boolean isPopular) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_LOCATION_NAME, name);
        values.put(COLUMN_LATITUDE, latitude);
        values.put(COLUMN_LONGITUDE, longitude);
        values.put(COLUMN_TYPE, type);
        values.put(COLUMN_OPEN_HOURS, openHours);
        values.put(COLUMN_CLOSE_HOURS, closeHours);
        values.put(COLUMN_PRICE_RANGE, priceRange);
        values.put(COLUMN_IS_POPULAR, isPopular ? 1 : 0);

        // Insert the new location, locationId will be auto-generated
        db.insert(TABLE_LOCATIONS, null, values);
        db.close();
    }

    // Insert a review into the reviews table
    // Modify this method to accept locationName instead of locationId
    public void insertReview(String locationName, float rating, String review) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_REVIEW_LOCATION_ID, locationName);  // Save locationName instead of ID
        values.put(COLUMN_RATING, rating);
        values.put(COLUMN_REVIEW, review);

        db.insert(TABLE_REVIEWS, null, values);
        db.close();
    }

    // Get all locations from the locations table
    public Cursor getAllLocations() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_LOCATIONS, null);
    }

    // Get reviews for a specific location
    public Cursor getReviewsForLocation(int locationId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_REVIEWS, null, COLUMN_REVIEW_LOCATION_ID + "=?",
                new String[]{String.valueOf(locationId)}, null, null, null);
    }
}
