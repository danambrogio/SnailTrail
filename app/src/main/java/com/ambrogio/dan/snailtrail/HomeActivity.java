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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    Button location1;
    Button location2;
    Button location3;
    Button currentLocation;
    Context context;
    LatLng current;

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

                        Location location = locationManager.getLastKnownLocation(provider);
                        current = new LatLng(location.getLatitude(), location.getLongitude());
                        lat = current.latitude;
                        lng = current.longitude;
                    }

                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    intent.putExtra("lat", lat);
                    intent.putExtra("lng", lng);
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
