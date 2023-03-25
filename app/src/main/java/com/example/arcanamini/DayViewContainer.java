package com.example.arcanamini;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.kizitonwose.calendar.core.CalendarDay;
import com.kizitonwose.calendar.view.CalendarView;
import com.kizitonwose.calendar.view.MonthDayBinder;
import com.kizitonwose.calendar.view.ViewContainer;

public class DayViewContainer extends ViewContainer {
    public TextView calendarDayText;
    public DayViewContainer(View v){
        super(v);
        calendarDayText = v.findViewById(R.id.calendarDayText);


    }
}