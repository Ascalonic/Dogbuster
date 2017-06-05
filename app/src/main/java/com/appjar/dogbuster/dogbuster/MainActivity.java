package com.appjar.dogbuster.dogbuster;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceReport;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;

public class MainActivity extends AppCompatActivity
       //  implements GoogleApiClient.OnConnectionFailedListener
{

//    //_______create object
//    private GoogleApiClient mGoogleApiClient;
//    private final int PLACE_PICKER_REQUEST = 1;
//    private String TAG = "place";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent i = new Intent(getBaseContext(), GPSTest.class);
//        startActivity(i);

//        //________initialize google client api
//        mGoogleApiClient = new GoogleApiClient
//                .Builder(this)
//                .addApi(Places.GEO_DATA_API)
//                .addApi(Places.PLACE_DETECTION_API)
//                .enableAutoManage(this, this)
//                .build();

//        //___________create object of placepicker builder
//        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
//
//        try {
//            //__________start placepicker activity for result
//            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
//        } catch (GooglePlayServicesRepairableException e) {
//            e.printStackTrace();
//        } catch (GooglePlayServicesNotAvailableException e) {
//            e.printStackTrace();
//      }

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                //start your activity here
                Intent intent = new Intent(getBaseContext(), DogbusterLogin.class);
                startActivity(intent);

                finish();
            }

        }, 2000L);

    }

//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        Toast.makeText(this, "Connection failed", Toast.LENGTH_SHORT).show();
//    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        //____________check for successfull result
//        if (resultCode == RESULT_OK) {
//
//            switch (requestCode) {
//
//                //_________case for placepicker
//                case PLACE_PICKER_REQUEST:
//
//                    //______create place object from the received intent.
//                    Place place = PlacePicker.getPlace(data, this);
//
//                    //______get place name from place object
//                    String toastMsg = String.format("Place: %s", place.getName());
//
//                    //_________show toast message for selected place
//                    Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
//
//                    break;
//
//            }
//
//        }
//
//    }
}
