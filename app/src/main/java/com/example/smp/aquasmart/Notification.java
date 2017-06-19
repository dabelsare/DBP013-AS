package com.example.smp.aquasmart;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.Calendar;

public class Notification extends AppCompatActivity {
    int hour,minute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        Calendar cal = Calendar.getInstance(); // Rereive calender date
//        hour = cal.get(Calendar.HOUR_OF_DAY); // Take hour from the calender
//        minute = cal.get(Calendar.MINUTE);    // Take minute from the calender

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
    }
}