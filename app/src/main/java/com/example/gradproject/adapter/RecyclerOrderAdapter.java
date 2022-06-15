package com.example.gradproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gradproject.R;
import com.example.gradproject.modle.Orders;

import java.util.ArrayList;

public class RecyclerOrderAdapter extends RecyclerView.Adapter <RecyclerOrderAdapter.OrderViewHolder>{

    Context context;
    ArrayList<Orders> ordersArrayList;


    public RecyclerOrderAdapter(Context context, ArrayList<Orders> ordersArrayList) {
        this.context = context;
        this.ordersArrayList = ordersArrayList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(LayoutInflater.from(context).inflate(R.layout.item_orders,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return ordersArrayList.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder{


        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
