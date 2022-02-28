package com.parkinapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.navigation.NavigationBarView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.parkinapp.HelperClasses.adapterphone;
import com.parkinapp.HelperClasses.phonehelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dev.b3nedikt.restring.Restring;

public class Explore extends AppCompatActivity implements OnMapReadyCallback, adapterphone.ListItemClickListener {

    private static final String TAG = Explore.class.getName();
    boolean isPermissionGranted;
    GoogleMap mGoogleMap;
    SearchView searchView;
    String veh;
    private LocationRequest locationRequest;
    private static final int REQUEST_CODE = 101;
    FusedLocationProviderClient fusedLocationProviderClient;
    Location currentLocation;

    RecyclerView phoneRecycler2;
    RecyclerView.Adapter adapter;


    LatLng VSIT = new LatLng(19.0213822,72.8707494);
    LatLng SionHospital = new LatLng(19.0238592,72.8825719);
    LatLng MatungaStation = new LatLng(19.03086695630095, 72.8512965672522);
    LatLng KohinoorSquare = new LatLng(19.024923,72.839316);
    LatLng Chembur = new LatLng(19.055887184156767, 72.88338121558216);
    LatLng McgmParking = new LatLng(18.993082500000156, 72.84830980411726);


    private ArrayList<LatLng> locationArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        checkMyPermission();

        statusCheck();
//Bottom card************************************************************************


        locationArrayList = new ArrayList<>();

        // on below line we are adding our
        // locations in our array list.
        locationArrayList.add(VSIT);
        locationArrayList.add(SionHospital);
        locationArrayList.add(MatungaStation);
        locationArrayList.add(KohinoorSquare);
        locationArrayList.add(Chembur);
        locationArrayList.add(McgmParking);

//ON open DAILOG************************************************************************

        TextView vehicle = (TextView) findViewById(R.id.vehicle1);
        vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(Explore.this);
                dialog.setContentView(R.layout.dialog);
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setTitle("Dialog box");
                TextView txtView2;


                Button button = (Button) dialog.findViewById(R.id.btnSelect);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView txtView = (TextView) findViewById(R.id.vehicle1);
                        txtView.setText("Car");
                        veh = "car";
                        dialog.dismiss();
                    }
                });

                Button button2 = (Button) dialog.findViewById(R.id.btnSelect2);
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView txtView = (TextView) findViewById(R.id.vehicle1);
                        txtView.setText("Bike");
                        veh = "Bike";
                        dialog.dismiss();
                    }
                });

                Button button3 = (Button) dialog.findViewById(R.id.btnSelect3);
                button3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView txtView = (TextView) findViewById(R.id.vehicle1);
                        txtView.setText("Ebike");
                        veh = "Ebike";
                        dialog.dismiss();
                    }
                });

                Button button4 = (Button) dialog.findViewById(R.id.btnSelect4);
                button4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView txtView = (TextView) findViewById(R.id.vehicle1);
                        txtView.setText("Ecar");
                        veh = "Ecar";
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });



        ImageButton current = (ImageButton) findViewById(R.id.currentLocation);
        current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(Explore.this, veh, Toast.LENGTH_SHORT);
                toast.show();
            }
        });


        searchView = (SearchView) findViewById(R.id.svlocation);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mGoogleMap.clear();
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;
                if (location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(Explore.this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    mGoogleMap.addMarker(new MarkerOptions().position(latLng).title(location));
                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        phoneRecycler2 = findViewById(R.id.my_recycler);
        phoneRecycler2();

        Button book = (Button) findViewById(R.id.book);


        if (isPermissionGranted) {
            SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
            supportMapFragment.getMapAsync(this);
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        MenuItem item = navigation.getMenu().findItem(R.id.nav_book);
        item.setCheckable(true);
        item.setChecked(true);
        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_book:
                        break;
                    case R.id.nav_home:
                        Intent a = new Intent(Explore.this, Home.class);
                        startActivity(a);
                        break;
                    case R.id.nav_history:
                        Intent b = new Intent(Explore.this, History.class);
                        startActivity(b);
                        break;
                    case R.id.nav_profile:
                        Intent c = new Intent(Explore.this, Profile.class);
                        startActivity(c);
                        break;
                }
                return false;
            }
        });
    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }


    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled,Please enable your GPS for better experience!")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    private void phoneRecycler2() {

        //All Gradients
        GradientDrawable gradient2 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffffff, 0xffffff});
        GradientDrawable gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffffff, 0xffffff});
        GradientDrawable gradient3 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffffff, 0xffffff});
        GradientDrawable gradient4 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffffff, 0xffffff});


        phoneRecycler2.setHasFixedSize(true);
        phoneRecycler2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));



        ArrayList<phonehelper> phonelocations = new ArrayList<>();
        phonelocations.add(new phonehelper(gradient1, R.drawable.common_full_open_on_phone, "VSIT college", "30/hr", "Charging Unavailable",getText(R.string.vsit).toString()));
        phonelocations.add(new phonehelper(gradient4, R.drawable.common_full_open_on_phone, "Matunga Station Parking", "40/hr", "Charging Unavailable", getText(R.string.matunga).toString()));
        phonelocations.add(new phonehelper(gradient2, R.drawable.common_full_open_on_phone, "Kohinoor Square", "30/hr", "Charging Available", getText(R.string.kohinoor).toString()));
        phonelocations.add(new phonehelper(gradient4, R.drawable.common_full_open_on_phone, "Sion Hospital","50/hr", "Charging Available", getText(R.string.sion).toString()));
        phonelocations.add(new phonehelper(gradient2, R.drawable.common_full_open_on_phone, "Mcgm Parking Sewri", "35/hr", "Charging Available" , getText(R.string.mcgm).toString()));


        adapter = new adapterphone(phonelocations, this);
        phoneRecycler2.setAdapter(adapter);


    }

    public void onphoneListClick(int clickItemIndex) {


    }


    private void checkMyPermission() {

        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                isPermissionGranted = true;
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), "");
                intent.setData(uri);
                startActivity(intent);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mGoogleMap = googleMap;
        googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(this, R.raw.custommap)
        );

        for (int i = 0; i < locationArrayList.size(); i++) {

            // below line is use to add marker to each location of our array list.
            mGoogleMap.addMarker(new MarkerOptions().position(locationArrayList.get(i)).icon(BitmapDescriptorFactory.fromResource(R.drawable.parkpin)));

        }

        LatLng latLng = new LatLng(19.018950, 72.863543);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title("Me");
        markerOptions.position(latLng);
        markerOptions.icon((BitmapDescriptorFactory.fromResource(R.drawable.target)));
        googleMap.addMarker(markerOptions);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 13);
        googleMap.animateCamera(cameraUpdate);
    }

 }
