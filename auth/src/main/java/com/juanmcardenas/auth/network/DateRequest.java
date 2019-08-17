package com.juanmcardenas.auth.network;

import android.app.Activity;
import android.location.Location;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.juanmcardenas.auth.location.GpsManager;
import com.juanmcardenas.auth.network.models.GeoNamesResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Martin Cardenas on 2019-08-16.
 */
public class DateRequest {

    private static final String TAG = DateRequest.class.getSimpleName();

    private static final String DATE_REQUEST_URL = "http://api.geonames.org/timezoneJSON?formatted=true&lat={DeviceLatitude}&lng={DeviceLongitude}&username=qa_mobile_easy&style=full";
    private static final String DATE_FORMAT = "YYYY-MM-dd hh:mm z";

    public void get(Activity activity, MutableLiveData<Date> dateLiveData) {

        // Get Latitude and Longitude
        GpsManager gpsManager = new GpsManager();
        Location location = gpsManager.get(activity);
        if (location == null) {
            callOnError(dateLiveData);
            return;
        }
        String url = DATE_REQUEST_URL.replace("{DeviceLatitude}", String.valueOf(location.getLatitude()))
                .replace("{DeviceLongitude}", String.valueOf(location.getLongitude()));
        // Prepare Request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {
                GeoNamesResponse geoNamesResponse = new Gson().fromJson(response, GeoNamesResponse.class);
                if (geoNamesResponse != null && geoNamesResponse.getTime() != null && geoNamesResponse.getCountryCode() != null) {
                    SimpleDateFormat sdf =  new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
                    Date date = sdf.parse(geoNamesResponse.getTime() + " GMT" + geoNamesResponse.getGmtOffset());
                    if (date != null && dateLiveData != null) {
                        dateLiveData.postValue(date);
                    }
                }
            } catch (Exception e) {
                callOnError(dateLiveData);
            }
        }, error -> {
            callOnError(dateLiveData);
        });

        // Enque request
        VolleySingleton.getInstance(activity.getBaseContext()).addToRequestQueue(stringRequest);
    }

    private void callOnError(MutableLiveData<Date> isSuccessLiveData) {
        if (isSuccessLiveData != null) {
            isSuccessLiveData.postValue(null);
        }
    }
}
