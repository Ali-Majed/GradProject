package com.example.gradproject.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.gradproject.adapter.recycler.RecyclerProductAdapter;
import com.example.gradproject.databinding.FragmentProductBinding;
import com.example.gradproject.interfaces.ProductActionListener;
import com.example.gradproject.interfaces.callbacks.ListCallback;
import com.example.gradproject.modle.FirestoreController;
import com.example.gradproject.modle.Product;
import com.example.gradproject.ui.AddProductActivity;
import com.example.gradproject.ui.DatilesProductActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ProductFragment extends Fragment {
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    List<Product> products=new ArrayList<>();

    FirestoreController firestoreController;
    RecyclerProductAdapter recyclerProductAdapter;
    FragmentProductBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentProductBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }



    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerProductAdapter = new RecyclerProductAdapter(requireContext(), products, new ProductActionListener() {
            @Override
            public void onProductActionListener(Product product) {
                Intent intent=new Intent(requireContext(), DatilesProductActivity.class);
                intent.putExtra("img",product.getImage_product());
                intent.putExtra("name_product",product.getName());
                intent.putExtra("size",product.getSize());
                intent.putExtra("quantity",product.getQuantity());

                startActivity(intent);

            }
        });
        binding.recyclerProduct.setAdapter(recyclerProductAdapter);
        binding.fabProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddProductActivity.class);
                startActivity(intent);
            }
        });

        firestoreController=new FirestoreController();

        binding.searchViewProduct.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                binding.searchViewProduct.clearFocus();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s);
                return true;
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        Log.d("start123456", "onStart: ");
        readProduct();
        Log.d("start123456", "onStart1: ");
    }
    private void filter(String s) {
        List<Product> productList=new ArrayList<>();
        for (Product product:products){
            if (product.getName().toLowerCase().contains(s.toLowerCase())){
                productList.add(product);
            }
        }
        recyclerProductAdapter.filterList(productList);
    }

    private void readProduct(){
        firestoreController.readProduct(new ListCallback<Product>() {
            @Override
            public void onFinished(List<Product> list, boolean success) {
                if (success){
                    products.clear();
                    products.addAll(list);
                    Log.d("size", "onViewCreated: "+products.size());
                    recyclerProductAdapter.notifyDataSetChanged();
                    if (products.size()==0) {
                        binding.textView2.setVisibility(View.VISIBLE);
                    }else {
                        binding.textView2.setVisibility(View.GONE);

                    }
                }
            }
        });
    }
}