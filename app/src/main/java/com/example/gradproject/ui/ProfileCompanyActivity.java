package com.example.gradproject.ui;

import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.gradproject.databinding.ActivityProfileCompanyBinding;

public class ProfileCompanyActivity extends AppCompatActivity {
private ActivityProfileCompanyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityProfileCompanyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imageButtonProfileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}