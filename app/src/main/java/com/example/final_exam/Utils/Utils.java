package com.example.final_exam.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.core.app.ActivityCompat;

import com.example.final_exam.R;

public class Utils {
    private static final int REQUEST_PHONE_CALL = 1;
    public static void callToEmergencies(Context context) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(String.valueOf(R.string.emergency_telephone)));//change the number.
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
            return;
        }
        context.startActivity(callIntent);
    }
}