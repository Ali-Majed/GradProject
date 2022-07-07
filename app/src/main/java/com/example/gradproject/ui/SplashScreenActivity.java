package com.example.gradproject.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.gradproject.R;
import com.example.gradproject.ui.AuthPOS.LoginPosActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SplashScreenActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    long type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        firebaseAuth = FirebaseAuth.getInstance();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isLoggedIn()){

                    firebaseFirestore.collection("users").document(firebaseAuth.getCurrentUser().getUid())
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()){
                                        Log.d("eee", "onComplete: "+task.getResult().getData());
                                        Log.d("eee", "onComplete: "+firebaseAuth.getCurrentUser().getUid());
                                        type= (long) task.getResult().getData().get("usertypePos");
                                        if (type==1){
                                            Intent intentPos=new Intent(getApplicationContext(), PosNavigationActivity.class);
                                            intentPos.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intentPos);
                                        }else if (type==0){
                                            Intent intentCompany=new Intent(getApplicationContext(), MainActivity.class);
                                            intentCompany.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intentCompany);
                                        }
                                    }
                                }
                            });

                }
                else {
                    Intent intent=new Intent(getApplicationContext(), LoginPosActivity.class);
                    startActivity(intent);
                }

            }
        }, 3000);


    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
    public boolean isLoggedIn() {
        return firebaseAuth.getCurrentUser() != null;
    }
}