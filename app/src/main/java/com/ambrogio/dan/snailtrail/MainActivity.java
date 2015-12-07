package com.ambrogio.dan.snailtrail;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements OnStreetViewPanoramaReadyCallback, OnMapReadyCallback, StreetViewPanorama.OnStreetViewPanoramaChangeListener {

    private static final LatLng DEFAULT_LOCATION = new LatLng(-33.87365, 151.20689);
    private LatLng current;                 //current location
    private ArrayList<LatLng> locations;    //where the player has been
    private ArrayList<LatLng> markers;      //markers on the map
    private final int[] colours = {Color.GREEN, Color.RED, Color.BLUE, Color.BLACK};
    private int currentColour;
    private Polyline trail;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView scoreText = (TextView) findViewById(R.id.scoreText);
        scoreText.setText(String.format(getString(R.string.score), score));

        currentColour = 0;

        // Get current location
        try {
            double lat = getIntent().getDoubleExtra("lat", -33.87365);
            double lng = getIntent().getDoubleExtra("lng", 151.20689);
            current = new LatLng(lat, lng);
            if(current == null){
                current = DEFAULT_LOCATION;
            }
        }
        catch (SecurityException e){
            current = DEFAULT_LOCATION;
        }
        catch (Exception e){
            //TODO: Remove catching generic exceptions
            current = DEFAULT_LOCATION;
        }
        locations = new ArrayList<>();
        locations.add(current);

        markers = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            double lat = getIntent().getDoubleExtra("marker" + i + "lat", 0);
            double lng = getIntent().getDoubleExtra("marker" + i + "lng", 0);
            LatLng marker = new LatLng(lat, lng);
            markers.add(marker);
        }

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
        centerMap(map);
    }

    @Override
    public void onStreetViewPanoramaChange(StreetViewPanoramaLocation streetViewPanoramaLocation) {

        current = streetViewPanoramaLocation.position;
        if (current != null) {
            locations.add(current);
        }

        // Check if close to a coin
        float[] distance = new float[1];
        for (LatLng coin : markers) {
            if (current != null) {
                Location.distanceBetween(current.latitude, current.longitude, coin.latitude, coin.longitude, distance);
                if (distance[0] < 20) {
                    markers.remove(coin);
                    score++;
                    TextView scoreText = (TextView) findViewById(R.id.scoreText);
                    scoreText.setText(String.format(getString(R.string.score), score));
                    break;
                }
            }
        }

        drawTrail();
    }

    public void drawTrail(){
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        GoogleMap map = mapFragment.getMap();

        // Move the pin to the current location)
        map.clear();
        map.addMarker(new MarkerOptions()
                .position(current)
                .title("Snail")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.snail)));
        addMarkers(map);

        // Draw a line on the map
        if (locations.size() > 1) {
            if (trail != null) {
                trail.remove();
            }
            this.trail = map.addPolyline(new PolylineOptions().addAll(locations).color(colours[currentColour]));
        }
        centerMap(map);
    }

    public void centerMap(GoogleMap map){
        CameraPosition pos = new CameraPosition.Builder()
                .target(current)
                .zoom(15)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(pos));
    }

    public void addMarkers(GoogleMap map){
        for (LatLng loc : markers) {
            Marker m = map.addMarker(new MarkerOptions().position(loc).title("1 Pt"));
        }
    }
}
