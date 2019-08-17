package com.juanmcardenas.auth.network.models;

/**
 * Created by Martin Cardenas on 2019-08-16.
 */
public class GeoNamesResponse {

    private String sunrise;

    private Double lng;

    private String countryCode;

    private int gmtOffset;

    private int rawOffset;

    private String sunset;

    private String timezoneId;

    private int dstOffset;

    private String countryName;

    private String time;

    private Double lat;

    public GeoNamesResponse(String sunrise, Double lng, String countryCode, int gmtOffset, int rawOffset, String sunset, String timezoneId, int dstOffset, String countryName, String time, Double lat) {
        this.sunrise = sunrise;
        this.lng = lng;
        this.countryCode = countryCode;
        this.gmtOffset = gmtOffset;
        this.rawOffset = rawOffset;
        this.sunset = sunset;
        this.timezoneId = timezoneId;
        this.dstOffset = dstOffset;
        this.countryName = countryName;
        this.time = time;
        this.lat = lat;
    }

    public String getSunrise() {
        return sunrise;
    }

    public Double getLng() {
        return lng;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public int getGmtOffset() {
        return gmtOffset;
    }

    public int getRawOffset() {
        return rawOffset;
    }

    public String getSunset() {
        return sunset;
    }

    public String getTimezoneId() {
        return timezoneId;
    }

    public int getDstOffset() {
        return dstOffset;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getTime() {
        return time;
    }

    public Double getLat() {
        return lat;
    }
}
