package com.example.arcanamini;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    public ArrayList<String> list;
    Context context;


    public MyAdapter(ArrayList<String> list) {
        this.list = list;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
        String[] results = (list.get(position).toString()).split(",");
        holder.nameButton.setText(results[0]);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout myLayout;
        public Button nameButton;

        Context context;

        public MyViewHolder(View itemView) {
            super(itemView);
            myLayout = (LinearLayout) itemView;


            nameButton = (Button) itemView.findViewById(R.id.cardNameButton);
            nameButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition(); //what item has been clicked
                    Toast.makeText(context,
                            "You have clicked " + ((TextView) view.findViewById(R.id.cardNameButton)).getText().toString(),
                            Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent (view.getContext(), CardDetailsActivity.class);
                    intent.putExtra ("ITEM_KEY", position);
                    intent.putExtra ("ITEM_NAME", ((TextView) view.findViewById(R.id.cardNameButton)).getText().toString());
                    view.getContext().startActivity(intent);
                }
            });

            context = itemView.getContext();

        }

        @Override
        public void onClick(View view) {

        }
    }
}