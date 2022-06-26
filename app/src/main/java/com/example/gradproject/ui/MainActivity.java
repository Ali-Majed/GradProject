package com.example.gradproject.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.example.gradproject.R;
import com.example.gradproject.adapter.NavAdapter;
import com.example.gradproject.fragment.SittingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    BottomNavigationView navigationView;
    NavAdapter navAdapter;
    ImageButton settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager2=findViewById(R.id.viewPager);
        navigationView=findViewById(R.id.nav);






        navAdapter=new NavAdapter(this);
        viewPager2.setAdapter(navAdapter);



        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.product:
                        viewPager2.setCurrentItem(0);
                        break;
                    case R.id.pos:
                        viewPager2.setCurrentItem(1);
                        break;
                    case R.id.order:
                        viewPager2.setCurrentItem(2);
                        break;


                }
                return true;
            }


        });


        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        navigationView.getMenu().findItem(R.id.product).setChecked(true);
                        break;
                    case 1:
                        navigationView.getMenu().findItem(R.id.pos).setChecked(true);

                        break;

                    case 2:
                        navigationView.getMenu().findItem(R.id.order).setChecked(true);
                        break;



                }
            }
        });





    }
}