package com.example.gradproject.adapter.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gradproject.databinding.ItemPosBinding;
import com.example.gradproject.interfaces.PosActionListener;
import com.example.gradproject.modle.Product;
import com.example.gradproject.modle.UserPOS;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerPosAdapter extends RecyclerView.Adapter<RecyclerPosAdapter.PosViewHolder> {

    private Context context;
    private List<UserPOS> posArrayList;
    private PosActionListener posActionListener;

    public RecyclerPosAdapter(Context context, List<UserPOS> posArrayList) {
        this.context = context;
        this.posArrayList = posArrayList;
    }

    @NonNull
    @Override
    public PosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PosViewHolder(ItemPosBinding.inflate(LayoutInflater.from(context),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PosViewHolder holder, int position) {
        UserPOS userPOS=posArrayList.get(position);
        holder.itemPosBinding.itemNamePos.setText(userPOS.getNamePos());
        holder.itemPosBinding.itemPosPlace.setText(userPOS.getPlacePos());
        Glide.with(context).load(userPOS.getImagePos()).into(holder.itemPosBinding.itemPosImageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Name :"+userPOS.getNamePos()
                        +"\n" +
                        "Email :"+userPOS.getEmailPos()+
                        "\n" +
                        "Place :"+userPOS.getPlacePos()
                        +"\n" +
                        "Number :"+userPOS.getNumberPos(),Toast.LENGTH_LONG).show();
            }
        });

    }

    public void filterList(List<UserPOS> userPOSList){
        posArrayList=userPOSList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return posArrayList.size();
    }

    class PosViewHolder extends RecyclerView.ViewHolder{
        ItemPosBinding itemPosBinding;
        public PosViewHolder(ItemPosBinding itemPosBinding) {
            super(itemPosBinding.getRoot());
            this.itemPosBinding=itemPosBinding;
        }
    }
}
