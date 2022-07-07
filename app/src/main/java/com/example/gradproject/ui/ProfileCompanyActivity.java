package com.example.gradproject.ui;

import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.gradproject.databinding.ActivityProfileCompanyBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class ProfileCompanyActivity extends AppCompatActivity {
private ActivityProfileCompanyBinding binding;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    long type;
    String name,email,number,place;
    String image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityProfileCompanyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseFirestore.collection("users").document(firebaseAuth.getCurrentUser().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){

                            name=(String) task.getResult().getData().get("nameCompany");
                            email=(String) task.getResult().getData().get("email");
                            number=(String) task.getResult().getData().get("number");
                            place=(String) task.getResult().getData().get("place");
                            image=(String) task.getResult().getData().get("imageurl");
                            if (type==0){
                                binding.profileCompanyName.setText(name);
                                binding.profileCompanyEmail.setText(email);
                                binding.profileCompanyPhone.setText(number);
                                binding.profileCompanyPlace.setText(place);
//                                Picasso.get().load(image).into(binding.imageViewCompany);
                                Glide.with(getApplicationContext()).load(image).into(binding.imageViewCompany);
//
                            }
                        }
                    }
                });

        binding.imageButtonProfileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}