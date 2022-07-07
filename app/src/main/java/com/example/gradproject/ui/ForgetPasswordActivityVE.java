package com.example.gradproject.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.gradproject.databinding.ActivityForgetPasswordVeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class ForgetPasswordActivityVE extends AppCompatActivity {

    private ActivityForgetPasswordVeBinding binding;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityForgetPasswordVeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth=FirebaseAuth.getInstance();

        binding.loginEmailChangePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResetPassword();
            }
        });


    }

    private void ResetPassword(){

        String email=binding.loginEmailChangeEditText.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            binding.loginEmailChangeEditText.setError("Please Enter Valid Email");
            binding.loginEmailChangeEditText.requestFocus();
            return;
        }
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){
                    Log.d("emailvrefy", "onComplete: "+task.getResult());
                    Toast.makeText(getApplicationContext(),"Please Check You Email to Reset Password",Toast.LENGTH_LONG).show();
                    onBackPressed();
                }else {
                    Toast.makeText(getApplicationContext(),"Failed to Reset Password",Toast.LENGTH_LONG).show();

                }
            }
        });


    }
}