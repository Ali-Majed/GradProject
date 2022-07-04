package com.example.gradproject.adapter.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gradproject.databinding.ItemOrdersBinding;
import com.example.gradproject.modle.Orders;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerOrderAdapter extends RecyclerView.Adapter<RecyclerOrderAdapter.OrderViewHolder>{

    private Context context;
    private ArrayList<Orders> ordersArrayList;



    public RecyclerOrderAdapter(Context context, ArrayList<Orders> ordersArrayList) {
        this.context = context;
        this.ordersArrayList = ordersArrayList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(ItemOrdersBinding.inflate(LayoutInflater.from(context),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Orders orders=ordersArrayList.get(position);
        holder.itemOrdersBinding.itemOrderNamePos.setText(orders.getNamePos());
        holder.itemOrdersBinding.itemOrderNameProduct.setText(orders.getNameProduct());
        holder.itemOrdersBinding.itemOrderQuantity.setText(orders.getQuantity());
        holder.itemOrdersBinding.itemOrderSize.setText(orders.getSize());
        holder.itemOrdersBinding.itemOrderTimeProduct.setText(orders.getTime());
        Picasso.get().load(orders.getImageOrder()).into(holder.itemOrdersBinding.imageViewOrder);



    }

    @Override
    public int getItemCount() {
        return ordersArrayList.size();

    }

    class OrderViewHolder extends RecyclerView.ViewHolder{
ItemOrdersBinding itemOrdersBinding;

        public OrderViewHolder(ItemOrdersBinding itemOrdersBinding) {
            super(itemOrdersBinding.getRoot());
            this.itemOrdersBinding=itemOrdersBinding;

        }
    }
}
