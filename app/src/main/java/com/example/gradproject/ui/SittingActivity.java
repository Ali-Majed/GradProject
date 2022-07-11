package com.example.gradproject.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.gradproject.databinding.ActivitySittingBinding;
import com.example.gradproject.ui.AuthPOS.LoginPosActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SittingActivity extends AppCompatActivity {

    private ActivitySittingBinding binding;
    private FirebaseAuth firebaseAuth;
    long type;
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

                FirebaseFirestore.getInstance().collection("users")
                        .document(firebaseAuth.getCurrentUser().getUid())
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {


                                type= (long) task.getResult().getData().get("usertypePos");
                                switch ((int) type){
                                    case 0:
                                        Intent intentCompany=new Intent(getApplicationContext(), ProfileCompanyActivity.class);
                                        startActivity(intentCompany);
                                        break;
                                    case 1:
                                        Intent intentPos=new Intent(getApplicationContext(), ProfilePosActivity.class);
                                        startActivity(intentPos);
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

            }
        });

        binding.changePasswordSittings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseFirestore.getInstance().collection("users")
                        .document(firebaseAuth.getCurrentUser().getUid())
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                String email=(String) task.getResult().getData().get("email");
                                String emailPos=(String) task.getResult().getData().get("emailPos");
                                Log.d("email123", "onComplete: "+email);
                                Log.d("email123", "onComplete: "+emailPos);
                                type= (long) task.getResult().getData().get("usertypePos");
                                if (type==0){
                                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()){
                                                Log.d("emailvrefy", "onComplete: "+task.getResult());
                                                Toast.makeText(getApplicationContext(),"Please Check You Email to Reset Password",Toast.LENGTH_LONG).show();

                                            }else {
                                                Toast.makeText(getApplicationContext(),"Failed to Reset Password",Toast.LENGTH_LONG).show();
                                                Intent intent=new Intent(getApplicationContext(),LoginPosActivity.class);
                                                startActivity(intent);
                                            }
                                        }
                                    });

                                }
                                else if (type==1){
                                    firebaseAuth.sendPasswordResetEmail(emailPos).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()){
                                                Log.d("emailvrefy", "onComplete: "+task.getResult());
                                                Toast.makeText(getApplicationContext(),"Please Check You Email to Reset Password",Toast.LENGTH_LONG).show();

                                            }else {
                                                Toast.makeText(getApplicationContext(),"Failed to Reset Password",Toast.LENGTH_LONG).show();
                                                Intent intent=new Intent(getApplicationContext(),LoginPosActivity.class);
                                                startActivity(intent);
                                            }
                                        }
                                    });


                                }


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });



            }
        });

        binding.logoutSittings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                firebaseAuth.signOut();
                Intent intent1=new Intent(getApplicationContext(), LoginPosActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent1);


            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }


}