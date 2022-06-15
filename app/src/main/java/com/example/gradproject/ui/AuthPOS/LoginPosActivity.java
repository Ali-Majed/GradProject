package com.example.gradproject.ui.AuthPOS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.gradproject.databinding.ActivityLoginPosBinding;
import com.example.gradproject.modle.UserPOS;
import com.example.gradproject.modle.UsersCompany;
import com.example.gradproject.ui.Auth.RegisterActivity;
import com.example.gradproject.ui.MainActivity;
import com.example.gradproject.ui.PosNavigationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;


public class LoginPosActivity extends AppCompatActivity {
    private ActivityLoginPosBinding binding;

    FirebaseAuth firebaseAuthPos=FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    UserPOS userPOS=new UserPOS();
    int usertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityLoginPosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent=getIntent();
        if (intent!=null){
            if (intent.hasExtra("usertype")){

                usertype=intent.getIntExtra("usertype",0);

            }
        }
        Log.d("usertype",usertype+"");
        binding.loginNewAccountPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (usertype==1){
                    Intent intent=new Intent(getApplicationContext(), RegisterPosActivity.class);
                    startActivity(intent);

                }else if (usertype==0){
                    Intent intent=new Intent(getApplicationContext(), RegisterActivity
                            .class);
                    startActivity(intent);
                }
            }
        });


        binding.loginButtonPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                if (CheckData()){




                    binding.loginProgressBarPos.setVisibility(View.VISIBLE);
                    firebaseAuthPos.signInWithEmailAndPassword(binding.loginEmailPosEditText.getText().toString()
                                    , binding.loginPasswordPosEditText.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()){
                                        binding.loginProgressBarPos.setVisibility(View.GONE);
//                                        String uid=task.getResult().getUser().getUid();
//                                        DocumentReference documentReference= firebaseFirestore
//                                                .collection("users_pos").document("usertypePos");

//                                                        Log.d("uu",userPOS.getUsertypePos()+"");
                                        if (usertype==1){
                                            Toast.makeText(getApplicationContext(), "تم تسجيل الدخول بنجاح", Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(getApplicationContext(), PosNavigationActivity.class);
                                            startActivity(intent);

                                        }else if (usertype==0){
                                            Toast.makeText(getApplicationContext(), "تم تسجيل الدخول بنجاح", Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                        }
                                        Log.d("uu",userPOS.getUsertypePos()+"");


                                    }else {
                                        Toast.makeText(getApplicationContext(), "فشل تسجيل الدخول", Toast.LENGTH_SHORT).show();

                                    }


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    binding.loginProgressBarPos.setVisibility(View.GONE);

                                    Log.d("ttt", e.getMessage());
                                }
                            });



                }




            }
        });




    }

    private boolean CheckData() {

        if ( !binding.loginEmailPosEditText.getText().toString().isEmpty()
                &&!binding.loginPasswordPosEditText.getText().toString().isEmpty()
        ) {

            return true;

        }

        Snackbar.make(binding.getRoot(), "Enter required data", Snackbar.LENGTH_SHORT).show();

        return false;
    }





}