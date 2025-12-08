package com.example.ramusage;

import android.app.ActivityManager;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ramusage.databinding.ActivityMainBinding;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        long totalRam = getTotalRAM();
        double totalRamInGB = totalRam / 1024.0 / 1024.0 / 1024.0;

        long freeRam = getFreeRAM();
        double freeRamInGB = freeRam / 1024.0 / 1024.0 / 1024.0;

        long usedRam = totalRam - freeRam; // used RAM in bytes
        double usedRamInGB = usedRam / 1024.0 / 1024.0 / 1024.0;

        binding.textView5.setText(String.format("Used RAM: %.2f GB",usedRamInGB));
        binding.textView3.setText(String.format("Free RAM: %.2f GB",freeRamInGB));
        binding.textView4.setText(String.format("Total RAM: %.2f GB",totalRamInGB));
    }

    private long getTotalRAM() {
        ActivityManager actManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        actManager.getMemoryInfo(memInfo);

        // Total RAM in bytes
        return memInfo.totalMem;
    }
    private long getFreeRAM() {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memInfo);

        // Available RAM in bytes
        return memInfo.availMem;
    }
}