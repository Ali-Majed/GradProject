package com.example.gradproject.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.gradproject.fragment.OrdersFragment;
import com.example.gradproject.fragment.Pos_Fragment;
import com.example.gradproject.fragment.ProductFragment;

public class  NavAdapter extends FragmentStateAdapter {


    public NavAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position==0){
            return new ProductFragment();
        }else if (position==1){
            return new Pos_Fragment();

        }else if (position==2){
            return new OrdersFragment();

        }
        return new ProductFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
