package com.example.gradproject.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.gradproject.R;
import com.example.gradproject.adapter.navigation.NavPOSAdapter;
import com.example.gradproject.databinding.ActivityPosNavigationBinding;
import com.google.android.material.navigation.NavigationBarView;

public class PosNavigationActivity extends AppCompatActivity {
ActivityPosNavigationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityPosNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        NavPOSAdapter navPOSAdapter=new NavPOSAdapter(this);
        binding.viewPager.setAdapter(navPOSAdapter);

        binding.nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.company:
                        binding.viewPager.setCurrentItem(0);
                        break;
                    case R.id.order_pos:
                        binding.viewPager.setCurrentItem(1);
                        break;



                }
                return true;
            }


        });


        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        binding.nav.getMenu().findItem(R.id.company).setChecked(true);
                        break;
                    case 1:
                        binding.nav.getMenu().findItem(R.id.order_pos).setChecked(true);

                        break;





                }
            }
        });


    }
}