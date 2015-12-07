package com.ambrogio.dan.snailtrail;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HomeActivity extends AppCompatActivity {

    Button location1;
    Button location2;
    Button location3;
    Button currentLocation;
    Context context;
    LatLng current;

    final static int NUM_MARKERS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = this;
        location1 = (Button) findViewById(R.id.location1Btn);
        location2 = (Button) findViewById(R.id.location2Btn);
        location3 = (Button) findViewById(R.id.location3Btn);
        currentLocation = (Button) findViewById(R.id.currentLocationBtn);
        location3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Geocoder gc = new Geocoder(context);

                try {
                    List<Address> list = gc.getFromLocationName("Spain, Madrid", 1);
                    Address address = list.get(0);
                    String locality = address.getLocality();

                    double lat = address.getLatitude();
                    double lng = address.getLongitude();
                    Toast.makeText(context, "Welcome to the Madrid, Spain level.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    intent.putExtra("lat", lat);
                    intent.putExtra("lng", lng);

                    /* Set up markers */
                    /*"Casa Museo Lope de Vega, Madrid, Spain",*/
                    String[] markerLocations = {"Calle de Moratín, 7, 28014 Madrid, Spain",
                            "Calle de las Infantas, 2-4, 28004 Madrid, Spain",
                            "ME Madrid Reina Victoria, Madrid, Spain"};
                    for (int i = 0; i < markerLocations.length; i++) {
                        list = gc.getFromLocationName(markerLocations[i], 1);
                        address = list.get(0);
                        lat = address.getLatitude();
                        lng = address.getLongitude();
                        intent.putExtra("marker" + i + "lat", lat);
                        intent.putExtra("marker" + i + "lng", lng);
                    }

                    intent.putExtra("premade", true);

                    startActivity(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
        location1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Geocoder gc = new Geocoder(context);


                try {
                    List<Address> list = gc.getFromLocationName("Ryerson University", 1);
                    Address address = list.get(0);
                    String locality = address.getLocality();
                    Toast.makeText(context, "Welcome to the Ryerson University - Toronto level.", Toast.LENGTH_LONG).show();
                    double lat = address.getLatitude();
                    double lng = address.getLongitude();
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    intent.putExtra("lat", lat);
                    intent.putExtra("lng", lng);

                    /* Set up markers */
                    String[] markerLocations = {"Jarvis Street Baptist Church, Toronto, ON",
                            "505 Victoria St Ln, Toronto, ON M5B",
                            "119 Mutual St, Toronto, ON M5B 2B2"};
                    for (int i = 0; i < markerLocations.length; i++) {
                        list = gc.getFromLocationName(markerLocations[i], 1);
                        address = list.get(0);
                        lat = address.getLatitude();
                        lng = address.getLongitude();
                        intent.putExtra("marker" + i + "lat", lat);
                        intent.putExtra("marker" + i + "lng", lng);
                    }

                    intent.putExtra("premade", true);

                    startActivity(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
        location2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Geocoder gc = new Geocoder(context);


                try {
                    List<Address> list = gc.getFromLocationName("London, England", 1);
                    Address address = list.get(0);
                    String locality = address.getLocality();
                    Toast.makeText(context, "Welcome to the London, England level.", Toast.LENGTH_LONG).show();
                    double lat = address.getLatitude();
                    double lng = address.getLongitude();
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    intent.putExtra("lat", lat);
                    intent.putExtra("lng", lng);

                    /* Set up markers */
                    String[] markerLocations = {"8 York Buildings, London WC2N 6JN, UK",
                            "Theatre Royal Haymarket, London",
                            "Garrick Theatre, Charing Cross Road, London, United Kingdom"};
                    for (int i = 0; i < markerLocations.length; i++) {
                        list = gc.getFromLocationName(markerLocations[i], 1);
                        address = list.get(0);
                        lat = address.getLatitude();
                        lng = address.getLongitude();
                        intent.putExtra("marker" + i + "lat", lat);
                        intent.putExtra("marker" + i + "lng", lng);
                    }

                    intent.putExtra("premade", true);

                    startActivity(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        currentLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Geocoder gc = new Geocoder(context);
                double lat = 0;
                double lng = 0;

                try {
                    LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                    Criteria criteria = new Criteria();
                    String provider = locationManager.getBestProvider(criteria, true);
                    if (provider != null) {

                        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for Activity#requestPermissions for more details.
                            return;
                        }
                        Location location = locationManager.getLastKnownLocation(provider);
                        current = new LatLng(location.getLatitude(), location.getLongitude());
                        lat = current.latitude;
                        lng = current.longitude;
                    }

                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    intent.putExtra("lat", lat);
                    intent.putExtra("lng", lng);

                    /* Set up markers */
                    // We need to get 3 points near their location that are valid addresses
                    // step 1: create an arraylist of LatLng's
                    ArrayList<LatLng> markers = new ArrayList<>();

                    // step 2: while size < 3, generate a random LatLng near current and check if valid
                    // 5m = 0.0000449 lat/lng
                    // should be in range of +- 200m
                    // 40 * 0.0000449
                    final double RANGE = 40 * 0.0000449;
                    Random r = new Random();
                    while (markers.size() < NUM_MARKERS){
                        double latitude = current.latitude + ((-RANGE) + (RANGE - (-RANGE)) * r.nextDouble());
                        double longitude = current.longitude + ((-RANGE) + (RANGE - (-RANGE)) * r.nextDouble());
                        List<Address> loc = gc.getFromLocation(latitude, longitude, 1);
                        if (loc != null && !loc.isEmpty()){
                            markers.add(new LatLng(latitude, longitude));
                        }
                    }

                    for (int i = 0; i < NUM_MARKERS; i++) {
                        intent.putExtra("marker" + i + "lat", markers.get(i).latitude);
                        intent.putExtra("marker" + i + "lng", markers.get(i).longitude);
                    }

                    intent.putExtra("premade", false);

                    startActivity(intent);
                }catch(Exception e){

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
}
