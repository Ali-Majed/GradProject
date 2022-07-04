package com.example.gradproject.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.gradproject.R;
import com.example.gradproject.databinding.ActivityAddOrderPosBinding;
import com.example.gradproject.modle.Orders;
import com.example.gradproject.modle.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AddOrderPosActivity extends AppCompatActivity {

    private ActivityAddOrderPosBinding binding;
    private Intent intent;
    private String name,quantity,size,id,namePos,time,idCompany,nameCompany;
    private FirebaseFirestore firebaseFirestore;
    private Uri img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddOrderPosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseFirestore=FirebaseFirestore.getInstance();

        intent=getIntent();
        if (intent!=null){
            if (intent.hasExtra("productId")&&intent.hasExtra("productName")&&
                    intent.hasExtra("productImage_product")&&intent.hasExtra("companyId")
            &&intent.hasExtra("companyName")){


                id=intent.getStringExtra("productId");
                name=intent.getStringExtra("productName");
                nameCompany=intent.getStringExtra("companyName");
                img=Uri.parse(intent.getStringExtra("productImage_product"));
                idCompany=intent.getStringExtra("companyId");


            }
        }

        Picasso.get().load(img).into(binding.imageView7);
        binding.addOrderPosName.setText(name);

        binding.addOrderPosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckData()){
                    binding.addProductPosProgressBar.setVisibility(View.VISIBLE);


                    firebaseFirestore.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()){

                                        time=getDateFormat(getCurrentDate());
                                        quantity=binding.addOrderPosQuantity.getText().toString();
                                        size=binding.addOrderPosSize.getText().toString();
                                        namePos= (String) task.getResult().getData().get("namePos");
                                        String uid= Objects.requireNonNull(FirebaseAuth.getInstance()
                                                .getCurrentUser().getUid());
                                        Log.d("TAG",uid);
                                        Orders orders=new Orders(namePos,name,quantity,size,img+"",uid,time,idCompany,nameCompany);

                                        DocumentReference documentReference= FirebaseFirestore.getInstance()
                                                .collection("order").document();
                                        orders.setIdOrder(documentReference.getId());
                                        documentReference.set(orders).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    binding.addProductPosProgressBar.setVisibility(View.GONE);
                                                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                                                }else {
                                                    binding.addProductPosProgressBar.setVisibility(View.GONE);

                                                    Toast.makeText(getApplicationContext(), "Failure ", Toast.LENGTH_SHORT).show();

                                                }
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                binding.addProductPosProgressBar.setVisibility(View.GONE);

                                                Log.e("error_productPos",e.getMessage());
                                            }
                                        });

                                    }

                                }
                            });
                }
            }
        });

    }

    public Date getCurrentDate(){
        return (Date) Calendar.getInstance().getTime();
    }
    String getDateFormat(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd/MM", Locale.ENGLISH);
        return simpleDateFormat.format(date);
    }
    private boolean CheckData() {

        if ( !binding.addOrderPosSize.getText().toString().isEmpty()
                &&!binding.addOrderPosQuantity.getText().toString().isEmpty()
        ) {

            return true;

        }

        Snackbar.make(binding.getRoot(), "Enter required data", Snackbar.LENGTH_SHORT).show();

        return false;
    }
}