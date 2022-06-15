package com.example.gradproject.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gradproject.R;
import com.example.gradproject.ui.AddProductActivity;
import com.example.gradproject.ui.ChangePasswordActivity;

public class SittingsFragment extends Fragment {
TextView tv_btn_change_password;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sittings, container, false);



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_btn_change_password=getView().findViewById(R.id.change_password_sittings);

        tv_btn_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

    }
}