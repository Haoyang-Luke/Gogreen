package com.example.gogreen.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.gogreen.R;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.maps.android.SphericalUtil;

import java.util.Arrays;
import java.util.List;

public class MapsActivity extends AppCompatActivity
   implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private AppBarConfiguration mAppBarConfiguration;
    SupportMapFragment supportMapFragment;

    private GoogleMap mMap;



    private Polyline polyline;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int MY_PERMISSION_REQUEST_CODE = 2111;

    private LocationRequest mlocationRequest;
    private Location mLastLocation;

    private static int UPDATE_INTERVAL = 5000;
    private static int FASTEST_INTERVAL = 3000;
    private static int DISPLACEMENT = 10;

    private LocationCallback locationCallback;
    PlacesClient placesClient;

//    DatabaseReference ref;
 //   GeoFire geoFire;

    Marker mUserMarker, markerDestination;


    AutocompleteSupportFragment place_location, place_destination;
    List<Place.Field> placeFields = Arrays.asList(Place.Field.ADDRESS, Place.Field.NAME,
            Place.Field.ID, Place.Field.LAT_LNG);

    String mPlaceLocation, mPlaceDestination;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homemap);

        Places.initialize(getApplicationContext(), "AIzaSyDh-YNf3xdya0x9zu8-XG6tcFny4gCDBOk");
        placesClient = Places.createClient(this);
        setUpPlacesAutoComplete();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


/*
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
*/
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        buildLocationCallBack();

        fusedLocationProviderClient.requestLocationUpdates(mlocationRequest, locationCallback, Looper.myLooper());
        getLocation();
        Snackbar.make(supportMapFragment.getView(), "You are online", Snackbar.LENGTH_SHORT)
                .show();

        setUpLocation();


    }
    private void buildLocationCallBack() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    mLastLocation = location;

                }
                getLocation();
            }
        };
    }
    private void setUpPlacesAutoComplete() {

        //initialize the AutocompleteSupportFragment

        place_location = (AutocompleteSupportFragment) getSupportFragmentManager()
                .findFragmentById(R.id.place_location);

        place_location.setPlaceFields(placeFields);
        place_location.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                mPlaceLocation = place.getAddress();
                //Remove old marker
                mMap.clear();

                //add marker at new location

                mUserMarker = mMap.addMarker(new MarkerOptions().position(place.getLatLng())
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                        .title("You"));

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 15.0f));
            }

            @Override
            public void onError(@NonNull Status status) {

            }
        });

        place_destination = (AutocompleteSupportFragment) getSupportFragmentManager()
                .findFragmentById(R.id.place_destination);
        place_destination.setPlaceFields(placeFields);
        place_destination.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                mPlaceDestination = place.getAddress();
                mMap.addMarker(new MarkerOptions().position(place.getLatLng())
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.destination_marker))
                        .title("Destination"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 15.0f));

                //show info in bottom
                BottomSheetRiderFragment mBottomSheet = (BottomSheetRiderFragment) BottomSheetRiderFragment.newInstance(mPlaceLocation, mPlaceDestination);
                mBottomSheet.show(getSupportFragmentManager(), mBottomSheet.getTag());




            }

            @Override
            public void onError(@NonNull Status status) {

            }
        });


    }
    private void setUpLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_REQUEST_CODE);
        } else {
            buildLocationRequest();
            buildLocationCallBack();
            getLocation();

        }



    }
    private void buildLocationRequest() {
        mlocationRequest = new LocationRequest();
        mlocationRequest.setInterval(UPDATE_INTERVAL);
        mlocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mlocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mlocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                mLastLocation = location;



                if (mLastLocation != null) {
                    final double latitude = mLastLocation.getLatitude();
                    final double longitude = mLastLocation.getLongitude();

                    LatLng center = new LatLng(latitude, longitude);
                    LatLng northSide = SphericalUtil.computeOffset(center, 100000, 0);
                    LatLng southSide = SphericalUtil.computeOffset(center, 100000, 180);

                    LatLng current = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());

                    mMap.clear();
                    mUserMarker = mMap.addMarker(new MarkerOptions()
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                            .position(current)
                            .title("You"));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current, 15.0f));
                }
            }

        });

    }
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        //   mMap.setInfoWindowAdapter(new CustomInfoWindow(this));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                //first, check markDestination
                //if is not null, just remove available marker

                if (markerDestination != null)
                    markerDestination.remove();

                markerDestination = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.destination_marker))
                        .position(latLng)
                        .title("Destination"));

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));


                BottomSheetRiderFragment mBottomSheet = (BottomSheetRiderFragment) BottomSheetRiderFragment
                        .newInstance(String.format("%s,%s", mLastLocation.getLatitude(), mLastLocation.getLongitude()),
                                String.format("%s,%s", latLng.latitude, latLng.longitude));
                mBottomSheet.show(getSupportFragmentManager(), mBottomSheet.getTag());


            }
        });

        mMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    buildLocationCallBack();
                    buildLocationRequest();
                    getLocation();

                }
                break;
        }
    }
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }*/
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }
}
