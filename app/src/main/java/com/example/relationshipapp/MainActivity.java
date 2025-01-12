package com.example.relationshipapp;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.temporal.ChronoUnit;
import java.time.LocalDate;

public class MainActivity extends AppCompatActivity {

    LocalDate currentDate = LocalDate.now();

    private TextView relation_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relation_date = findViewById(R.id.displayDate);
        Button button = findViewById(R.id.dateButton);

        DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {

            LocalDate actualDate = LocalDate.of(year, month, day);
            long days = actualDate.until(currentDate, ChronoUnit.DAYS);
            updateLabel(days);
        };

        button.setOnClickListener(view ->
                new DatePickerDialog(MainActivity.this,
                        date, currentDate.getYear(),
                        currentDate.getMonthValue(),
                        currentDate.getDayOfMonth()).show()
        );

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    private void updateLabel(long days){

        relation_date.setText(getString(R.string.days, (days)));
    }

}