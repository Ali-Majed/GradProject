package com.example.gradproject.adapter;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.example.gradproject.callbacks.ListCallback;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;



import java.util.List;

public class FirebaseStorageController {

    FirebaseStorage storage = FirebaseStorage.getInstance();



    public void getImages(ListCallback<StorageReference> listCallback) {
        storage.getReference("image/").listAll().addOnCompleteListener(new OnCompleteListener<ListResult>() {
            @Override
            public void onComplete(@NonNull Task<ListResult> task) {
                if(task.isSuccessful()){
                    List<StorageReference> referenceList = task.getResult().getItems();
                    listCallback.onFinished(referenceList,true);

                }else{
                    listCallback.onFinished(null,false);
                }
            }
        });
    }
}
