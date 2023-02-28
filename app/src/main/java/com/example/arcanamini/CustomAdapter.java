package com.example.arcanamini;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    public ArrayList<String> list;
    Context context;


    public CustomAdapter(ArrayList<String> list) {
        this.list = list;
    }

    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomAdapter.MyViewHolder holder, int position) {
        String[] results = (list.get(position).toString()).split(",");
        holder.nameTextView.setText(results[0]);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView nameTextView;
        public LinearLayout myLayout;

        Context context;

        public MyViewHolder(View itemView) {
            super(itemView);
            myLayout = (LinearLayout) itemView;

            nameTextView = (TextView) itemView.findViewById(R.id.cardNameTextView);

            itemView.setOnClickListener(this);
            context = itemView.getContext();

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition(); //what item has been clicked
            Toast.makeText(context,
                    "You have clicked " + ((TextView) view.findViewById(R.id.cardNameTextView)).getText().toString(),
                    Toast.LENGTH_SHORT).show();

            Intent intent = new Intent (view.getContext(), CardDetailsActivity.class);
            intent.putExtra ("ITEM_KEY", position);
            intent.putExtra ("ITEM_NAME", ((TextView) view.findViewById(R.id.cardNameTextView)).getText().toString());
            view.getContext().startActivity(intent);
        }
    }
}