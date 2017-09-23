package vn.hoitinhocvinhlong.policevinhlong.controller.activity.map;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vn.hoitinhocvinhlong.policevinhlong.R;
import vn.hoitinhocvinhlong.policevinhlong.model.SoBanNganh;
import vn.hoitinhocvinhlong.policevinhlong.util.JsonResult;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mGoogleMap;
    private SupportMapFragment mSupportMapFragment;
    private Location mLocationTinNhan;
    private List<SoBanNganh> mSoBanNganh;
    private int mNhiemVu;
    private int mPage = 0;
    private static final String API_POLYLINE = "AIzaSyBkErf0g7cGjxlqAcUQVLD_GVGVLxwwj3w";

//    "https://maps.googleapis.com/maps/api/directions/json?origin="+ mLocationCurrent.getLatitude() + "," + mLocationCurrent.getLongitude()
//                + "&destination=" + mLocationQuanAn.getLatitude() + "," + mLocationQuanAn.getLongitude() + "&key=" + "AIzaSyC-eJjaraDQTRojyNkpIDICvi-W0ME1DOs"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mSoBanNganh = new ArrayList<>();
        mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if(getIntent() != null){
            mLocationTinNhan = new Location("m");
            mLocationTinNhan.setLatitude(getIntent().getDoubleExtra("LatitudeTinNhan", 0));
            mLocationTinNhan.setLongitude(getIntent().getDoubleExtra("LongitudeTinNhan", 0));
            mNhiemVu = getIntent().getIntExtra("NhiemVu", 0);
        }
        JsonResult.getSoBanNganh(this, mNhiemVu, mPage, new JsonResult.GetSuccess() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                if(response.getString("code").equals("1000")){
                    JSONObject jsonObjectSoBanNganh = response.getJSONObject("data");
                    JSONArray jsonArraySoBanNganh = jsonObjectSoBanNganh.getJSONArray("items");
                    int size = jsonArraySoBanNganh.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject jsonSoBanNganh = jsonArraySoBanNganh.getJSONObject(i);
                        int id = jsonSoBanNganh.getInt("id");
                        String ten = jsonSoBanNganh.getString("ten");
                        double lat = jsonSoBanNganh.getDouble("lat");
                        double lng = jsonSoBanNganh.getDouble("lng");
                        String diachi = jsonSoBanNganh.getString("diachi");
                        String sdt = jsonSoBanNganh.getString("sodienthoai");
                        String cap = jsonSoBanNganh.getString("cap");
                        String tinh = jsonSoBanNganh.getString("tinh");
                        String nhiemvu = jsonSoBanNganh.getString("nhiemvu");
                        List<Integer> nhiemvus = new Gson().fromJson(nhiemvu, List.class);
                        mSoBanNganh.add(new SoBanNganh(id, ten, lat,lng, diachi, nhiemvus, sdt, cap, tinh));
                    }
                    if(mSoBanNganh.size() == size){
                        mSupportMapFragment.getMapAsync(MapActivity.this);
                    }
                }else{
                    Toast.makeText(MapActivity.this, "Có lỗi xãy ra.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onResponse(String response) throws JSONException {

            }

            @Override
            public void onError(VolleyError response) throws JSONException {

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        LatLng tinNhan = new LatLng(mLocationTinNhan.getLatitude(), mLocationTinNhan.getLongitude());
        LatLng soBanNganh = new LatLng(mSoBanNganh.get(0).getLat(), mSoBanNganh.get(0).getLng());
        mGoogleMap.addMarker(new MarkerOptions().position(tinNhan));
        mGoogleMap.addMarker(new MarkerOptions().position(soBanNganh).title(mSoBanNganh.get(0).getTen()));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(soBanNganh, 10);
        mGoogleMap.animateCamera(cameraUpdate);
        getPolyLine(mSoBanNganh.get(0), mLocationTinNhan, mGoogleMap);
    }

    private void getPolyLine(SoBanNganh soBanNganh, Location locationTinNhan, final GoogleMap googleMap){
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + soBanNganh.getLat() + ","
                + soBanNganh.getLng() + "&destination=" + locationTinNhan.getLatitude() + "," + locationTinNhan.getLongitude()
                + "&key=" + API_POLYLINE;
        JsonResult.getPolyLine(this, url, new JsonResult.GetSuccess() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                List<LatLng> latLngs = new ArrayList<>();
                JSONArray jsonArray = response.getJSONArray("routes");
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                JSONObject jsonObjectOverviewPolyLine = jsonObject.getJSONObject("overview_polyline");
                String points = jsonObjectOverviewPolyLine.getString("points");
                latLngs = PolyUtil.decode(points);
                PolylineOptions polylineOptions = new PolylineOptions();
                for(LatLng latLng : latLngs){
                    polylineOptions.add(latLng);
                }
                googleMap.addPolyline(polylineOptions);
            }

            @Override
            public void onResponse(String response) throws JSONException {

            }

            @Override
            public void onError(VolleyError response) throws JSONException {

            }
        });
    }
}
