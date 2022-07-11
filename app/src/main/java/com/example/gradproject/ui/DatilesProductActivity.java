package com.example.gradproject.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.gradproject.R;
import com.example.gradproject.databinding.ActivityDatilesProductBinding;

public class DatilesProductActivity extends AppCompatActivity {

    private ActivityDatilesProductBinding binding;
    private Intent intent;
    private String img,name,size,quantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDatilesProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intent=getIntent();
        if (intent!=null){
            if (intent.hasExtra("img")&&intent.hasExtra("name_product")&&
                    intent.hasExtra("size")&&intent.hasExtra("quantity")){
                img=intent.getStringExtra("img");
                name=intent.getStringExtra("name_product");
                size=intent.getStringExtra("size");
                quantity=intent.getStringExtra("quantity");

            }
        }

        Glide.with(getApplicationContext()).load(img).into(binding.detailesImg);
        binding.detailesName.setText(name);
        binding.detailesSize.setText(size);
        binding.detailesQuantity.setText(quantity);

        binding.imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}