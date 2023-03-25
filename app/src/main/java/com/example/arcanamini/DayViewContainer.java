package com.example.arcanamini;

import static com.kizitonwose.calendar.core.ExtensionsKt.firstDayOfWeekFromLocale;

import android.content.Context;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import java.time.DayOfWeek;
import java.time.YearMonth;

public class DayViewContainer {
    CalendarView dayBinder;
    public DayViewContainer(View v){
        v.findViewById(R.id.calendarDayText);
        YearMonth currentMonth = YearMonth.now();
        YearMonth startMonth = currentMonth.minusMonths(100);  // Adjust as needed
        YearMonth endMonth = currentMonth.plusMonths(100);  // Adjust as needed
        DayOfWeek firstDayOfWeek = firstDayOfWeekFromLocale(); // Available from the library
        calendarView.setup(startMonth, endMonth, firstDayOfWeek);
        calendarView.scrollToMonth(currentMonth);

    }


}
