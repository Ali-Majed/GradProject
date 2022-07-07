package com.example.gradproject.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.gradproject.databinding.ActivityChoeseBinding;
import com.example.gradproject.ui.Auth.RegisterActivity;
import com.example.gradproject.ui.AuthPOS.RegisterPosActivity;
import com.google.firebase.auth.FirebaseAuth;

public class ChoeseActivity extends AppCompatActivity {

    private ActivityChoeseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityChoeseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.choeseCompanyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getApplicationContext(), RegisterActivity.class);

                startActivity(intent);
            }
        });

        binding.choesePosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getApplicationContext(), RegisterPosActivity.class);
                intent.putExtra("usertype",1);
                startActivity(intent);
            }
        });
    }
}