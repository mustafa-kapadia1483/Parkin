package com.parkinapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class History extends AppCompatActivity {

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    ListView listView;
    String phoneNumber;
    TextView tranId;

    List<Bookings> bookingsList=new ArrayList<>();
    Bookings bookings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        phoneNumber = fAuth.getCurrentUser().getPhoneNumber().substring(3,13);

        firebaseDatabase= FirebaseDatabase.getInstance("https://parkin-e5c4e-default-rtdb.firebaseio.com/");
        listView = (ListView) findViewById(R.id.listView);
        databaseReference=firebaseDatabase.getReference(phoneNumber);


        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Map<String,String> map=(Map<String, String>) snapshot.getValue();
                bookings = new Bookings();
                bookings.setParkName(map.get("parkName"));
                bookings.setParkAdd(map.get("parkAdd"));
                bookings.setVhRegNo(map.get("vhRegNo"));
                bookings.setStartTime(map.get("startTime"));
                bookings.setGivenDate(map.get("givenDate"));
                bookings.setNoHours(map.get("noHours"));
                bookings.setVhModel(map.get("vhModel"));
                bookings.setFinalAmount(map.get("finalAmount"));
                bookings.setStatus(map.get("status"));
                bookings.setTransId(map.get("transId"));
                bookings.setType(map.get("type"));

                bookingsList.add(bookings);

                MyAdapter myAdapter =new MyAdapter(History.this,bookingsList);
                listView.setAdapter(myAdapter);

            }


            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView tranId = view.findViewById(R.id.tranId);
                TextView parkNAme = view.findViewById(R.id.parkName);
                String str = tranId.getText().toString();
                String str2  = parkNAme.getText().toString();
                Intent ticket = new Intent(History.this, showBooking.class);
                ticket.putExtra("TranID", str);
                ticket.putExtra("ParkName", str2);
                startActivity(ticket);

            }
        });



//Bottom NAv****************************
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        MenuItem item = navigation.getMenu().findItem(R.id.nav_history);
        item.setCheckable(true);
        item.setChecked(true);
        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Intent a = new Intent(History.this, Home.class);
                        startActivity(a);
                        break;
                    case R.id.nav_book:
                        Intent b = new Intent(History.this, Explore.class);
                        startActivity(b);
                        break;
                    case R.id.nav_history:

                        break;
                    case R.id.nav_profile:
                        Intent c = new Intent(History.this, Profile.class);
                        startActivity(c);
                        break;
                }
                return false;
            }
        });

    }

    //Adapter*************************



    public class MyAdapter extends BaseAdapter{

        Context context;
        List<Bookings> stringList;
        TextView parkName,startTime,endTime,givenDate,noHours,vhRegNo, tranId, type;

        public MyAdapter(Context context, List<Bookings> stringList) {
            this.context = context;
            this.stringList = stringList;
        }

        @Override
        public int getCount() {
            return stringList.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view= LayoutInflater.from(context).inflate(R.layout.booking, viewGroup,false);


            parkName=view.findViewById(R.id.parkName);
            vhRegNo=view.findViewById(R.id.vhRegNo);
            startTime=view.findViewById(R.id.startTime);
            givenDate=view.findViewById(R.id.givenDate);
            noHours=view.findViewById(R.id.noHours);
            tranId = view.findViewById(R.id.tranId);



            parkName.setText(stringList.get(i).getParkName());
            vhRegNo.setText("("+stringList.get(i).getVhRegNo()+")");
            startTime.setText(stringList.get(i).getStartTime());
            givenDate.setText(stringList.get(i).getGivenDate());
            noHours.setText(stringList.get(i).getNoHours() + "hrs");
            tranId.setText(stringList.get(i).getTransId());



            return view;
        }
    }

}