package vn.hoitinhocvinhlong.policevinhlong.controller.activity.guitinnhan;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.hoitinhocvinhlong.policevinhlong.R;
import vn.hoitinhocvinhlong.policevinhlong.adapter.AdapterCameraAndVideo;
import vn.hoitinhocvinhlong.policevinhlong.controller.activity.main.MainActivity;
import vn.hoitinhocvinhlong.policevinhlong.model.NhiemVu;
import vn.hoitinhocvinhlong.policevinhlong.model.TinNhan;
import vn.hoitinhocvinhlong.policevinhlong.util.JsonResult;
import vn.hoitinhocvinhlong.policevinhlong.util.UploadFileImage;
import vn.hoitinhocvinhlong.policevinhlong.util.UploadFileVideo;

public class GuiTinNhanActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks
        , GoogleApiClient.OnConnectionFailedListener, AdapterCameraAndVideo.ProtocolRemoveTinNhan{

    // View
    private Toolbar mToolbar;
    private Button mButtonCamera, mButtonVideo, mButtonGui;
    private EditText mTextInputNoiDung;
    private Spinner mSpinnerSoBanNganh;
    // Camera, Video
    private static final int REQUEST_PERMISSION_CAMERA = 1;
    private static final int REQUEST_PERMISSION_LOCATION = 2;
    private String[] mPermissionsCameraVideo = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
    private String[] mPermissionsLocation = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private List<String> mPermissionCameraList = new ArrayList<>();
    private List<String> mPermissionLocationList = new ArrayList<>();
    private static final int REQUEST_IMAGE_CAPTURE_NEW = 11;
    private static final int REQUEST_VIDEO_CAPTURE_NEW = 22;
    private static final int REQUEST_IMAGE_CAPTURE = 33;
    private static final int REQUEST_VIDEO_CAPTURE = 44;
    private static String mCurrentPhotoPath, mCurrentVideoPath;
    private int mSizeVideo, mSizeCamera;
    private NhiemVu mNhiemVu;
    //Recycler view camera, video
    private RecyclerView mRecyclerViewCameraAndVideo;
    private List<File> mFileList;
    private AdapterCameraAndVideo mAdapterCameraAndVideo;
    //Location
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private List<String> mListVideo;
    private List<String> mListImage;
    //Nhiem vu
    private List<NhiemVu> mNhiemVuList;
    private ArrayAdapter<NhiemVu> mNhiemVuArrayAdapter;
    //Sharepreference
    private SharedPreferences mSharedPreferences;
    private int mIdUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui_tin_nhan);
        //Get sharepreference
        mSharedPreferences = getSharedPreferences("TaiKhoanDangNhap", MODE_PRIVATE);
        mIdUser = mSharedPreferences.getInt("IdUser", 0);
        //Camera, Video
        mListVideo = new ArrayList<>();
        mListImage = new ArrayList<>();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // Get view
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mButtonCamera = (Button) findViewById(R.id.buttonCamera);
        mButtonVideo = (Button) findViewById(R.id.buttonVideo);
        mButtonGui = (Button) findViewById(R.id.buttonGui);
        mSpinnerSoBanNganh = (Spinner) findViewById(R.id.spinnerSoBanNganh);
        mTextInputNoiDung = (TextInputEditText) findViewById(R.id.textInputEditTextNoiDung);
        //Set nhiem vu
        mNhiemVuList = new ArrayList<>();
        JsonResult.getNhiemVu(this, new JsonResult.GetSuccess() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                if(response.getString("code").equals("1000")){
                    JSONObject jsonObjectData = response.getJSONObject("data");
                    JSONArray jsonArrayItem = jsonObjectData.getJSONArray("items");
                    for(int i =0; i < jsonArrayItem.length(); i++){
                        JSONObject jsonObjectItem = jsonArrayItem.getJSONObject(i);
                        int id = jsonObjectItem.getInt("id");
                        String ten = jsonObjectItem.getString("ten");
                        mNhiemVuList.add(new NhiemVu(id, ten));
                    }
                    mNhiemVuArrayAdapter = new ArrayAdapter<NhiemVu>(GuiTinNhanActivity.this, android.R.layout.simple_spinner_item, mNhiemVuList);
                    mNhiemVuArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                    mSpinnerSoBanNganh.setAdapter(mNhiemVuArrayAdapter);
                    mSpinnerSoBanNganh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            mNhiemVu = mNhiemVuList.get(i);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                }
            }

            @Override
            public void onResponse(String response) throws JSONException {

            }

            @Override
            public void onError(VolleyError response) throws JSONException {

            }
        });
        // Set toolbar
        if(getSupportActionBar() == null){
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle("Gửi Tin Nhắn");
        }
        // Location
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addConnectionCallbacks(this)
                .build();
        // Chụp hình
        mButtonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(requestPermissionCameraVideo()){
                        try {
                            dispatchTakePictureIntent();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }else{
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        try {
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(createImageFile()));
                            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        // Video
        mButtonVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(requestPermissionCameraVideo()){
                        try {
                            dispatchTakeVideoIntent();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }else{
                    Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
                        try {
                            takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, createVideoFile());
                            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        // Gửi
        mButtonGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String noiDung = mTextInputNoiDung.getText().toString().trim();
                if(mListImage.size() > 0){
                    mListImage.clear();
                }
                if(mListVideo.size() > 0){
                    mListVideo.clear();
                }
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(requestPermissionLocation()){
                        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    }
                }else{
                    mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                }
                for(int i = 0; i < mFileList.size(); i++){
                    if(mFileList.get(i).getAbsolutePath().split("\\.")[1].equals("jpg")){
                        new UploadFileImage(GuiTinNhanActivity.this, new UploadFileImage.GetSuccess() {
                            @Override
                            public void onPostExcute(JSONObject response) {
                            }

                            @Override
                            public void onPostExcute(String unused, String response) {
                                String urlImage = response.substring(2, response.length());
                                mListImage.add(JsonResult.URL + urlImage + ".jpg");
                                if(mListImage.size() +  mListVideo.size() == mFileList.size()){
                                    TinNhan tinNhan = new TinNhan();
                                    tinNhan.setIduser(mIdUser);
                                    tinNhan.setHinhanh(mListImage);
                                    tinNhan.setVideo(mListVideo);
                                    tinNhan.setLat(mLocation.getLatitude());
                                    tinNhan.setLng(mLocation.getLongitude());
                                    tinNhan.setNhiemvu(mNhiemVu.getId());
                                    tinNhan.setNoidung(noiDung);
                                    uploadTinNhan(tinNhan);
                                    return;
                                }
                            }

                            @Override
                            public void onError(VolleyError response) {

                            }
                        }).execute(mFileList.get(i).getAbsolutePath(), String.valueOf(true));
                    }else if(mFileList.get(i).getAbsolutePath().split("\\.")[1].equals("mp4")){
                        new UploadFileVideo(GuiTinNhanActivity.this, new UploadFileVideo.GetSuccess() {
                            @Override
                            public void onPostExcute(JSONObject response) {

                            }

                            @Override
                            public void onPostExcute(String unused, String response) {
                                String urlVideo = response.substring(2, response.length());
                                mListVideo.add(JsonResult.URL + urlVideo + ".mp4");
                                if(mListImage.size() +  mListVideo.size() == mFileList.size()){
                                    TinNhan tinNhan = new TinNhan();
                                    tinNhan.setIduser(mIdUser);
                                    tinNhan.setHinhanh(mListImage);
                                    tinNhan.setVideo(mListVideo);
                                    tinNhan.setLat(mLocation.getLatitude());
                                    tinNhan.setLng(mLocation.getLongitude());
                                    tinNhan.setNhiemvu(mNhiemVu.getId());
                                    tinNhan.setNoidung(noiDung);
                                    uploadTinNhan(tinNhan);
                                    return;
                                }

                            }

                            @Override
                            public void onError(VolleyError response) {

                            }
                        }).execute(mFileList.get(i).getAbsolutePath(), String.valueOf(true));
                    }
                }
                if(mFileList.isEmpty()){
                    TinNhan tinNhan = new TinNhan();
                    tinNhan.setIduser(mIdUser);
                    tinNhan.setHinhanh(mListImage);
                    tinNhan.setVideo(mListVideo);
                    tinNhan.setLat(mLocation.getLatitude());
                    tinNhan.setLng(mLocation.getLongitude());
                    tinNhan.setNhiemvu(mNhiemVu.getId());
                    tinNhan.setNoidung(noiDung);
                    uploadTinNhan(tinNhan);
                }


            }
        });
        // Set recycler view camera and video
        mFileList = new ArrayList<>();
        mRecyclerViewCameraAndVideo = (RecyclerView) findViewById(R.id.recyclerViewCameraAndVideo);
        mRecyclerViewCameraAndVideo.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewCameraAndVideo.setNestedScrollingEnabled(false);
        mRecyclerViewCameraAndVideo.setHasFixedSize(true);
        mRecyclerViewCameraAndVideo.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mAdapterCameraAndVideo = new AdapterCameraAndVideo(this, mFileList, this);
        mRecyclerViewCameraAndVideo.setAdapter(mAdapterCameraAndVideo);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mGoogleApiClient != null){
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mGoogleApiClient != null){
            mGoogleApiClient.disconnect();
        }
    }

    private boolean requestPermissionCameraVideo() {
        for (String s : mPermissionsCameraVideo) {
            if (ContextCompat.checkSelfPermission(this, s) != PackageManager.PERMISSION_GRANTED) {
                mPermissionCameraList.add(s);
            }
        }
        if (!mPermissionCameraList.isEmpty()) {
            ActivityCompat.requestPermissions(this, mPermissionCameraList.toArray(new String[mPermissionCameraList.size()]), REQUEST_PERMISSION_CAMERA);
            return false;
        }
        return true;
    }

    private boolean requestPermissionLocation(){
        for(String s : mPermissionsLocation){
            if(ContextCompat.checkSelfPermission(this, s) != PackageManager.PERMISSION_GRANTED){
                mPermissionLocationList.add(s);
            }
        }
        if(!mPermissionLocationList.isEmpty()){
            ActivityCompat.requestPermissions(this, mPermissionLocationList.toArray(new String[mPermissionLocationList.size()]), REQUEST_PERMISSION_LOCATION);
            return false;
        }
        return true;
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private File createVideoFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "VIDEO_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File video = File.createTempFile(
                imageFileName,
                ".mp4",
                storageDir
        );
        mCurrentVideoPath = "file:" + video.getAbsolutePath();
        return video;
    }

    private void dispatchTakePictureIntent() throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "vn.hoitinhocvinhlong.policevinhlong.provider",
                        createImageFile());
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_NEW);
            }

        }
    }

    private void dispatchTakeVideoIntent() throws IOException {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createVideoFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "vn.hoitinhocvinhlong.policevinhlong.provider",
                        createVideoFile());
                takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE_NEW);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case REQUEST_PERMISSION_CAMERA:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED  && grantResults[2] == PackageManager.PERMISSION_GRANTED){
                    try {
                        dispatchTakePictureIntent();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(this, "Ứng dụng cần quyền truy cập!", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_PERMISSION_LOCATION:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                }else{
                    Toast.makeText(this, "Ứng dụng cần quyền truy cập!", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case REQUEST_IMAGE_CAPTURE_NEW:
                if(resultCode == RESULT_OK){
                    if(mCurrentPhotoPath != null){
                        Uri imageUri = Uri.parse(mCurrentPhotoPath);
                        mFileList.add(mFileList.size(), new File(imageUri.getPath()));
                        setVisibleRecyclerView();
                        mAdapterCameraAndVideo.notifyItemInserted(mFileList.size());
                    }
                }
                break;
            case REQUEST_VIDEO_CAPTURE_NEW:
                if(resultCode == RESULT_OK){
                    if(mCurrentVideoPath != null){
                        Uri videoUri = Uri.parse(mCurrentVideoPath);
                        mFileList.add(mFileList.size(),  new File(videoUri.getPath()));
                        setVisibleRecyclerView();
                        mAdapterCameraAndVideo.notifyItemInserted(mFileList.size());
                    }
                }
                break;
            case REQUEST_IMAGE_CAPTURE:
                if(resultCode == RESULT_OK){
                    Uri cameraUri = data.getData();
                    mFileList.add(mFileList.size(), new File(cameraUri.getPath()));
                    setVisibleRecyclerView();
                    mAdapterCameraAndVideo.notifyItemInserted(mFileList.size());
                }
                break;
            case REQUEST_VIDEO_CAPTURE:
                if(resultCode == RESULT_OK){
                    Uri videoUri = data.getData();
                    mFileList.add(mFileList.size(), new File(videoUri.getPath()));
                    setVisibleRecyclerView();
                    mAdapterCameraAndVideo.notifyItemInserted(mFileList.size());
                }
        }
    }

    private void setVisibleRecyclerView(){
        if(mFileList.size() != 0){
            mRecyclerViewCameraAndVideo.setVisibility(View.VISIBLE);
        }else{
            mRecyclerViewCameraAndVideo.setVisibility(View.GONE);
        }
    }

    private void uploadTinNhan(TinNhan tinNhan){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Đang gửi");
        progressDialog.setMessage("Vui lòng chờ");
        progressDialog.show();
        JsonResult.tinNhan(this, tinNhan, new JsonResult.GetSuccess() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                progressDialog.dismiss();
                Intent iMain = new Intent(GuiTinNhanActivity.this, MainActivity.class);
                iMain.putExtra("Position", mNhiemVu.getId() - 1);
                startActivity(iMain);
            }

            @Override
            public void onResponse(String response) throws JSONException {

            }

            @Override
            public void onError(VolleyError response) throws JSONException {

            }
        });
    }


    // Google Location
    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void complete(int position) {
        mFileList.remove(position);
        mAdapterCameraAndVideo.notifyDataSetChanged();
        setVisibleRecyclerView();
    }
}
