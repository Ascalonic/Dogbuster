package com.appjar.dogbuster.dogbuster;

/**
 * Created by HP on 06-04-2017.
 */

import android.Manifest;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;

public class DogbusterIntentService extends IntentService  implements LocationListener {

    LocationManager locationManager;
    String mprovider;

    private int mInterval = 5000; // 5 seconds by default, can be changed later
    private Handler mHandler;

    String latitude,longitude;

    public DogbusterIntentService()
    {
        this(DogbusterIntentService.class.getName());
    }

    public DogbusterIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //showToast("Starting IntentService");

        Log.d("POINT 1","CONN_TEST");

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        Log.d("POINT 2","CONN_TEST");

        mprovider = locationManager.getBestProvider(criteria, false);

        Log.d("POINT 3","CONN_TEST");

        if (mprovider != null && !mprovider.equals("")) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            Log.d("POINT 4","CONN_TEST");

            Location location = locationManager.getLastKnownLocation(mprovider);
            locationManager.requestLocationUpdates(mprovider, 15000, 1, this);

            Log.d("POINT 5","CONN_TEST");

            if (location != null)
                onLocationChanged(location);
            else
                Toast.makeText(getBaseContext(), "No Location Provider Found!", Toast.LENGTH_SHORT).show();

            Log.d("POINT 6","CONN_TEST");

            //requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            //requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        }

        Log.d("Starting IntentService","CONN_TEST");

        try {

            Log.d("POINT 7","CONN_TEST");

            checkForWarning();
            Thread.sleep(10000);

            Log.d("POINT 8","CONN_TEST");

            Intent i=new Intent(getBaseContext(),DogbusterIntentService.class);
            startService(i);

            Log.d("POINT 9","CONN_TEST");

        } catch (Exception e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }


        Log.d("Finishing IntentService","CONN_TEST");
    }

    private void addNotification(String msg) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.dog_icon)
                        .setContentTitle("Dog Activity Nearby")
                        .setContentText(msg)
                        .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                        .setLights(Color.WHITE,1000, 3000);


        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    protected void showToast(final String msg){
        //gets the main thread
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                // run this code in the main thread
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onLocationChanged(Location location) {

        Toast.makeText(getBaseContext(), "Location changed", Toast.LENGTH_SHORT).show();

        longitude=String.valueOf(location.getLongitude());
        latitude=String.valueOf(location.getLatitude());
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

                if(output.contains("LOW"))
                {
                    addNotification("You are in a LOW danger level stray dog activity zone!");
                    Toast.makeText(getBaseContext(), "You are in a LOW danger level stray dog activity zone!",
                            Toast.LENGTH_LONG).show();
                }
                else if(output.contains("MEDIUM"))
                {
                    addNotification("You are in a MEDIUM danger level stray dog activity zone!");
                    Toast.makeText(getBaseContext(), "You are in a MEDIUM danger level stray dog activity zone!",
                            Toast.LENGTH_LONG).show();
                }
                else if(output.contains("HIGH"))
                {
                    addNotification("You are in a HIGH danger level stray dog activity zone!");
                    Toast.makeText(getBaseContext(), "You are in a HIGH danger level stray dog activity zone! Please leave immediately!",
                            Toast.LENGTH_LONG).show();
                }

            }
        },0);

        while(true)
        {
            try
            {

                double lat=0.0,longi=0.0;

                GPSTracker gps = new GPSTracker(getApplicationContext());
                if(gps.canGetLocation()){

                    lat=gps.getLatitude();
                    longi=gps.getLongitude();

                    //Log.d("Lat" + SellerConsole.shop_lat,"TEST_LOC");
                    //Log.d("Longi" + SellerConsole.shop_longi,"TEST_LOC");

                }

                SI.execute(Double.toString(lat),Double.toString(longi));

                break;
            }
            catch(Exception ex)
            {
                //Log.d("Retrying...","CONN_TEST");
            }
        }

    }

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        try
        {
            mHandler.removeCallbacks(mStatusChecker);
        }
        catch(Exception ex)
        {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
    }
}
