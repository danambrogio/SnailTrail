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
        location1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               /* Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("location", "100 City Centre Dr, Mississauga, ON L5B 2C9");
                startActivity(intent);*/
                Geocoder gc = new Geocoder(context);


                try {
                    List<Address> list = gc.getFromLocationName("L5B 2C9", 1);
                    Address address = list.get(0);
                    String locality = address.getLocality();
                    Toast.makeText(context, locality, Toast.LENGTH_LONG).show();
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
               /* Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("location", "100 City Centre Dr, Mississauga, ON L5B 2C9");
                startActivity(intent);*/
                Geocoder gc = new Geocoder(context);


                try {
                    List<Address> list = gc.getFromLocationName("M5B 2R8", 1);
                    Address address = list.get(0);
                    String locality = address.getLocality();
                    Toast.makeText(context, locality, Toast.LENGTH_LONG).show();
                   /* double lat = address.getLatitude();
                    double lng = address.getLongitude();
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    intent.putExtra("lat", lat);
                    intent.putExtra("lng", lng);
                    startActivity(intent);*/
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
        location3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               /* Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("location", "100 City Centre Dr, Mississauga, ON L5B 2C9");
                startActivity(intent);*/
                Geocoder gc = new Geocoder(context);


                try {
                    List<Address> list = gc.getFromLocationName("Eiffel Tower", 1);
                    Address address = list.get(0);
                    String locality = address.getLocality();
                    Toast.makeText(context, locality, Toast.LENGTH_LONG).show();
                    /*double lat = address.getLatitude();
                    double lng = address.getLongitude();
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    intent.putExtra("lat", lat);
                    intent.putExtra("lng", lng);
                    startActivity(intent);*/
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
        currentLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               /* Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("location", "100 City Centre Dr, Mississauga, ON L5B 2C9");
                startActivity(intent);*/
                Geocoder gc = new Geocoder(context);
                double lat = 0;
                double lng = 0;



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
                    startActivity(intent);



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
