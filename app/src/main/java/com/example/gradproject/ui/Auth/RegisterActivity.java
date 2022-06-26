package com.example.gradproject.ui.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.gradproject.databinding.ActivityRegisterBinding;
import com.example.gradproject.modle.UsersCompany;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth=FirebaseAuth.getInstance();

        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CheckData()) {

                    firebaseAuth.createUserWithEmailAndPassword(binding.registerEditTextEmail.getText().toString()
                                    ,binding.registerEditTextPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        binding.registerProgressBar.setVisibility(View.VISIBLE);
                                        String uid= Objects.requireNonNull(task.getResult().getUser()).getUid();
                                        UsersCompany usersCompany = new UsersCompany(uid,binding.registerEditTextNameCompany.getText().toString()
                                                ,binding.registerEditTextDistributorName.getText().toString()
                                                ,binding.registerEditTextEmail.getText().toString()
                                                ,binding.registerEditTextPassword.getText().toString()
                                                ,binding.registerEditTextPlace.getText().toString()
                                                ,binding.registerEditTextNumber.getText().toString(),0);
                                        DocumentReference documentReference= FirebaseFirestore.getInstance()
                                                .collection("userscompany").document();



                                        usersCompany.setId(documentReference.getId());
                                        documentReference.set(usersCompany).addOnCompleteListener(new OnCompleteListener<Void>() {

                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {


                                                if (task.isSuccessful()){
                                                    binding.registerProgressBar.setVisibility(View.GONE);
                                                    Toast.makeText(getApplicationContext(), "Success Created Account", Toast.LENGTH_SHORT).show();

                                                }

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                binding.registerProgressBar.setVisibility(View.GONE);
                                                Log.e("reror",e.getMessage());


                                            }
                                        });




                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("error",e.getMessage());

                                }
                            });
                }
            }
        });


    }

    private boolean CheckData() {

        if (!binding.registerEditTextNameCompany.getText().toString().isEmpty()
                && !binding.registerEditTextEmail.getText().toString().isEmpty()
                && !binding.registerEditTextDistributorName.getText().toString().isEmpty()
                && !binding.registerEditTextNumber.getText().toString().isEmpty()
                && !binding.registerEditTextPassword.getText().toString().isEmpty()
                && !binding.registerEditTextPlace.getText().toString().isEmpty()) {

            return true;

        }

        Snackbar.make(binding.getRoot(), "Enter required data", Snackbar.LENGTH_SHORT).show();

        return false;
    }

}