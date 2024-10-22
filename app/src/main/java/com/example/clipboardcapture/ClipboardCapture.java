package com.example.clipboardcapture;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

public class ClipboardCapture extends AppCompatActivity {

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            Log.d("ClipboardCapture", "Window focus gained.");

            // Delay clipboard capture by 500ms to ensure clipboard is available
            captureClipboardContent();

            minimizeApp();
        }
    }

    // Method to capture clipboard content
    private void captureClipboardContent() {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        if (clipboardManager != null) {
            Log.d("ClipboardCapture", "ClipboardManager is not null.");
            if (clipboardManager.hasPrimaryClip()) {
                ClipData clipData = clipboardManager.getPrimaryClip();
                if (clipData != null && clipData.getItemCount() > 0) {
                    Log.d("ClipboardCapture", "Captured clipboard content: " + clipData.getItemAt(0).getText());
                } else {
                    Log.d("ClipboardCapture", "No clip data available.");
                }
            } else {
                Log.d("ClipboardCapture", "No primary clip in clipboard.");
            }
        }
    }
    // Method to minimize the app after capturing clipboard content
    private void minimizeApp() {
        Log.d("ClipboardCapture", "Minimizing the app.");
        Intent mainActivityIntent = new Intent(ClipboardCapture.this, MainActivity.class);
        startActivity(mainActivityIntent);
        moveTaskToBack(true); // Move the activity to the background
    }

}
