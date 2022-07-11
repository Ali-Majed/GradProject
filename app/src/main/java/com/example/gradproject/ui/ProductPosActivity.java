package com.example.gradproject.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.gradproject.adapter.recycler.RecyclerProductPosAdapter;
import com.example.gradproject.databinding.ActivityProductPosBinding;
import com.example.gradproject.interfaces.ProductPosActionListener;
import com.example.gradproject.modle.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ProductPosActivity extends AppCompatActivity {

    private ActivityProductPosBinding binding;
    private FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    private ArrayList<Product> products=new ArrayList<>();
    private RecyclerProductPosAdapter recyclerProductAdapter;
    private Intent intent;
    private String CompanyId,company;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityProductPosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        intent=getIntent();
        if (intent!=null){
            if (intent.hasExtra("id")&&intent.hasExtra("name")){
                CompanyId=intent.getStringExtra("id");
                company=intent.getStringExtra("name");

                Log.d("CompanyId", "onCreate: "+CompanyId);
                Log.d("CompanyId", "onCreate: "+company);
            }
        }


        recyclerProductAdapter=new RecyclerProductPosAdapter(getApplicationContext(), products, new ProductPosActionListener() {
            @Override
            public void onProductPosActionListener(Product product) {
                Intent intent=new Intent(getApplicationContext(),AddOrderPosActivity.class);
                intent.putExtra("productId",product.getId_product());
                intent.putExtra("productName",product.getName());
                intent.putExtra("companyId",CompanyId);
                intent.putExtra("companyName",company);
                intent.putExtra("productImage_product",product.getImage_product());
                startActivity(intent);
            }
        });
        binding.productPosRecycler.setAdapter(recyclerProductAdapter);

        firebaseFirestore.collection("product").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                Product product = snapshot.toObject(Product.class);
                                if (CompanyId.equals(product.getUser_id())) {
                                    Log.d("TAGuser", "onComplete: " + product.getUser_id());
                                    product.setId_product(snapshot.getId());
                                    products.add(product);
                                }
                                if (products.size()==0) {
                                    binding.textView2.setVisibility(View.VISIBLE);
                                }else {
                                    binding.textView2.setVisibility(View.GONE);

                                }
                                recyclerProductAdapter.notifyDataSetChanged();
                            }

                        }
                    }
                });

    }
}