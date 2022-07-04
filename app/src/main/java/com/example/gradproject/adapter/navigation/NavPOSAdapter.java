package com.example.gradproject.adapter.navigation;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.gradproject.fragment.CompanyPosFragment;
import com.example.gradproject.fragment.OrderPosFragment;
import com.example.gradproject.fragment.OrdersFragment;
import com.example.gradproject.fragment.Pos_Fragment;
import com.example.gradproject.fragment.ProductFragment;

public class NavPOSAdapter extends FragmentStateAdapter {
    public NavPOSAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position==0){
            return new CompanyPosFragment();
        }else if (position==1){
            return new OrderPosFragment();

        }
        return new CompanyPosFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
