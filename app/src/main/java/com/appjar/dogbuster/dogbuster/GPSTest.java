package com.appjar.dogbuster.dogbuster;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;


interface AsyncResponse {
    void processFinish(String output);
}


public class GPSTest extends AppCompatActivity implements LocationListener{

    LocationManager locationManager;
    String mprovider;

    private int mInterval = 5000; // 5 seconds by default, can be changed later
    private Handler mHandler;

    TextView longitude,latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpstest);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        mprovider = locationManager.getBestProvider(criteria, false);

        if (mprovider != null && !mprovider.equals("")) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Location location = locationManager.getLastKnownLocation(mprovider);
            locationManager.requestLocationUpdates(mprovider, 15000, 1, this);

            if (location != null)
                onLocationChanged(location);
            else
                Toast.makeText(getBaseContext(), "No Location Provider Found!", Toast.LENGTH_SHORT).show();
        }

        mHandler = new Handler();
        startRepeatingTask();
    }

    @Override
    public void onLocationChanged(Location location) {

        Toast.makeText(getBaseContext(), "Location changed", Toast.LENGTH_SHORT).show();

        longitude = (TextView) findViewById(R.id.txtLong);
        latitude = (TextView) findViewById(R.id.txtLat);

        longitude.setText(String.valueOf(location.getLongitude()));
        latitude.setText(String.valueOf(location.getLatitude()));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                checkForWarning();
            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };

    void checkForWarning()
    {
        String res="";
        ServerInterface SI=new ServerInterface(getBaseContext(), new AsyncResponse() {
            @Override
            public void processFinish(String output) {

                Toast.makeText(getBaseContext(), output,
                            Toast.LENGTH_LONG).show();

            }
        },0);

        SI.execute(latitude.getText().toString(),longitude.getText().toString());
    }

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
    }
}
