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
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArchiveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArchiveFragment extends Fragment {

    private TextView monthYearText, prev, next;
    private RecyclerView calRecycler;
    private LocalDate selectedDate, currDate;
    private CalAdapter calendarAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ReadDatabaseHelper helper;

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
        prev = v.findViewById(R.id.prevMonth);
        next = v.findViewById(R.id.nextMonth);
        currDate = LocalDate.now();
        selectedDate = LocalDate.now();
        helper = new ReadDatabaseHelper(getActivity(), LocalDate.now().format(DateTimeFormatter.ofPattern("MMM d, yyyy")));

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousMonthAction(v);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextMonthAction(v);
            }
        });


        setMonthView();
        return v;

    }

    private void setMonthView(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        String s = selectedDate.format(formatter);
        monthYearText.setText(s);
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(selectedDate);
        calendarAdapter = new CalAdapter(daysInMonth, selectedDate);
        layoutManager = new GridLayoutManager(getContext(), 7);
        calRecycler.setLayoutManager(layoutManager);
        calRecycler.setAdapter(calendarAdapter);
        Log.i("date", String.valueOf(selectedDate));
        LocalDate x = helper.getOldestDate();
        prev.setEnabled((x.isBefore(selectedDate))); //&& (x.getYear() < selectedDate.getYear() && x.getMonthValue() < selectedDate.getMonthValue())));
        next.setEnabled((LocalDate.now().isAfter(selectedDate))); //&& (LocalDate.now().getYear() > selectedDate.getYear() && LocalDate.now().getMonthValue() > selectedDate.getMonthValue())));

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