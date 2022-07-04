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

import com.example.gradproject.adapter.recycler.RecyclerCompanyAdapter;
import com.example.gradproject.databinding.FragmentCompanyPosBinding;
import com.example.gradproject.interfaces.CompanyActionListener;
import com.example.gradproject.modle.UsersCompany;
import com.example.gradproject.ui.ProductPosActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class CompanyPosFragment extends Fragment {


    private FragmentCompanyPosBinding binding;
    ArrayList<UsersCompany> usersCompanies=new ArrayList<>();
    RecyclerCompanyAdapter recyclerCompanyAdapter;
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCompanyPosBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseFirestore.collection("users")
                .whereEqualTo("usertypePos",0)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                UsersCompany usersCompany = snapshot.toObject(UsersCompany.class);
                                    Log.d("TAGuser", "onComplete: "+usersCompany.getId());
                                    usersCompany.setId(snapshot.getId());
                                    usersCompanies.add(usersCompany);

                                recyclerCompanyAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

        recyclerCompanyAdapter=new RecyclerCompanyAdapter(requireContext(), usersCompanies, new CompanyActionListener() {
            @Override
            public void onCompanyActionListener(String id,String company) {
                Intent intent=new Intent(requireContext(),ProductPosActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("name",company);
                startActivity(intent);

            }
        });


        binding.recyclerCompanyPos.setAdapter(recyclerCompanyAdapter);
    }
}