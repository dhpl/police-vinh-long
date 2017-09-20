package vn.hoitinhocvinhlong.policevinhlong.controller.activity.map;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import vn.hoitinhocvinhlong.policevinhlong.R;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mGoogleMap;
    private SupportMapFragment mSupportMapFragment;
    private Location mLocationTinNhan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mSupportMapFragment.getMapAsync(this);
        if(getIntent() != null){
            mLocationTinNhan = new Location("m");
            mLocationTinNhan.setLatitude(getIntent().getDoubleExtra("LatitudeTinNhan", 0));
            mLocationTinNhan.setLongitude(getIntent().getDoubleExtra("LongitudeTinNhan", 0));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        LatLng tinNhan = new LatLng(mLocationTinNhan.getLatitude(), mLocationTinNhan.getLongitude());
        mGoogleMap.addMarker(new MarkerOptions().position(tinNhan));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(tinNhan, 14);
        mGoogleMap.animateCamera(cameraUpdate);
    }
}
