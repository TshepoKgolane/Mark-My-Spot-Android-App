package com.example.markmyspot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class mapDisplay extends FragmentActivity implements OnMapReadyCallback {
    //
    GoogleMap map;
    Location currentLocation;
    Location Destination;
    FusedLocationProviderClient fusedLocationProviderClient;
    FirebaseAuth mAuth;
    SearchView searchview;
    private static final int REQUEST_CODE = 101;
    private List<Polyline> polylines=null;
    private ArrayList<Double> lats = new ArrayList<Double>();
    private ArrayList<Double> longs = new ArrayList<Double>();
    private ArrayList<String> names = new ArrayList<String>();
    MarkerOptions markerOptions3;
    private Marker marker;
    private String myLat;
    private String myLong;
    private LatLng goHere;
    //buttons
    Button btnSettings;
    Button btnFav;
    Button btnMenu;
    Button btnHome;
    Button btnGo;
    TextView markerTitle;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("FavLandMarks");

    MarkerOptions markerOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_map_display);
            btnSettings = findViewById(R.id.btnSettings);
            btnMenu = findViewById(R.id.btnMenu);
            btnFav = findViewById(R.id.btnFav);
            btnHome = findViewById(R.id.btnHome);
            btnGo = findViewById(R.id.btnGo);
            markerTitle = findViewById(R.id.markerTitle);
            searchview = findViewById(R.id.searchBar);

            searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    String location = searchview.getQuery().toString();
                    List<Address> addressList = null;
                    if (location !=null || !location.equals("")){
                        Geocoder geocoder = new Geocoder(mapDisplay.this);
                        try {
                            addressList = geocoder.getFromLocationName(location,1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Address address = addressList.get(0);
                        LatLng latlng = new LatLng(address.getLatitude(),address.getLongitude());
                        map.addMarker(new MarkerOptions().position(latlng).title(location));
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,10));
                    }
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });

btnGo.setEnabled(false);
btnGo.setVisibility(View.INVISIBLE);

            //adding marker


        }catch(Exception err){
            Toast.makeText(this, ""+err.getMessage(), Toast.LENGTH_SHORT).show();
        }
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Track(""+goHere.latitude,""+goHere.longitude,"d");

            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mapDisplay.this, mapDisplay.class));
            }
        });
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mapDisplay.this, Navigation.class));
            }
        });
        ref.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String lat = dataSnapshot.child("lati").getValue().toString();
                    String lon = dataSnapshot.child("longi").getValue().toString();
                    String name = dataSnapshot.child("name").getValue().toString();

                lats.add(Double.parseDouble(lat));
                longs.add(Double.parseDouble(lon));
                names.add(name);
                }


    }
    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mapDisplay.this, Favourites.class));
            }
        });
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mapDisplay.this, settings.class));
            }
        });
        try {
            mAuth = FirebaseAuth.getInstance();
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            fetchLocation();
        } catch (Exception r) {
            Toast.makeText(this, "" + r.getMessage(), Toast.LENGTH_SHORT).show();
        }
ref.addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        for (DataSnapshot dataSnapshot:snapshot.getChildren()){
            String lat = dataSnapshot.child("lati").getValue().toString();
            String lon = dataSnapshot.child("longi").getValue().toString();
            String name = dataSnapshot.child("name").getValue().toString();

            lats.add(Double.parseDouble(lat));
            longs.add(Double.parseDouble(lon));
            names.add(name);

            for (int i=0; i>lats.size();i++){
                LatLng place2 = new LatLng(lats.get(i),longs.get(i));
                markerOptions3 = new MarkerOptions();
                markerOptions3.position(place2).title(names.get(i));
                marker = map.addMarker(markerOptions3);
            }
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(mapDisplay.this, Login.class));
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        try {
            if (currentLocation != null) {
                LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                myLat = ""+currentLocation.getLatitude();
                myLong = ""+currentLocation.getLongitude();
                markerOptions = new MarkerOptions().position(latLng).title("I stay here");
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f));
                LatLng place = new LatLng(-26.180564,27.995862);
                LatLng place3 = new LatLng(-26.117243,28.454875);
                LatLng place4 = new LatLng(-26.111424, 28.455218);
                LatLng place5 = new LatLng(-26.116704, 28.460497);
                MarkerOptions markerOptions2 = new MarkerOptions().position(place).title("Where we chill");
                MarkerOptions markerOptions3 = new MarkerOptions().position(place3).title("Pasii's spot");
                MarkerOptions markerOptions4 = new MarkerOptions().position(place4).title("Winnie Mandela Chillout");
                MarkerOptions markerOptions5 = new MarkerOptions().position(place5).title("Bophelo Hub");
                for (int i=0; i>lats.size();i++){
                    LatLng place2 = new LatLng(lats.get(i),longs.get(i));
                    MarkerOptions markerOptionsv = new MarkerOptions().position(place2).title(names.get(i));
                    googleMap.addMarker(markerOptionsv);
                }
                googleMap.addMarker(markerOptions);
                googleMap.addMarker(markerOptions2);
                googleMap.addMarker(markerOptions3);
                googleMap.addMarker(markerOptions4);
                googleMap.addMarker(markerOptions5);
googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        goHere = marker.getPosition();
        btnGo.setEnabled(true);
        btnGo.setVisibility(View.VISIBLE);
        markerTitle.setText(marker.getTitle());
        return false;
    }
});
            }

        }catch (Exception r){
           Toast.makeText(this, ""+r.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void fetchLocation() {
        try {
            if (ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
                return;
            }
            //my spot
            Task<Location> task = fusedLocationProviderClient.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        currentLocation = location;
                        //Toast.makeText(getApplicationContext(), currentLocation.getLatitude() + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                        assert supportMapFragment != null;
                        supportMapFragment.getMapAsync(mapDisplay.this);
                    }
                }
            });
        }catch (Exception r){
            Toast.makeText(this, ""+r.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLocation();
                }
                break;
        }
    }
    private void Track(String source,String destination, String mode){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q="+source+","+destination+"&mode="+mode));
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }

}