package com.example.arcanamini;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ReadAdapter extends RecyclerView.Adapter<ReadAdapter.MyViewHolder> {
    public ArrayList<String> list;
    String date;

    Context context;


    public ReadAdapter(ArrayList<String> list, String date) {
        this.list = list;
        this.date = date;
    }

    @Override
    public ReadAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_reading, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v, date);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReadAdapter.MyViewHolder holder, int position) {
        String[] results = (list.get(position).toString()).split("~");
        holder.time.setText(results[0]);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        //public LinearLayout myLayout;

        int table;
        String date;
        public MaterialCardView mcardview;
        public TextView time;


        Context context;

        public MyViewHolder(View itemView, String date) {
            super(itemView);
            this.date = date;
            //myLayout = itemView;

            mcardview = (MaterialCardView) itemView;


            //mcardview = itemView.findViewById(R.id.card_row);

            time = itemView.findViewById(R.id.spread_time);

            mcardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition(); //what item has been clicked
//                    Toast.makeText(context,
//                            "You have clicked " + ((TextView) view.findViewById(R.id.card_title)).getText().toString(),
//                            Toast.LENGTH_SHORT).show();

//                    Intent intent = new Intent (view.getContext(), ReflectionDetailsActivity.class);
//                    intent.putExtra ("ITEM_KEY", position);
                    Bundle b = new Bundle();
                    b.putInt("ITEM_KEY", position);
                    b.putString("ITEM_DATE", date);
                    Log.i("dateDebug", date);
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy");
                    String dateX = dateFormat.format(calendar.getTime());
                    Log.i("dateDebug", dateX);

                    //intent.putExtra ("ITEM_NAME", ((TextView) view.findViewById(R.id.card_title)).getText().toString());
                    NavController n =  Navigation.findNavController(view);
                    if (n.getGraph().getId() == R.id.nav_archive)n.navigate(R.id.action_fragment_archive_recycler_to_reflectionDetailsFragment,b);
                    else if (n.getGraph().getId() == R.id.nav_graph){

                        if (date.equals(dateX)) n.navigate(R.id.action_activity_main_to_reflectionDetailsFragment2,b);
                            else n.navigate(R.id.action_archiveRecyclerFragment_to_reflectionDetailsFragment2,b);
                    }

                }
            });

            context = itemView.getContext();

        }
    }
}