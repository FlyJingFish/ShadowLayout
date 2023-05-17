package com.flyjingfish.shadowlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.flyjingfish.shadowlayout.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.shadowLayout.setOnClickListener(v -> {});
    }
}