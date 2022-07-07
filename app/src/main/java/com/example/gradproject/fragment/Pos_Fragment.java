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
import android.widget.SearchView;

import com.example.gradproject.R;
import com.example.gradproject.adapter.recycler.RecyclerPosAdapter;
import com.example.gradproject.databinding.FragmentPosBinding;
import com.example.gradproject.modle.UserPOS;
import com.example.gradproject.modle.UsersCompany;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class Pos_Fragment extends Fragment {
private FragmentPosBinding binding;
ArrayList<UserPOS> arrayList=new ArrayList<>();
private RecyclerPosAdapter adapter;
private FirebaseFirestore firebaseFirestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentPosBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseFirestore=FirebaseFirestore.getInstance();

        firebaseFirestore.collection("users")
                .whereEqualTo("usertypePos",1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                UserPOS userPOS = snapshot.toObject(UserPOS.class);
                                Log.d("TAGuser", "onComplete: "+userPOS.getIdPos());
                                userPOS.setIdPos(snapshot.getId());
                                arrayList.add(userPOS);

                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

        adapter=new RecyclerPosAdapter(requireContext(),arrayList);
        binding.recyclerPos.setAdapter(adapter);

        binding.searchViewPos.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                binding.searchViewPos.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s);
                return true;
            }
        });


    }
    private void filter(String s) {
        List<UserPOS> userPOSList=new ArrayList<>();
        for (UserPOS usersCompany:arrayList){
            if (usersCompany.getNamePos().toLowerCase().contains(s.toLowerCase())){
                userPOSList.add(usersCompany);
            }
        }
        adapter.filterList(userPOSList);
    }
}