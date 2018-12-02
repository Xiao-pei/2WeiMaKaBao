package com.a1466387944.a2weimakabao;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterMain extends RecyclerView.Adapter<AdapterMain.MyViewHolder> {

    public interface MyClickItemListener {
        void onClicked(View view, int position);
    }

    ArrayList<BarcodeClass> barcodeClasses;
    private MyClickItemListener itemListener;
    private MyClickItemListener starButtonListener;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name_textview;
        public TextView info_textview;
        public ImageButton star_button;
        public CardView card_view;
        public MyViewHolder(View v) {
            super(v);
        }

    }

    public AdapterMain(ArrayList<BarcodeClass> barcodes) {
        barcodeClasses = barcodes;
    }

    @Override
    public AdapterMain.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_main,
                parent,false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        myViewHolder.info_textview = view.findViewById(R.id.text_info_item);
        myViewHolder.name_textview = view.findViewById(R.id.text_name_item);
        myViewHolder.star_button = view.findViewById(R.id.button_star);
        myViewHolder.card_view = view.findViewById(R.id.card_view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.name_textview.setText(barcodeClasses.get(position).getName());
        holder.info_textview.setText(barcodeClasses.get(position).getInfo());
        if (barcodeClasses.get(position).IsStared())
            holder.star_button.setImageResource(R.drawable.ic_star_full);
        else
            holder.star_button.setImageResource(R.drawable.ic_star_empty);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemListener.onClicked(view, holder.getLayoutPosition());
            }
        });

        holder.star_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                starButtonListener.onClicked(view, holder.getLayoutPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return barcodeClasses.size();
    }

    public void setItemListener(MyClickItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void setStarButtonListener(MyClickItemListener starButtonListener) {
        this.starButtonListener = starButtonListener;
    }
}
