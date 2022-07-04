package com.example.gradproject.adapter.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gradproject.R;
import com.example.gradproject.interfaces.ProductActionListener;
import com.example.gradproject.modle.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerProductAdapter extends RecyclerView.Adapter<RecyclerProductAdapter.ProductViewHolder> {
    Context context;
    ArrayList<Product> productArrayList;
    private ProductActionListener productActionListener;

    public RecyclerProductAdapter(Context context, ArrayList<Product> productArrayList) {
        this.context = context;
        this.productArrayList = productArrayList;
    }

    public RecyclerProductAdapter(Context context, ArrayList<Product> productArrayList, ProductActionListener productActionListener) {
        this.context = context;
        this.productArrayList = productArrayList;
        this.productActionListener = productActionListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product=productArrayList.get(position);
        holder.nameProduct.setText(product.getName());
        holder.sizeProduct.setText(product.getSize());
        holder.quantityProduct.setText(product.getQuantity());
        Picasso.get().load(product.getImage_product()).into(holder.imageViewProduct);



    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView nameProduct,sizeProduct,quantityProduct;
        ImageView imageViewProduct;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);


            nameProduct=itemView.findViewById(R.id.item_product_name);
            sizeProduct=itemView.findViewById(R.id.item_product_size);
            quantityProduct=itemView.findViewById(R.id.item_product_quantity);
            imageViewProduct=itemView.findViewById(R.id.item_product_imageView);

        }
    }

}
