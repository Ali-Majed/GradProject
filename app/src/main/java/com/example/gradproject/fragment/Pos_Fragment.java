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
import com.example.gradproject.interfaces.PosActionListener;
import com.example.gradproject.interfaces.callbacks.ListCallback;
import com.example.gradproject.modle.FirestoreController;
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
private FirestoreController firestoreController;

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


        firestoreController=new FirestoreController();



        adapter=new RecyclerPosAdapter(requireContext(), arrayList);
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

    @Override
    public void onStart() {
        super.onStart();
        readPos();
    }

    private void readPos(){
        firestoreController.readPos (new ListCallback<UserPOS>() {
            @Override
            public void onFinished(List<UserPOS> list, boolean success) {
                arrayList.clear();
                arrayList.addAll(list);
                adapter.notifyDataSetChanged();
                if (arrayList.size()==0) {
                    binding.textView2.setVisibility(View.VISIBLE);
                }else {
                    binding.textView2.setVisibility(View.GONE);

                }
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