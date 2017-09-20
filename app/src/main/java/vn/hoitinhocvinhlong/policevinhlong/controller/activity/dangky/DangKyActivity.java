package vn.hoitinhocvinhlong.policevinhlong.controller.activity.dangky;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vn.hoitinhocvinhlong.policevinhlong.R;
import vn.hoitinhocvinhlong.policevinhlong.controller.activity.xacnhan.XacNhanActivity;
import vn.hoitinhocvinhlong.policevinhlong.model.ThanhVien;
import vn.hoitinhocvinhlong.policevinhlong.util.JsonResult;

public class DangKyActivity extends AppCompatActivity {

    //View
    private Toolbar mToolbar;
    private TextInputEditText mTextInputHoTen, mTextInputSoDienThoai, mTextInputMatKhau, mTextInputEmail;
    private Button mButtonDangKy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        //Get view
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTextInputHoTen = (TextInputEditText) findViewById(R.id.textInputHoTen);
        mTextInputSoDienThoai = (TextInputEditText) findViewById(R.id.textInputSoDienThoai);
        mTextInputMatKhau = (TextInputEditText) findViewById(R.id.textInputMatKhau);
        mTextInputEmail = (TextInputEditText) findViewById(R.id.textInputEmail);
        mButtonDangKy = (Button) findViewById(R.id.buttonDangKy);
        //Set toolbar
        if(getSupportActionBar() == null){
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle("Đăng ký");
        }
        //Đăng ký
        mButtonDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangKy();
            }
        });


    }

    private void dangKy(){
        String hoTen = mTextInputHoTen.getText().toString().trim();
        final String soDienThoai = mTextInputSoDienThoai.getText().toString().trim();
        String matKhau = mTextInputMatKhau.getText().toString().trim();
        String email = mTextInputEmail.getText().toString().trim();
        if(TextUtils.isEmpty(hoTen)){
            mTextInputHoTen.setError("Không được để trống");
            return;
        }
        if(TextUtils.isEmpty(soDienThoai)){
            mTextInputSoDienThoai.setError("Không được để trống");
            return;
        }
        if(TextUtils.isEmpty(matKhau)){
            mTextInputMatKhau.setError("Không được để trống");
            return;
        }
        if(matKhau.length() < 6){
            mTextInputMatKhau.setError("Mật khầu phải lớn hơn hoặc bằng 6 ký tự");
            return;
        }
        final ThanhVien thanhVien = new ThanhVien();
        thanhVien.setTen(hoTen);
        thanhVien.setSdt(soDienThoai);
        thanhVien.setMatkhau(matKhau);
        thanhVien.setEmail(email);
        JsonResult.checkRegister(this, thanhVien, new JsonResult.GetSuccess() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                if(response.getInt("code") == 1000){
                    Intent iXacNhan = new Intent(DangKyActivity.this, XacNhanActivity.class);
                    iXacNhan.putExtra("ThanhVien", thanhVien);
                    startActivity(iXacNhan);
                }else if(response.getInt("code") == 1002){
                    Toast.makeText(DangKyActivity.this, "Tài khoản tồn tại.", Toast.LENGTH_SHORT).show();
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

    private boolean checkEmail(String email){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Matcher matcher = pattern.matcher(email);
        if(matcher.find()){
            return true;
        }
        return false;
    }

}
