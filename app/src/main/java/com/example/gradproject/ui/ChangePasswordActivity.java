package com.example.gradproject.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.gradproject.R;
import com.example.gradproject.databinding.ActivityChangePasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ChangePasswordActivity extends AppCompatActivity {

    private ActivityChangePasswordBinding binding;
    private FirebaseAuth firebaseAuth;

    private FirebaseUser firebaseUser;
    private String password;
    private SharedPreferences sp;
    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();


        binding.changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckData()){

                    FirebaseFirestore.getInstance().collection("user")
                            .document(firebaseAuth.getCurrentUser().getUid())
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    String password=(String) task.getResult().getData().get("password");
                                    String email=(String) task.getResult().getData().get("email");

                                    if (password!=null){


                                    }

                                    firebaseUser.updatePassword(binding.changePasswordNewEditText.getText().toString())
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                        }
                                    });



                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });

                }

            }
        });

    }

    private boolean CheckData() {
        if (!binding.changePasswordOldEditText.getText().toString().isEmpty()
                && !binding.changePasswordOldEditText.getText().toString().isEmpty()
                && !binding.changePasswordConfirmNewEditText.getText().toString().isEmpty()) {
            return true;
        }
        Snackbar.make(binding.getRoot(), "Enter required data", Snackbar.LENGTH_SHORT).show();
        return false;
    }
}