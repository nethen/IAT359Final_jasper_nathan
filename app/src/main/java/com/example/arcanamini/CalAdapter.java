package com.example.arcanamini;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class CalAdapter extends RecyclerView.Adapter<CalAdapter.CalViewHolder>{
    public ArrayList<String> daysofmonth;

    Context context;
    LocalDate now;

     public CalAdapter(ArrayList<String> daysofmonth, LocalDate now){
         super();
         this.daysofmonth = daysofmonth;
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
        String s = daysofmonth.get(position);
        if (!s.isEmpty()) {
            holder.year = LocalDate.now().getYear();
            holder.month = LocalDate.now().getMonthValue();
            holder.day = s;
        }
        holder.celldate.setText(s);
        if (s.isEmpty()) {
            holder.mcardview.setBackgroundColor(0);
            holder.mcardview.setEnabled(false);
        } else {
            holder.mcardview.setEnabled(true);
            if (s.equals(String.valueOf(now.getDayOfMonth())) && now.getMonth() == LocalDate.now().getMonth()){
                int x = Color.rgb(233, 165, 13);
                holder.mcardview.setCardBackgroundColor(x);
                holder.mcardview.setStrokeColor(x);
                holder.celldate.setTextColor(Color.WHITE);
            }
        }

    }


    @Override
    public int getItemCount() {

         return daysofmonth.size();
    }

    public static class CalViewHolder extends RecyclerView.ViewHolder {

        //public LinearLayout myLayout;

        int table;

        public MaterialCardView mcardview;
        public TextView celldate;
        public int year, month;
        public String day;


        Context context;

        public CalViewHolder(View itemView) {
            super(itemView);

            //myLayout = itemView;

            mcardview = (MaterialCardView) itemView;
            celldate = itemView.findViewById(R.id.card_cal_day);



            mcardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition(); //what item has been clicked
                    Toast.makeText(view.getContext(), String.valueOf(year+"-"+month+"-"+day), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent (view.getContext(), CardDetailsActivity.class);
//                    intent.putExtra ("ITEM_KEY", position);
//
//                    intent.putExtra ("ITEM_TABLE", table);
//
//                    intent.putExtra ("ITEM_NAME", ((TextView) view.findViewById(R.id.card_title)).getText().toString());
//
//                    view.getContext().startActivity(intent);
                }
            });

            context = itemView.getContext();

        }
    }
}