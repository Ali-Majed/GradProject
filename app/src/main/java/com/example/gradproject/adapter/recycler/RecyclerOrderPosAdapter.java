package com.example.gradproject.adapter.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gradproject.databinding.ItemOrdersBinding;
import com.example.gradproject.databinding.ItemOrdersPosBinding;
import com.example.gradproject.modle.Orders;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerOrderPosAdapter extends RecyclerView.Adapter<RecyclerOrderPosAdapter.OrderPosViewHolder> {
    private Context context;
    private ArrayList<Orders> ordersArrayList;

    public RecyclerOrderPosAdapter(Context context, ArrayList<Orders> ordersArrayList) {
        this.context = context;
        this.ordersArrayList = ordersArrayList;
    }

    @NonNull
    @Override
    public OrderPosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderPosViewHolder((ItemOrdersPosBinding.inflate(LayoutInflater.from(context),parent,false)));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderPosViewHolder holder, int position) {
        Orders orders=ordersArrayList.get(position);

        holder.itemOrdersPosBinding.itemOrdersPosName.setText(orders.getNameProduct());
        holder.itemOrdersPosBinding.itemOrdersPosQuantity.setText(orders.getQuantity());
        holder.itemOrdersPosBinding.itemOrderPosSize.setText(orders.getSize());
        holder.itemOrdersPosBinding.itemOrdersPosNameCompany.setText(orders.getNameCompany());
        holder.itemOrdersPosBinding.itemOrderPosTimeProduct.setText(orders.getTime());
        Picasso.get().load(orders.getImageOrder()).into(holder.itemOrdersPosBinding.itemOrdersPosImageView);



    }

    @Override
    public int getItemCount() {
        return ordersArrayList.size();
    }


    class OrderPosViewHolder extends RecyclerView.ViewHolder{
        private ItemOrdersPosBinding itemOrdersPosBinding;

        public OrderPosViewHolder(ItemOrdersPosBinding itemOrdersPosBinding) {
            super(itemOrdersPosBinding.getRoot());
            this.itemOrdersPosBinding=itemOrdersPosBinding;
        }
    }
}
