package vn.hoitinhocvinhlong.policevinhlong.controller.activity.dangnhap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import vn.hoitinhocvinhlong.policevinhlong.R;
import vn.hoitinhocvinhlong.policevinhlong.controller.activity.dangky.DangKyActivity;
import vn.hoitinhocvinhlong.policevinhlong.controller.activity.main.MainActivity;
import vn.hoitinhocvinhlong.policevinhlong.controller.service.PoliceService;
import vn.hoitinhocvinhlong.policevinhlong.model.ThanhVien;
import vn.hoitinhocvinhlong.policevinhlong.util.JsonResult;

public class DangNhapActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Button mButtonDangNhap, mButtonDangKy;
    private TextInputEditText mTextInputEditTextUsername, mTextInputEditTextPassword;
    private Gson mGson;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        //Sharepreferences
        mSharedPreferences = getSharedPreferences("TaiKhoanDangNhap", MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        //Gson
        mGson = new Gson();
        // View
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mButtonDangNhap = (Button) findViewById(R.id.buttonDangNhap);
        mButtonDangKy = (Button) findViewById(R.id.buttonDangKy);
        mTextInputEditTextUsername = (TextInputEditText) findViewById(R.id.textInputSoDienThoai);
        mTextInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputMatKhau);
        //Set toolbar
        if(getSupportActionBar() == null){
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle("Đăng nhập");
        }
        //Đăng ký
        mButtonDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iDangKy = new Intent(DangNhapActivity.this, DangKyActivity.class);
                startActivity(iDangKy);
            }
        });
        // Đăng nhập
        mButtonDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();

            }
        });
    }

    public void login(){
        String username = mTextInputEditTextUsername.getText().toString().trim();
        String password = mTextInputEditTextPassword.getText().toString().trim();
        if(TextUtils.isEmpty(username)){
            mTextInputEditTextUsername.setError("Không được để trống.");
            return;
        }
        if(TextUtils.isEmpty(password)){
            mTextInputEditTextPassword.setError("Không được để trống.");
            return;
        }
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Đang đăng nhập");
        progressDialog.setMessage("Vui lòng chờ");
        progressDialog.setCancelable(true);
        progressDialog.show();
        JsonResult.login(this, username, password, new JsonResult.GetSuccess() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                int code = response.getInt("code");
                if(code == 1000){
                    String s = String.valueOf(response.getJSONObject("data"));
                    ThanhVien thanhVien = mGson.fromJson(s, ThanhVien.class);
                    mEditor.putString("Username", thanhVien.getUsername());
                    mEditor.putString("MatKhau", thanhVien.getMatkhau());
                    mEditor.putString("Ten", thanhVien.getTen());
                    mEditor.putInt("IdUser", thanhVien.getId());
                    mEditor.commit();
                    progressDialog.dismiss();
                    Intent iService = new Intent(DangNhapActivity.this, PoliceService.class);
                    iService.putExtra("ID", thanhVien.getId());
                    startService(iService);
                    Intent iMain = new Intent(DangNhapActivity.this, MainActivity.class);
                    startActivity(iMain);
                    finish();
                }else if(code == 1003){
                    progressDialog.dismiss();
                    Toast.makeText(DangNhapActivity.this, "Tài khoản không chính xác.", Toast.LENGTH_SHORT).show();
                }else{
                    progressDialog.dismiss();
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
}
