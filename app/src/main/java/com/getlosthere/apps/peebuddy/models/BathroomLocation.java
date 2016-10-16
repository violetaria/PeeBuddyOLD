package com.getlosthere.apps.peebuddy.models;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by violetaria on 10/6/16.
 */
public class BathroomLocation {
    private String name;
    private Double lat;
    private Double lng;
    private Double overallRating;
    private int ratingCount;
    private Double myRating;

    public int getRatingCount() {
        return ratingCount;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public Double getMyRating() {
        return myRating;
    }

    public String getName() {
        return name;
    }

    public LatLng getLatLngPoint() {
        LatLng latLng = new LatLng(this.lat, this.lng);
        return latLng;
    }

    public Double getOverallRating() {
        return overallRating;
    }

    public BathroomLocation() {  }

    public static BathroomLocation fromJSONObject(JSONObject jsonObject){
        BathroomLocation bathroomLocation = new BathroomLocation();
        try {
            bathroomLocation.name = jsonObject.getString("name");
            JSONObject latLngJSONObject = jsonObject.getJSONObject("latlng");
            bathroomLocation.lat = latLngJSONObject.getDouble("lat");
            bathroomLocation.lng = latLngJSONObject.getDouble("lng");
            bathroomLocation.overallRating = jsonObject.getDouble("rating");
            bathroomLocation.ratingCount = jsonObject.getInt("rating_count");
        } catch (JSONException e){
            e.printStackTrace();
        }
        return bathroomLocation;
    }

    public static ArrayList<BathroomLocation> fromJSONArray(JSONArray jsonArray){
        ArrayList<BathroomLocation> bathroomLocations = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++){
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                BathroomLocation bathroomLocation = BathroomLocation.fromJSONObject(jsonObject);
                bathroomLocations.add(bathroomLocation);
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }
        return bathroomLocations;
    }
}
