package com.example.gradproject.ui.AuthPOS;

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
import com.example.gradproject.databinding.ActivityRegisterPosBinding;
import com.example.gradproject.modle.Product;
import com.example.gradproject.modle.UserPOS;
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

public class RegisterPosActivity extends AppCompatActivity {
    private ActivityRegisterPosBinding binding;
    FirebaseAuth firebaseAuth;
    private static final int REQUEST_PICK_PHOTO=100;
    private Uri imageUri;
    private String photoUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRegisterPosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();

        binding.registerImageViewPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPhoto();
            }
        });
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
                                        UserPOS userPOS = new UserPOS(uid,binding.registerEditTextPosName.getText().toString()
                                                ,binding.registerEditTextPosEmail.getText().toString()
                                                ,binding.registerEditTextPosPassword.getText().toString()
                                                ,binding.registerEditTextPosNumber.getText().toString()
                                                ,binding.registerEditTextPosPlace.getText().toString(),1,photoUri);
                                        DocumentReference documentReference= firebaseFirestore
                                                .collection("users").document(firebaseAuth.getCurrentUser().getUid());
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
                binding.registerImageViewPos.setImageURI(imageUri);
            } else {
                Log.d("ttt",  null);
            }
        }
    }
}