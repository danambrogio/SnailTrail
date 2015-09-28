package com.ambrogio.dan.snailtrail;

import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements OnStreetViewPanoramaReadyCallback, OnMapReadyCallback, StreetViewPanorama.OnStreetViewPanoramaChangeListener {

    private LatLng current;
    private ArrayList<LatLng> locations;
    private final int[] colours = {Color.GREEN, Color.RED, Color.BLUE, Color.BLACK};
    private int currentColour;
    private Polyline trail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        currentColour = 0;

        // Get current location
        try {
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, true);
            if (provider != null){
                Location location = locationManager.getLastKnownLocation(provider);
                current = new LatLng(location.getLatitude(), location.getLongitude());
            }
            else {
                current = new LatLng(-33.87365, 151.20689);
            }
        }
        catch (SecurityException e){
            current = new LatLng(-33.87365, 151.20689);
        }
        catch (Exception e){
            current = new LatLng(-33.87365, 151.20689);
        }
        locations = new ArrayList<>();
        locations.add(current);

        StreetViewPanoramaFragment streetViewPanoramaFragment =
                (StreetViewPanoramaFragment) getFragmentManager()
                        .findFragmentById(R.id.panorama);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            currentColour = (currentColour + 1) % 3;
            drawTrail();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
        panorama.setPosition(current);
        panorama.setOnStreetViewPanoramaChangeListener(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions()
                .position(current)
                .title("Start"));
        CameraPosition pos = new CameraPosition.Builder()
                .target(current)
                .zoom(15)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(pos));
    }

    @Override
    public void onStreetViewPanoramaChange(StreetViewPanoramaLocation streetViewPanoramaLocation) {
        current = streetViewPanoramaLocation.position;
        if (current != null) {
            locations.add(current);
        }

        drawTrail();
    }

    public void drawTrail(){
        // Draw a line on the map
        if (locations.size() > 1) {
            MapFragment mapFragment = (MapFragment) getFragmentManager()
                    .findFragmentById(R.id.map);
            if (trail != null) {
                trail.remove();
            }
            this.trail = mapFragment.getMap().addPolyline(new PolylineOptions().addAll(locations).color(colours[currentColour]));
        }
    }
}
