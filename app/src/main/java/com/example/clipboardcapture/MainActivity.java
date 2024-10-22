package com.example.clipboardcapture;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.materialswitch.MaterialSwitch;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13 or higher
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }
        MaterialSwitch materialSwitch;

        materialSwitch = findViewById(R.id.switch1);

        materialSwitch.setChecked(clipboardServiceRunning());

        materialSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Log.d("MainActivity.OnCreate","Switch checked: "+isChecked);
            if(isChecked){
                runClipboardService();
            }else{
                Log.d("MainActivity","switch OFF -> stop ClipboardService");
                stopClipboardService();
            }
        });
    }

    private void runClipboardService(){
        Intent serviceIntent = new Intent(this, ClipboardService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent);
        }else{
            startService(serviceIntent);
        }
    }

    private void stopClipboardService(){
        Intent serviceIntent = new Intent(this, ClipboardService.class);
        stopService(serviceIntent);
    }

    public boolean clipboardServiceRunning(){
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service: activityManager.getRunningServices(Integer.MAX_VALUE)){
            if(ClipboardService.class.getName().equals(service.service.getClassName())){
                Log.d("MainActivity","ClipboardService is running");
                return true;
            }
        }
        Log.d("MainActivity","ClipboardService is NOT running");
        return false;
    }

}