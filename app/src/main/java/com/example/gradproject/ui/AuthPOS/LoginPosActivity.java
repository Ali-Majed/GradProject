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
import com.example.gradproject.ui.Auth.RegisterActivity;
import com.example.gradproject.ui.ChoeseActivity;
import com.example.gradproject.ui.ForgetPasswordActivityVE;
import com.example.gradproject.ui.MainActivity;
import com.example.gradproject.ui.PosNavigationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class LoginPosActivity extends AppCompatActivity {
    private ActivityLoginPosBinding binding;
    FirebaseAuth firebaseAuthPos=FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    long type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityLoginPosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.loginNewAccountPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent2=new Intent(getApplicationContext(),ChoeseActivity.class);
                    startActivity(intent2);
            }
        });

        binding.forgetPasswordPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ForgetPasswordActivityVE.class));
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
                                        firebaseFirestore.collection("users").document(firebaseAuthPos.getCurrentUser().getUid())
                                                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()){
                                                            Log.d("eee", "onComplete: "+task.getResult().getData());
                                                            Log.d("eee", "onComplete: "+firebaseAuthPos.getCurrentUser().getUid());
                                                            type= (long) task.getResult().getData().get("usertypePos");
                                                            if (type==1){
                                                                Toast.makeText(getApplicationContext(), "تم تسجيل الدخول بنجاح", Toast.LENGTH_SHORT).show();
                                                                Intent intentPos=new Intent(getApplicationContext(), PosNavigationActivity.class);
                                                                startActivity(intentPos);
                                                            }else if (type==0){
                                                                Toast.makeText(getApplicationContext(), "تم تسجيل الدخول بنجاح", Toast.LENGTH_SHORT).show();
                                                                Intent intentCompany=new Intent(getApplicationContext(), MainActivity.class);
                                                                intentCompany.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                startActivity(intentCompany);

//                                                                finish();
                                                            }
                                                        }
                                                    }
                                                });
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
