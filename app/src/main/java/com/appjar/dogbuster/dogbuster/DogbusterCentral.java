package com.appjar.dogbuster.dogbuster;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceReport;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;

public class DogbusterCentral extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {

        //_______create object
    private GoogleApiClient mGoogleApiClient;
    private final int PLACE_PICKER_REQUEST = 1;
    private String TAG = "place";

    private ProgressBar spinner;

    public static int downCount=0;

    public static double Lats[];
    public static double Longs[];
    public static String DLevel[];
    public static int Range[];

    public static double selectedLat,selectedLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogbuster_central);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent i=new Intent(getBaseContext(),DogbusterIntentService.class);
        startService(i);

        spinner=(ProgressBar)findViewById(R.id.progressDownload);
        spinner.setVisibility(View.GONE);

    }

        @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection failed", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //____________check for successfull result
        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                //_________case for placepicker
                case PLACE_PICKER_REQUEST:

                    //______create place object from the received intent.
                    Place place = PlacePicker.getPlace(data, this);

                    //______get place name from place object
                    String toastMsg = String.format("Place: %s,%s", place.getLatLng().latitude,place.getLatLng().longitude);

                    selectedLat=place.getLatLng().latitude;  selectedLong=place.getLatLng().longitude;

                    Intent i = new Intent(getBaseContext(), Uploader.class);
                    startActivity(i);

//                    String res="";
//                    ServerInterface SI=new ServerInterface(getBaseContext(), new AsyncResponse() {
//                        @Override
//                        public void processFinish(String output) {
//
//                                Toast.makeText(getBaseContext(), output,
//                                        Toast.LENGTH_LONG).show();
//
//                        }
//                    },4);
//
//                    SI.execute(String.valueOf(place.getLatLng().latitude),String.valueOf(place.getLatLng().longitude),"500","HIGH");

                    break;

            }

        }

    }

    @Override
    public void onBackPressed() {
        return;

        //moveTaskToBack(true);

//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dogbuster_central, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_addloc) {

            // Handle the camera action

            //________initialize google client api
            try
            {


                mGoogleApiClient = new GoogleApiClient
                        .Builder(this)
                        .addApi(Places.GEO_DATA_API)
                        .addApi(Places.PLACE_DETECTION_API)
                        .enableAutoManage(this, this)
                        .build();

                //___________create object of placepicker builder
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    //__________start placepicker activity for result
                    startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
            catch(Exception e)
            {

                mGoogleApiClient.stopAutoManage(this);
                mGoogleApiClient.disconnect();

                mGoogleApiClient = new GoogleApiClient
                        .Builder(this)
                        .addApi(Places.GEO_DATA_API)
                        .addApi(Places.PLACE_DETECTION_API)
                        .enableAutoManage(this, this)
                        .build();

                //___________create object of placepicker builder
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    //__________start placepicker activity for result
                    startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException ex) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException ex) {
                    e.printStackTrace();
                }

               // Toast.makeText(getBaseContext(), "Please try again...",
               //                        Toast.LENGTH_LONG).show();
            }


        } else if (id == R.id.nav_viewactivity) {

            spinner.setVisibility(View.VISIBLE);

            String res="";
            ServerInterface SI=new ServerInterface(getBaseContext(), new AsyncResponse() {
                @Override
                public void processFinish(String output) {

                    //Log.d(output.toString(),"TEST OUT");
                    downCount=Integer.parseInt(output);
                    Lats=new double[downCount];
                    Longs=new double[downCount];
                    DLevel=new String[downCount];
                    Range=new int[downCount];
                }
            },2);

            SI.execute();

            SI=new ServerInterface(getBaseContext(), new AsyncResponse() {
                @Override
                public void processFinish(String output) {

                    Log.d(output,"TEST OUT");

                    for(int i=0;i<downCount;i++)
                    {
                        String temp=output.substring(0,output.indexOf('#'));

                        if(i<downCount-1)
                            output=output.substring(output.indexOf('#')+1);

                        Log.d(temp + " " + output,"TEMP AND OUT");

                        Lats[i]=Double.parseDouble(temp.substring(0,temp.indexOf('%')));
                        temp=temp.substring(temp.indexOf('%')+1);

                        Log.d(String.valueOf(Lats[i]) + " " + temp,"TEMP LATS AND TEMP");

                        Longs[i]=Double.parseDouble(temp.substring(0,temp.indexOf('%')));
                        temp=temp.substring(temp.indexOf('%')+1);

                        Log.d(String.valueOf(Longs[i])+ " " + temp,"TEMP LONGS AND TEMP");

                        Range[i]=Integer.parseInt(temp.substring(0,temp.indexOf('%')));
                        temp=temp.substring(temp.indexOf('%')+1);

                        Log.d(String.valueOf(Range[i])+ " " + temp,"RANGE AND TEMP");

                        DLevel[i]=temp;

                    }

                    Intent i = new Intent(getBaseContext(), DogsActivity.class);
                    startActivity(i);

                    spinner.setVisibility(View.GONE);

                }
            },3);

            SI.execute();

        }
        else if(id==R.id.nav_send)
        {
            Uri uri = Uri.parse("https://goo.gl/forms/p2JwriG2QpvQ3GAr1"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

