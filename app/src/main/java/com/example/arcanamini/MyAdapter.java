package com.example.arcanamini;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.google.android.material.card.MaterialCardView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements Filterable{
    public ArrayList<String> list;
    public ArrayList<String> originalList;
    int table;
    Context context;


    public MyAdapter(ArrayList<String> list, int table) {
        this.originalList = list;
        this.list = list;
        this.table = table;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v, table);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
        String[] results = (list.get(position).toString()).split(",");
        holder.title.setText(results[0]);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        //public LinearLayout myLayout;

        int table;

        public MaterialCardView mcardview;
        public TextView title;
        public TextView subtitle;


        Context context;

        public MyViewHolder(View itemView, int table) {
            super(itemView);

            //myLayout = itemView;

            mcardview = (MaterialCardView) itemView;

            this.table = table;


            //mcardview = itemView.findViewById(R.id.card_row);
            title = itemView.findViewById(R.id.card_title);
            mcardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition(); //what item has been clicked
                    //Toast.makeText(context,
//                            "You have clicked " + ((TextView) view.findViewById(R.id.card_title)).getText().toString(),
//                            Toast.LENGTH_SHORT).show();

                    Bundle b = new Bundle();
                    b.putInt ("ITEM_KEY", position );
                    b.putInt ("ITEM_TABLE", table );
                    b.putString ("ITEM_NAME", ((TextView) view.findViewById(R.id.card_title)).getText().toString());
//                    getActivity().startActivity(intent);
                    Navigation.findNavController(view).navigate(R.id.action_fragment_cardrecycler_to_card_details, b);

                }
            });

            context = itemView.getContext();

        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (ArrayList<String>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<String> filteredResults = null;
                if (constraint.length() == 0) {
                    filteredResults = originalList;
                } else {
                    filteredResults = getFilteredResults(constraint.toString().toLowerCase());
                }

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }
        };
    }

    protected ArrayList<String> getFilteredResults(String constraint) {
        ArrayList<String> results = new ArrayList<>();

        for (String item : originalList) {
            if (item.toLowerCase().contains(constraint)) {
                results.add(item);
            }
        }
        return results;
    }


}