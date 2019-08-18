package com.juanmcardenas.auth.location;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.juanmcardenas.auth.util.DialogUtil;

/**
 * Created by Martin Cardenas on 2019-08-16.
 */
public class GpsManager {

    private static final int FINE_LOCATION_PERMISSION_REQUEST_CODE = 912;

    public GpsManager() {
    }

    public Location get(Activity activity) {
        // Create Location manager
        LocationManager locationManager = (LocationManager)
                activity.getBaseContext().getSystemService(Context.LOCATION_SERVICE);
        // Null checks
        if (locationManager == null) {
            return null;
        }
        // Check if GPS is enabled

        // Get coordinates
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                DialogUtil.showLocationPermissionRequestDialog(activity);
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        FINE_LOCATION_PERMISSION_REQUEST_CODE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        return null;
    }
}
