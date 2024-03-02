package com.example.locmes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.locmes.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddToCalendar extends AppCompatActivity {

    CalendarView calendarView;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_calendar);

        calendarView = findViewById(R.id.calendarView);
        calendar = Calendar.getInstance();

        calendarView.setOnDateChangeListener((calendarView, year, month, day) -> Toast.makeText(AddToCalendar.this, day + "/" + (month + 1) + "/" + year, Toast.LENGTH_SHORT).show());

        setDate(2, 3, 2024);
        getDate();

        ImageView imageBack = findViewById(R.id.imageBack);
        imageBack.setOnClickListener(view -> onBackPressed());
    }

    public void setDate(int day, int month, int year) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        long milli = calendar.getTimeInMillis();
        calendarView.setDate(milli);
    }

    public void getDate() {
        long date = calendarView.getDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
        calendar.setTimeInMillis(date);
        String selected_date = simpleDateFormat.format(calendar.getTime());
        Toast.makeText(this, selected_date, Toast.LENGTH_SHORT).show();
    }


}