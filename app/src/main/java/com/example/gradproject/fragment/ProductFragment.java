package com.example.gradproject.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gradproject.R;
import com.example.gradproject.adapter.recycler.RecyclerProductAdapter;
import com.example.gradproject.modle.Product;
import com.example.gradproject.ui.AddProductActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class ProductFragment extends Fragment {
FloatingActionButton fab;
RecyclerView recyclerView;
FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    ArrayList<Product> products=new ArrayList<>();
    RecyclerProductAdapter recyclerProductAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab=getView().findViewById(R.id.fab_product);
        recyclerView=getView().findViewById(R.id.recycler_product);
        recyclerProductAdapter=new RecyclerProductAdapter(requireContext(), products);
        recyclerView.setAdapter(recyclerProductAdapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), AddProductActivity.class);
                startActivity(intent);
            }
        });

        firebaseFirestore.collection("product").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                Product product = snapshot.toObject(Product.class);
                                if (Objects.requireNonNull(FirebaseAuth.getInstance()
                                        .getCurrentUser()).getUid().equals(product.getUser_id())){
                                    Log.d("TAGuser", "onComplete: "+product.getUser_id());
                                    product.setId_product(snapshot.getId());

                                    products.add(product);


                                }
                                recyclerProductAdapter.notifyDataSetChanged();

                            }
                        }
                    }
                });


    }


}