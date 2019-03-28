package com.example.week5weekend;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class PermissionsManager {
    private static final int PERMISSION_REQUEST = 200;
    OnPermissionResult permissionManager;
    Context context;
    String[] permissions;
    boolean isPermissionGranted;

    public PermissionsManager(Context context, String[] permissions){
        this.context = context;
        this.permissionManager = (OnPermissionResult) context;
        this.permissions = permissions;
        this.isPermissionGranted = false;
    }

    public void checkPermission(){
        for (String permission : permissions) {
            isPermissionGranted = ContextCompat.checkSelfPermission(context, permission)
                    == PackageManager.PERMISSION_GRANTED;
        }

        if (!isPermissionGranted) {

            for (String permission : permissions) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)context,
                        permission)) {
                } else {
                    requestPermission();
                }
            }
        } else {
            permissionManager.onPermissionResult(true);
        }
    }

    public void requestPermission() {
        ActivityCompat.requestPermissions((Activity) context, permissions, PERMISSION_REQUEST);
    }

    public void checkResult(int requestCode, String permissions[], int[] results) {
        switch (requestCode) {
            case PERMISSION_REQUEST: {
                if (results.length > 0
                        && results[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionManager.onPermissionResult(true);
                } else {
                    permissionManager.onPermissionResult(false);
                }
                return;
            }
        }
    }

    public interface OnPermissionResult {
        void onPermissionResult(boolean isPermissionGranted);
    }

}