package com.example.arcanamini;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CalAdapter extends RecyclerView.Adapter<CalAdapter.CalViewHolder>{
    public ArrayList<String> daysofmonth;
    public ArrayList<LocalDate> daysofmonthLD;

    Context context;
    LocalDate now;

//     public CalAdapter(ArrayList<String> daysofmonth, LocalDate now){
//         super();
//         this.daysofmonth = daysofmonth;
//         this.now = now;
//     }
    public CalAdapter(ArrayList<LocalDate> daysofmonth, LocalDate now){
        super();
        this.daysofmonthLD = daysofmonth;
        this.now = now;
    }

    @Override
    public CalAdapter.CalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.card_cal, parent, false);
//        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
//        layoutParams.height = (int) (parent.getHeight() * 0.16666666);
        CalViewHolder viewHolder = new CalViewHolder(v);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(CalAdapter.CalViewHolder holder, int position) {
//         Log.i("calI", String.valueOf(position));
//        String s = daysofmonth.get(position);
        LocalDate d = daysofmonthLD.get(position);
        int year = d.getYear();
        int month = d.getMonthValue();
        ArrayList<String> dates;
        int day = d.getDayOfMonth();
        boolean available = false;
        if (!d.equals(null)) {
            holder.year = year;
            holder.month = month;
            holder.day = day;

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month-1, day);
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy");
            String date = dateFormat.format(calendar.getTime());

            ReadDatabaseHelper databaseHelper = new ReadDatabaseHelper(holder.context, date);
            databaseHelper.getReadableDatabase();
            dates = databaseHelper.getDates();
            for (String st : dates){
                if (st.equals(date)) available = true;
            }
        }
        holder.celldate.setText(String.valueOf(day));
        holder.mcardview.setEnabled(false);
        if (month != now.getMonthValue()) {
            holder.mcardview.setBackgroundColor(0);
        } else {
            if (available){
                holder.mcardview.setCardBackgroundColor(Color.BLACK);
                holder.celldate.setTextColor(Color.WHITE);
                holder.mcardview.setEnabled(true);
            } else if (daysofmonthLD.size() <= 7) {
                holder.mcardview.setBackgroundColor(0);
            }
            Log.i("day",String.valueOf(d));
            Log.i("day",String.valueOf(now));
            if (d.equals(now)){
                if (daysofmonthLD.size() <= 7) holder.mcardview.setEnabled(false);
                int x = Color.rgb(233, 165, 13);
                holder.mcardview.setCardBackgroundColor(x);
                holder.mcardview.setStrokeColor(x);
                holder.celldate.setTextColor(Color.WHITE);
            }
        }

    }


    @Override
    public int getItemCount() {

         return daysofmonthLD.size();
    }

    public static class CalViewHolder extends RecyclerView.ViewHolder {

        //public LinearLayout myLayout;

        int table;

        public MaterialCardView mcardview;
        public TextView celldate;
        public int year, month, day;



        Context context;

        public CalViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();

            //myLayout = itemView;

            mcardview = (MaterialCardView) itemView;
            celldate = itemView.findViewById(R.id.card_cal_day);


            mcardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition(); //what item has been clicked
//                    Toast.makeText(view.getContext(), String.valueOf(Navigation.findNavController(view).getGraph()), Toast.LENGTH_SHORT).show();
                    Bundle b = new Bundle();
                    b.putInt("YEAR", year);
                    b.putInt("MONTH", month);
                    b.putInt("DAY", day);
                    if (Navigation.findNavController(view).getGraph().getId() == (R.id.nav_archive) ) {
                        if (year == LocalDate.now().getYear() && month == LocalDate.now().getMonthValue() && day == LocalDate.now().getDayOfMonth()) {
                            Navigation.findNavController(view).navigate(R.id.action_archiveFragment_to_activity_main);
                        } else
                            Navigation.findNavController(view).navigate(R.id.action_archiveFragment_to_fragment_archive_recycler, b);
                    } else {
                        Navigation.findNavController(view).navigate(R.id.action_activity_main_to_archiveRecyclerFragment, b);
                    }
                }
            });

        }
    }
}