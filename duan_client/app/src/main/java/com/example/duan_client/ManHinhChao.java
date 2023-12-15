package com.example.duan_client;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ManHinhChao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chao);
        ImageView ivlogo = findViewById(R.id.ivlogo);

        Glide.with(this).load(R.drawable.logo).into(ivlogo);
        new Handler().postDelayed(() -> {
            startActivity(new Intent(ManHinhChao.this, LoginActivity.class));
            finish();
        },3000);
    }
}