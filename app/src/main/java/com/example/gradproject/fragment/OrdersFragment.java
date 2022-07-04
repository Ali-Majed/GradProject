package com.example.gradproject.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gradproject.adapter.recycler.RecyclerOrderAdapter;
import com.example.gradproject.databinding.FragmentOrdersBinding;
import com.example.gradproject.modle.Orders;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;


public class OrdersFragment extends Fragment {

    private FragmentOrdersBinding binding;
    private ArrayList<Orders> ordersArrayList=new ArrayList<>();
    private RecyclerOrderAdapter recyclerOrderAdapter;
    private FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    private LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentOrdersBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




        firebaseFirestore.collection("order").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                Orders order = snapshot.toObject(Orders.class);
                                if (Objects.requireNonNull(FirebaseAuth.getInstance()
                                        .getCurrentUser()).getUid().equals(order.getIdCompany())){
                                    Log.d("TAGuser", "onComplete: "+order.getIdCompany());
                                    order.setIdOrder(snapshot.getId());

                                    ordersArrayList.add(order);


                                }
                                recyclerOrderAdapter.notifyDataSetChanged();

                            }
                        }
                    }
                });




        recyclerOrderAdapter=new RecyclerOrderAdapter(requireContext(),ordersArrayList);

        binding.recyclerOrder.setAdapter(recyclerOrderAdapter);
    }
}