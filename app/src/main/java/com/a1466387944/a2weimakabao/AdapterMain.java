package com.a1466387944.a2weimakabao;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterMain extends RecyclerView.Adapter<AdapterMain.MyViewHolder> {
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> infos = new ArrayList<>();
    ArrayList<Boolean> stard = new ArrayList<>();

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name_textview;
        public TextView info_textview;
        public ImageButton star_button;
        public MyViewHolder(View v) {
            super(v);
        }
    }

    public AdapterMain(BarcodeClass[] barcodes){
        for (int i = 0; i < barcodes.length; i++){
            names.add(barcodes[i].getName());
            stard.add(barcodes[i].getIsStared());
            if (barcodes[i].getInfo()!=null)
                infos.add(barcodes[i].getInfo());
            else infos.add(" ");
        }
    }

    @Override
    public AdapterMain.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_main,
                parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        myViewHolder.info_textview = view.findViewById(R.id.text_info_item);
        myViewHolder.name_textview = view.findViewById(R.id.text_name_item);
        myViewHolder.star_button = view.findViewById(R.id.button_star);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
       holder.name_textview.setText(names.get(position));
       holder.info_textview.setText(infos.get(position));
    }

    @Override
    public int getItemCount() {
        return names.size();
    }
}
