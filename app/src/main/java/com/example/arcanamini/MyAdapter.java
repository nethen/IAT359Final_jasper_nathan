package com.example.arcanamini;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    public ArrayList<String> list;
    int table;
    Context context;


    public MyAdapter(ArrayList<String> list, int table) {

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

            myLayout = itemView;

            mcardview = (MaterialCardView) itemView;

            this.table = table;


            //mcardview = itemView.findViewById(R.id.card_row);
            title = itemView.findViewById(R.id.card_title);
            subtitle = itemView.findViewById(R.id.card_subtitle);
            mcardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition(); //what item has been clicked
                    Toast.makeText(context,
                            "You have clicked " + ((TextView) view.findViewById(R.id.card_title)).getText().toString(),
                            Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent (view.getContext(), CardDetailsActivity.class);
                    intent.putExtra ("ITEM_KEY", position);

                    intent.putExtra ("ITEM_TABLE", table);

                    intent.putExtra ("ITEM_NAME", ((TextView) view.findViewById(R.id.card_title)).getText().toString());

                    view.getContext().startActivity(intent);
                }
            });

            context = itemView.getContext();

        }
    }
}