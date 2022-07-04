package com.example.gradproject.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.gradproject.R;
import com.example.gradproject.adapter.navigation.NavAdapter;
import com.example.gradproject.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {


    NavAdapter navAdapter;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.imageButtonSitting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),SittingActivity.class);
                startActivity(intent);

            }
        });

        navAdapter=new NavAdapter(this);
        binding.viewPager.setAdapter(navAdapter);



        binding.nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.product:
                        binding.viewPager.setCurrentItem(0);
                        break;
                    case R.id.pos:
                        binding.viewPager.setCurrentItem(1);
                        break;
                    case R.id.order:
                        binding.viewPager.setCurrentItem(2);
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
                        binding.nav.getMenu().findItem(R.id.product).setChecked(true);
                        break;
                    case 1:
                        binding.nav.getMenu().findItem(R.id.pos).setChecked(true);

                        break;

                    case 2:
                        binding.nav.getMenu().findItem(R.id.order).setChecked(true);
                        break;



                }
            }
        });





    }
}