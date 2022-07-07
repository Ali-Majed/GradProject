package com.example.gradproject.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gradproject.R;
import com.example.gradproject.adapter.recycler.RecyclerOrderAdapter;
import com.example.gradproject.adapter.recycler.RecyclerOrderPosAdapter;
import com.example.gradproject.databinding.FragmentOrderPosBinding;
import com.example.gradproject.databinding.FragmentOrdersBinding;
import com.example.gradproject.modle.Orders;
import com.example.gradproject.modle.UserPOS;
import com.example.gradproject.modle.UsersCompany;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderPosFragment extends Fragment {
private FragmentOrderPosBinding binding;
    private ArrayList<Orders> ordersArrayList=new ArrayList<>();
    private RecyclerOrderPosAdapter recyclerOrderAdapter;
    private FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentOrderPosBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseFirestore.collection("order")
                .orderBy("time", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                Orders order = snapshot.toObject(Orders.class);
//                               firebaseFirestore.collection("order").orderBy("time").startAt(order.getTime());
                                if (Objects.requireNonNull(FirebaseAuth.getInstance()
                                        .getCurrentUser()).getUid().equals(order.getIdPos())){
                                    Log.d("TAGuser", "onComplete: "+order.getIdCompany());
                                    order.setIdOrder(snapshot.getId());
                                    ordersArrayList.add(order);
                                }
                                recyclerOrderAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
        recyclerOrderAdapter=new RecyclerOrderPosAdapter(requireContext(),ordersArrayList);
        binding.recyclerOrderPos.setAdapter(recyclerOrderAdapter);
    }


}