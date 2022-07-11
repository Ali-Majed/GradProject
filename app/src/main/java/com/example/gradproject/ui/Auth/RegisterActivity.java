package com.example.gradproject.ui.Auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.example.gradproject.databinding.ActivityRegisterBinding;
import com.example.gradproject.modle.UserPOS;
import com.example.gradproject.modle.UsersCompany;
import com.example.gradproject.ui.AuthPOS.LoginPosActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    FirebaseAuth firebaseAuth;
    private static final int REQUEST_PICK_PHOTO=100;
    private Uri imageUri;
    private String photoUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth=FirebaseAuth.getInstance();
        binding.registerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPhoto();
            }
        });
        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CheckData()) {
                    binding.registerProgressBar.setVisibility(View.VISIBLE);

                    firebaseAuth.createUserWithEmailAndPassword(binding.registerEditTextEmail.getText().toString()
                                    ,binding.registerEditTextPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        StorageReference storageReference= FirebaseStorage.getInstance().getReference()
                                                .child(System.currentTimeMillis()+"."+getFileExtension(imageUri));
                                        storageReference.putFile(imageUri)
                                                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                                        if (task.isSuccessful()){
                                                            task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                @Override
                                                                public void onSuccess(Uri uri) {
                                                                    photoUri=uri.toString();


                                                                    String uid=firebaseAuth.getCurrentUser().getUid();
                                        UsersCompany usersCompany = new UsersCompany(uid,binding.registerEditTextNameCompany.getText().toString()
                                                ,binding.registerEditTextDistributorName.getText().toString()
                                                ,binding.registerEditTextEmail.getText().toString()
                                                ,binding.registerEditTextPassword.getText().toString()
                                                ,binding.registerEditTextPlace.getText().toString()
                                                ,binding.registerEditTextNumber.getText().toString(),0,photoUri);
                                        DocumentReference documentReference= FirebaseFirestore.getInstance()
                                                .collection("users").document(firebaseAuth.getCurrentUser().getUid());
                                        usersCompany.setId(documentReference.getId());
                                        documentReference.set(usersCompany).
                                                addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    binding.registerProgressBar.setVisibility(View.GONE);
                                                    firebaseAuth.getCurrentUser().sendEmailVerification();
                                                    firebaseAuth.signOut();
                                                    Toast.makeText(getApplicationContext(), "Account created successfully, please verify your email", Toast.LENGTH_SHORT).show();                                                    Intent intent=new Intent(getApplicationContext(),LoginPosActivity.class);
                                                    startActivity(intent);

                                                }
                                            }
                                        })
                                                .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                binding.registerProgressBar.setVisibility(View.GONE);
                                                Log.e("reror",e.getMessage());
                                            }
                                        });
                                                                }
                                                            });
                                                            Log.d("TAG",photoUri+"");
                                                        }
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

    private String getFileExtension(Uri itemUri) {
        return
                MimeTypeMap.getSingleton().getExtensionFromMimeType(getContentResolver().getType(itemUri));
    }
    public void selectPhoto() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_PICK_PHOTO);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PICK_PHOTO && resultCode ==
                RESULT_OK) {
            if (data.getData() != null) {
                imageUri = data.getData();
                binding.registerImageView.setImageURI(imageUri);
            } else {
                Log.d("ttt",  null);
            }
        }
    }
}