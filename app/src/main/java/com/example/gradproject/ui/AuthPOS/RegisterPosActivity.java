package com.example.gradproject.ui.AuthPOS;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.gradproject.R;
import com.example.gradproject.callbacks.ProcessCallback;
import com.example.gradproject.databinding.ActivityLoginBinding;
import com.example.gradproject.databinding.ActivityLoginPosBinding;
import com.example.gradproject.databinding.ActivityRegisterPosBinding;
import com.example.gradproject.modle.UserPOS;
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

public class RegisterPosActivity extends AppCompatActivity {

    private ActivityRegisterPosBinding binding;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRegisterPosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();


        binding.registerPosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CheckData()) {

                    firebaseAuth.createUserWithEmailAndPassword(binding.registerEditTextPosEmail.getText().toString()
                                    ,binding.registerEditTextPosPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        binding.registerPosProgressBar.setVisibility(View.VISIBLE);
//



                                        String uid= Objects.requireNonNull(task.getResult().getUser()).getUid();
                                        UserPOS userPOS = new UserPOS(uid,binding.registerEditTextPosName.getText().toString()
                                                ,binding.registerEditTextPosEmail.getText().toString()
                                                ,binding.registerEditTextPosPassword.getText().toString()
                                                ,binding.registerEditTextPosNumber.getText().toString()
                                                ,binding.registerEditTextPosPlace.getText().toString(),1);
                                        DocumentReference documentReference= firebaseFirestore
                                                .collection("users").document(firebaseAuth.getCurrentUser().getUid());



//                                        userPOS.setIdPos(documentReference.getId());
                                        documentReference.set(userPOS).addOnCompleteListener(new OnCompleteListener<Void>() {

                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {


                                                if (task.isSuccessful()){
                                                    binding.registerPosProgressBar.setVisibility(View.GONE);
                                                    Toast.makeText(getApplicationContext(), "Success Created Account", Toast.LENGTH_SHORT).show();
                                                    Intent intent=new Intent(getApplicationContext(),LoginPosActivity.class);
                                                startActivity(intent);}

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                binding.registerPosProgressBar.setVisibility(View.GONE);
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

        if (!binding.registerEditTextPosName.getText().toString().isEmpty()
                && !binding.registerEditTextPosEmail.getText().toString().isEmpty()
                && !binding.registerEditTextPosNumber.getText().toString().isEmpty()
                && !binding.registerEditTextPosPassword.getText().toString().isEmpty()
                && !binding.registerEditTextPosPlace.getText().toString().isEmpty()) {

            return true;

        }

        Snackbar.make(binding.getRoot(), "Enter required data", Snackbar.LENGTH_SHORT).show();

        return false;
    }


}