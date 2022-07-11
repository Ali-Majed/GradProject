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

import com.example.gradproject.adapter.recycler.RecyclerCompanyAdapter;
import com.example.gradproject.databinding.FragmentCompanyPosBinding;
import com.example.gradproject.interfaces.CompanyActionListener;
import com.example.gradproject.interfaces.callbacks.ListCallback;
import com.example.gradproject.modle.FirestoreController;
import com.example.gradproject.modle.Product;
import com.example.gradproject.modle.UsersCompany;
import com.example.gradproject.ui.ProductPosActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class CompanyPosFragment extends Fragment {
    private FragmentCompanyPosBinding binding;
    ArrayList<UsersCompany> usersCompanies=new ArrayList<>();
    RecyclerCompanyAdapter recyclerCompanyAdapter;
    private FirestoreController firestoreController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCompanyPosBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firestoreController=new FirestoreController();

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


        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                binding.searchView.clearFocus();
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
        readCompanies();
    }

    private void readCompanies(){
        firestoreController.readCompany(new ListCallback<UsersCompany>() {
            @Override
            public void onFinished(List<UsersCompany> list, boolean success) {
                usersCompanies.clear();
                usersCompanies.addAll(list);
                recyclerCompanyAdapter.notifyDataSetChanged();
                if (usersCompanies.size()==0) {
                    binding.textView2.setVisibility(View.VISIBLE);
                }else {
                    binding.textView2.setVisibility(View.GONE);

                }
            }
        });
    }
    private void filter(String s) {
        List<UsersCompany> usersCompanyList=new ArrayList<>();
        for (UsersCompany usersCompany:usersCompanies){
            if (usersCompany.getNameCompany().toLowerCase().contains(s.toLowerCase())){
                usersCompanyList.add(usersCompany);
            }
        }
        recyclerCompanyAdapter.filterList(usersCompanyList);
    }
}