package com.appjar.dogbuster.dogbuster;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DogsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogs);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);
        LatLng kerala = new LatLng(10.12, 76.32);

//        displayCircle(kerala,1000,"HIGH");
//        displayCircle(new LatLng(10.13,76.33),1000,"MEDIUM");
//        displayCircle(new LatLng(10.14,76.34),1000,"LOW");

        for(int i=0;i<DogbusterCentral.downCount;i++)
        {
            displayCircle(new LatLng(DogbusterCentral.Lats[i],DogbusterCentral.Longs[i]),DogbusterCentral.Range[i],DogbusterCentral.DLevel[i]);
            Log.d(String.valueOf(DogbusterCentral.Lats[i])+"  " + String.valueOf(DogbusterCentral.Longs[i]),"LATLONG TEST");
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(kerala));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(8.0f ));
    }

    public void displayCircle(LatLng var_latlng, int rad, String msg)
    {

        BitmapDescriptor marker_red
                = BitmapDescriptorFactory.defaultMarker(
                BitmapDescriptorFactory.HUE_RED);
        BitmapDescriptor marker_orange
                = BitmapDescriptorFactory.defaultMarker(
                BitmapDescriptorFactory.HUE_ORANGE);
        BitmapDescriptor marker_blue
                = BitmapDescriptorFactory.defaultMarker(
                BitmapDescriptorFactory.HUE_BLUE);

        if(msg.equals("HIGH")) {
            Circle circle = mMap.addCircle(new CircleOptions()
                    .center(var_latlng)
                    .radius(rad)
                    .strokeColor(android.R.color.white)
                    .fillColor(0x66FF0000).strokeWidth(1));

            mMap.addMarker(new MarkerOptions().position(var_latlng)
                    .title(msg).icon(marker_red));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(var_latlng));
        }

        else if(msg.equals("MEDIUM")) {
            Circle circle = mMap.addCircle(new CircleOptions()
                    .center(var_latlng)
                    .radius(rad)
                    .strokeColor(android.R.color.white)
                    .fillColor(0x44FF8C00).strokeWidth(1));

            mMap.addMarker(new MarkerOptions().position(var_latlng)
                    .title(msg).icon(marker_orange));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(var_latlng));
        }

        else if(msg.equals("LOW")) {
            Circle circle = mMap.addCircle(new CircleOptions()
                    .center(var_latlng)
                    .radius(rad)
                    .strokeColor(android.R.color.white)
                    .fillColor(0x330000FF).strokeWidth(1));

            mMap.addMarker(new MarkerOptions().position(var_latlng)
                    .title(msg).icon(marker_blue));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(var_latlng));
        }
    }
}
