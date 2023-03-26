package com.example.arcanamini;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;

public class CalAdapter extends RecyclerView.Adapter<CalAdapter.CalViewHolder>{
    public ArrayList<String> daysofmonth;

    Context context;

     public CalAdapter(ArrayList<String> daysofmonth){
         super();
         this.daysofmonth = daysofmonth;
     }

    @Override
    public CalAdapter.CalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_cal, parent, false);
        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
//        layoutParams.height = (int) (parent.getHeight());
        CalViewHolder viewHolder = new CalViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CalAdapter.CalViewHolder holder, int position) {
//         Log.i("calI", String.valueOf(position));
        String s = daysofmonth.get(position);
        holder.celldate.setText(s);
        if (s.isEmpty()) {
            holder.mcardview.setBackgroundColor(0);
            holder.mcardview.setEnabled(false);
        } else {
            holder.mcardview.setEnabled(true);
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

//                    Intent intent = new Intent (view.getContext(), CardDetailsActivity.class);
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