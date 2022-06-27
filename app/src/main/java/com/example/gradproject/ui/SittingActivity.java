package com.example.gradproject.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.gradproject.databinding.ActivitySittingBinding;
import com.example.gradproject.ui.AuthPOS.LoginPosActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SittingActivity extends AppCompatActivity {

    private ActivitySittingBinding binding;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySittingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.getCurrentUser();

        binding.profileSittings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),ProfileCompanyActivity.class);
                startActivity(intent);
            }
        });

        binding.changePasswordSittings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(getApplicationContext(),ChangePasswordActivity.class);
                startActivity(intent1);
            }
        });

        binding.logoutSittings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                firebaseAuth.signOut();
                Intent intent1=new Intent(getApplicationContext(), LoginPosActivity.class);
                startActivity(intent1);
                finish();


            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}