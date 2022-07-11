package com.example.gradproject.adapter.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gradproject.databinding.ItemProductPosBinding;
import com.example.gradproject.interfaces.ProductPosActionListener;
import com.example.gradproject.modle.Product;

import java.util.ArrayList;

public class RecyclerProductPosAdapter extends RecyclerView.Adapter<RecyclerProductPosAdapter.ProductPosViewHolder> {
    private Context context;
    private ArrayList<Product> productArrayList;
    private ProductPosActionListener productActionListener;

    public RecyclerProductPosAdapter(Context context, ArrayList<Product> productArrayList, ProductPosActionListener productPosActionListener) {
        this.context = context;
        this.productArrayList = productArrayList;
        this.productActionListener = productPosActionListener;
    }

    @NonNull
    @Override
    public ProductPosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductPosViewHolder(ItemProductPosBinding.inflate(LayoutInflater.from(context),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductPosViewHolder holder, int position) {
        Product product=productArrayList.get(position);
        holder.itemProductPosBinding.itemProductPosName.setText(product.getName());
        Glide.with(context).load(product.getImage_product()).into(holder.itemProductPosBinding.itemProductPosImageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productActionListener.onProductPosActionListener(product);
            }
        });


    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    class ProductPosViewHolder extends RecyclerView.ViewHolder{
ItemProductPosBinding itemProductPosBinding;

        public ProductPosViewHolder(ItemProductPosBinding itemProductPosBinding) {
            super(itemProductPosBinding.getRoot());
            this.itemProductPosBinding=itemProductPosBinding;
        }
    }
}
