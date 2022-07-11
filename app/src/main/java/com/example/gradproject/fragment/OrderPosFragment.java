package com.example.gradproject.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gradproject.adapter.recycler.RecyclerOrderPosAdapter;
import com.example.gradproject.databinding.FragmentOrderPosBinding;
import com.example.gradproject.interfaces.callbacks.ListCallback;
import com.example.gradproject.modle.FirestoreController;
import com.example.gradproject.modle.Orders;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class OrderPosFragment extends Fragment {
private FragmentOrderPosBinding binding;
    private ArrayList<Orders> ordersArrayList=new ArrayList<>();
    private RecyclerOrderPosAdapter recyclerOrderAdapter;
    private FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    private FirestoreController firestoreController;
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

        firestoreController=new FirestoreController();


        recyclerOrderAdapter=new RecyclerOrderPosAdapter(requireContext(),ordersArrayList);
        binding.recyclerOrderPos.setAdapter(recyclerOrderAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        readPosOrder();
    }

    private void readPosOrder(){
        firestoreController.readOrderPos(new ListCallback<Orders>() {
            @Override
            public void onFinished(List<Orders> list, boolean success) {
                if (success){
                    ordersArrayList.clear();
                    ordersArrayList.addAll(list);
                    recyclerOrderAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}