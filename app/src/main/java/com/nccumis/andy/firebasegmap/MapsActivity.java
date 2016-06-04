package com.nccumis.andy.firebasegmap;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String FIREBASE_URL = "https://amber-torch-4664.firebaseio.com/";
    private Firebase firebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Firebase.setAndroidContext(this);
        firebaseRef = new Firebase(FIREBASE_URL);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        firebaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.child("Mark").getChildren()) {
                    String latitude = child.child("latitude").getValue().toString();
                    String longitude = child.child("longitude").getValue().toString();

                    double latitude_val = Double.parseDouble(latitude);
                    double longitude_val = Double.parseDouble(longitude);
                    String title = child.child("title").getValue().toString();
                    LatLng cod = new LatLng(latitude_val, longitude_val);
                    googleMap.addMarker(new MarkerOptions().position(cod).title(title));
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        LatLng defaultLat = new LatLng(24.9, 121.5);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(defaultLat));
        /**double lat, lng;
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER); // 設定定位資訊由 GPS提供
            lat = location.getLatitude();  // 取得經度
            lng = location.getLongitude(); // 取得緯度
            LatLng HOME = new LatLng(lat, lng);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(HOME)); // 定位到現在位置
            return;
        }*/

    }
}
