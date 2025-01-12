package com.example.relationshipapp;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {

    private DatabaseManager dbManager;
    private Cursor cursor;
    private final String[] from = new String[] {
            DatabaseHelper.ID,
            DatabaseHelper.DATE,
            DatabaseHelper.DISPLAY_TYPE,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbManager = new DatabaseManager(this);
        //showCurrencyList();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}