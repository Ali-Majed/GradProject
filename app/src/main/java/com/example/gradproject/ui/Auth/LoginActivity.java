package com.example.gradproject.ui.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gradproject.R;
import com.example.gradproject.databinding.ActivityLoginBinding;
import com.example.gradproject.databinding.ActivityRegisterBinding;
import com.example.gradproject.ui.Auth.RegisterActivity;
import com.example.gradproject.ui.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    public FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.loginNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });


        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CheckData()){

                    binding.loginProgressBar.setVisibility(View.VISIBLE);

                    firebaseAuth.signInWithEmailAndPassword(binding.loginEmailEditText.getText().toString(), binding.loginPasswordEditText.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        binding.loginProgressBar.setVisibility(View.GONE);

                                        Toast.makeText(getApplicationContext(), "تم تسجيل الدخول بنجاح", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                        startActivity(intent);
                                    }else {
                                        Toast.makeText(getApplicationContext(), "فشل تسجيل الدخول", Toast.LENGTH_SHORT).show();

                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    binding.loginProgressBar.setVisibility(View.GONE);

                                    Log.d("ttt", e.getMessage());
                                }
                            });

                }

            }
        });





    }

    private boolean CheckData() {

        if ( !binding.loginEmailEditText.getText().toString().isEmpty()
                &&!binding.loginPasswordEditText.getText().toString().isEmpty()
          ) {

            return true;

        }

        Snackbar.make(binding.getRoot(), "Enter required data", Snackbar.LENGTH_SHORT).show();

        return false;
    }


}