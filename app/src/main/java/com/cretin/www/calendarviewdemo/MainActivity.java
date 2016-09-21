package com.cretin.www.calendarviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cretin.www.calendarviewdemo.views.CalendarView;

public class MainActivity extends AppCompatActivity {

    private CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarView = (CalendarView) findViewById(R.id.calendar);

        //设置指定的日期
        String[] resStr = new String[]{"2016 9 23", "2016 9 20", "2016 9 18"};
        calendarView.setmPaintDays(resStr);
    }
}
