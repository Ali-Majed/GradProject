package com.example.gradproject.adapter.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gradproject.databinding.ItemConpanyPosBinding;
import com.example.gradproject.interfaces.CompanyActionListener;
import com.example.gradproject.modle.UsersCompany;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerCompanyAdapter extends RecyclerView.Adapter<RecyclerCompanyAdapter.CompanyViewHolder> {

    private Context context;
    private ArrayList<UsersCompany> arrayList;
    private CompanyActionListener companyActionListener;

    public RecyclerCompanyAdapter(Context context, ArrayList<UsersCompany> arrayList, CompanyActionListener companyActionListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.companyActionListener = companyActionListener;
    }

    @NonNull
    @Override
    public CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CompanyViewHolder(ItemConpanyPosBinding.inflate(LayoutInflater.from(context),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyViewHolder holder, int position) {
        UsersCompany usersCompany= arrayList.get(position);
        holder.itemConpanyPosBinding.itemPosCompanyName.setText(usersCompany.getNameCompany());
        holder.itemConpanyPosBinding.itemPosCompanyPlace.setText(usersCompany.getPlace());
        Picasso.get().load(usersCompany.getImageurl()).into(holder.itemConpanyPosBinding.itemPosCompanyImageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                companyActionListener.onCompanyActionListener( usersCompany.getId(),usersCompany.getNameCompany());

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class CompanyViewHolder extends RecyclerView.ViewHolder{

        private ItemConpanyPosBinding itemConpanyPosBinding;
        public CompanyViewHolder(ItemConpanyPosBinding itemConpanyPosBinding) {
            super(itemConpanyPosBinding.getRoot());
                this.itemConpanyPosBinding=itemConpanyPosBinding;
        }
    }
}
