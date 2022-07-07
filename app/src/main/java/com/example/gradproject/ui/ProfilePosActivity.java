package com.example.gradproject.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.gradproject.databinding.ActivityProfilePosBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class ProfilePosActivity extends AppCompatActivity {

    ActivityProfilePosBinding binding;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    long type;
    String name,email,number,place;
    String image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityProfilePosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseFirestore.collection("users").document(firebaseAuth.getCurrentUser().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){

                            name=(String) task.getResult().getData().get("namePos");
                            email=(String) task.getResult().getData().get("emailPos");
                            number=(String) task.getResult().getData().get("numberPos");
                            place=(String) task.getResult().getData().get("placePos");
                            image=(String) task.getResult().getData().get("imagePos");
                            if (type==0){
                               binding.profilePosName.setText(name);
                               binding.profilePosEmail.setText(email);
                               binding.profilePosPhone.setText(number);
                               binding.profilePosPlace.setText(place);
                                Picasso.get().load(image).into(binding.imageViewPosProfile);

//
                            }
                        }
                    }
                });

   }
}