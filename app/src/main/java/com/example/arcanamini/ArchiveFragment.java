package com.example.arcanamini;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArchiveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArchiveFragment extends Fragment {

    private TextView monthYearText;
    private RecyclerView calRecycler;
    private LocalDate selectedDate, currDate;
    private CalAdapter calendarAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public ArchiveFragment() {
        // Required empty public constructor
    }

    public static ArchiveFragment newInstance() {
        ArchiveFragment fragment = new ArchiveFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_archive, container, false);
        monthYearText = v.findViewById(R.id.archive_month);
        calRecycler = v.findViewById(R.id.archive_calendar);
        currDate = LocalDate.now();
        selectedDate = LocalDate.now();
        setMonthView();
        return v;

    }

    private void setMonthView(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        String s = selectedDate.format(formatter);
        monthYearText.setText(s);
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(selectedDate);
        calendarAdapter = new CalAdapter(daysInMonth, LocalDate.now());
        layoutManager = new GridLayoutManager(getContext(), 7);
        calRecycler.setLayoutManager(layoutManager);
        calRecycler.setAdapter(calendarAdapter);
        Log.i("date", String.valueOf(selectedDate));
    }
    private ArrayList<LocalDate> daysInMonthArray(LocalDate date){
        ArrayList<LocalDate> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);
        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstOfMonth = date.withDayOfMonth(1);
        LocalDate current = sundayForDate(firstOfMonth);
        LocalDate endDate = current.plusWeeks(6);

        while (current.isBefore(endDate))
        {
            daysInMonthArray.add(current);
            current = current.plusDays(1);
        }
//        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();
//        for (int i = 0; i < 42; i++){
//            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek){
//                daysInMonthArray.add(LocalDate.);
//            } else {
//                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
//            }
//        }
        return daysInMonthArray;
    }
    private static LocalDate sundayForDate(LocalDate current)
    {
        LocalDate oneWeekAgo = current.minusWeeks(1);

        while (current.isAfter(oneWeekAgo))
        {
            if(current.getDayOfWeek() == DayOfWeek.SUNDAY)
                return current;

            current = current.minusDays(1);
        }

        return null;
    }

    public void previousMonthAction(View view)
    {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view)
    {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }
}