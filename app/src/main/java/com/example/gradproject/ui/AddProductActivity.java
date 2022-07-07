package com.example.gradproject.ui;

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

import com.example.gradproject.databinding.ActivityAddProductBinding;
import com.example.gradproject.modle.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class AddProductActivity extends AppCompatActivity {
    private ActivityAddProductBinding binding;
    private static final int REQUEST_PICK_PHOTO=100;
    private Uri imageUri;
    private String photoUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckData()){
                    binding.addProductProgressBar.setVisibility(View.VISIBLE);
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
                                        String uid= Objects.requireNonNull(FirebaseAuth.getInstance()
                                                .getCurrentUser().getUid());
                                        Log.d("TAG",uid);
                                        Product product=new Product(binding.addProductEditNameProduct.getText().toString()
                                                ,binding.addProductEditSizeProduct.getText().toString()
                                                , binding.addProductEditQuantityProduct.getText().toString()
                                                ,photoUri,uid);
                                        DocumentReference documentReference= FirebaseFirestore.getInstance()
                                                .collection("product").document();
                                        product.setId_product(documentReference.getId());
                                        documentReference.set(product).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    binding.addProductProgressBar.setVisibility(View.GONE);
                                                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                                                }else {
                                                    binding.addProductProgressBar.setVisibility(View.GONE);
                                                    Toast.makeText(getApplicationContext(), "Failure ", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                binding.addProductProgressBar.setVisibility(View.GONE);
                                                Log.e("error_product",e.getMessage());
                                            }
                                        });
                                    }
                                });
                                Log.d("TAG",photoUri+"");
                            }
                        }
                    });
                    Log.d("TAG",photoUri+"");
                }
                Log.d("img",imageUri+"");
            }
        });
        binding.addProductImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPhoto();
            }
        });
    }
    private boolean CheckData() {

        if ( !binding.addProductEditNameProduct.getText().toString().isEmpty()
                &&!binding.addProductEditQuantityProduct.getText().toString().isEmpty()
                &&!binding.addProductEditSizeProduct.getText().toString().isEmpty()
        ) {
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
                binding.addProductSetImageView.setImageURI(imageUri);
            } else {
                Log.d("ttt",  null);
            }
        }
    }
}