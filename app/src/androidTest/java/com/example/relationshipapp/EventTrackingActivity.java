package com.example.relationshipapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EventTrackingActivity extends AppCompatActivity {
    private static final String SHARED_PREFS = "AnniversaryAppPrefs";
    private static final String LAST_DATE_KEY = "lastDate";
    private static final String CURRENT_DAY_KEY = "currentDay";

    private int currentDay;
    private TextView dayText;
    private TextView anniversaryText;
    private TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_event_tracking);
        //Initialize views
        dayText = findViewById(R.id.dayText);
        anniversaryText = findViewById(R.id.anniversaryText);
        timePicker = findViewById(R.id.timePicker);

        // Load the current day value
//        loadDayValue();

        // Display the day text
//        updateDayText();

        dayText.setOnClickListener(v -> {
            incrementDayManually();
        });
    }

    /**
     * Load the current day value from SharedPreferences.
     */
    private void loadDayValue() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        // Retrieve saved data
        String lastSavedDate = sharedPreferences.getString(LAST_DATE_KEY, getCurrentDate());
        currentDay = sharedPreferences.getInt(CURRENT_DAY_KEY, 1); // Default to Day 1

        // Calculate the difference in days since the last saved date
        int daysPassed = calculateDaysDifference(lastSavedDate, getCurrentDate());
        currentDay += daysPassed;

        // Save the updated date and day back to SharedPreferences
        saveDayValue(getCurrentDate(), currentDay);
    }


    /**
     * Save the current day and date to SharedPreferences.
     */
    private void saveDayValue(String date, int day) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(LAST_DATE_KEY, date);
        editor.putInt(CURRENT_DAY_KEY, day);
        editor.apply();
    }

    /**
     * Update the dayText with the current day value.
     */
    private void updateDayText() {
        dayText.setText("Day " + currentDay);
    }


    /**
     * Increment the day manually (on button click).
     */
    private void incrementDayManually() {
        currentDay++;
        saveDayValue(getCurrentDate(), currentDay);
        updateDayText();
    }

    /**
     * Calculate the number of days between two dates.
     *
     * @param startDate The earlier date in "yyyy-MM-dd" format.
     * @param endDate   The later date in "yyyy-MM-dd" format.
     * @return The difference in days.
     */
    private int calculateDaysDifference(String startDate, String endDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(dateFormat.parse(startDate));

            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(dateFormat.parse(endDate));

            long differenceInMillis = endCalendar.getTimeInMillis() - startCalendar.getTimeInMillis();
            return (int) (differenceInMillis / (1000 * 60 * 60 * 24)); // Convert milliseconds to days
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }




    //Get the current date in "yyyy-MM-dd" format.
//
//     return The current date as a string.
//
    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(Calendar.getInstance().getTime());
    }

}

