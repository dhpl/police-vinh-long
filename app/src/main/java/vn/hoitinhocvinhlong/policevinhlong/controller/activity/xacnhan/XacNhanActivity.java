package vn.hoitinhocvinhlong.policevinhlong.controller.activity.xacnhan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import vn.hoitinhocvinhlong.policevinhlong.R;
import vn.hoitinhocvinhlong.policevinhlong.controller.activity.main.MainActivity;
import vn.hoitinhocvinhlong.policevinhlong.model.ThanhVien;
import vn.hoitinhocvinhlong.policevinhlong.util.JsonResult;

public class XacNhanActivity extends AppCompatActivity {

    //View
    private TextInputEditText mEditTextMaXacNhan;
    private RadioButton mRadioButtonSMS, mRadioButtonThoai;
    private Button mButtonGui, mButtonXacNhan;
    private ProgressBar mProgressBar;
    private Toolbar mToolbar;
    private int mRandom;
    private ThanhVien mThanhVien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xac_nhan);
        //Get view
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mEditTextMaXacNhan = (TextInputEditText) findViewById(R.id.editTextMaXacNhan);
        mRadioButtonSMS = (RadioButton) findViewById(R.id.radioButtonSMS);
        mRadioButtonThoai = (RadioButton) findViewById(R.id.radioButtonDienThoai);
        mButtonGui = (Button) findViewById(R.id.buttonGui);
        mButtonXacNhan = (Button) findViewById(R.id.buttonXacNhan);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mEditTextMaXacNhan.setEnabled(false);
        //Set toolbar
        if(getSupportActionBar() == null){
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle("Xác nhận");
        }
        if(getIntent() != null){
            mThanhVien = getIntent().getParcelableExtra("ThanhVien");
        }
        //Gửi xác nhận
        mButtonGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRandom = new Random().nextInt(9999 - 1000) + 1000;
                // SMS
                if(mRadioButtonSMS.isChecked()){
                    String s = "http://rest.esms.vn/MainService.svc/json/SendMultipleMessage_V4_get?Phone=" + mThanhVien.getSdt()
                            + "&Content=Ma%20Xac%20Nhan%20" + mRandom
                            + "&ApiKey=6ED482D2618834551E20E65C59E841&SecretKey=0B237E32CDDE5DA0A295D2C8716419&SmsType=4";
                    JsonResult.registerSMS(XacNhanActivity.this, s, new JsonResult.GetSuccess() {
                        @Override
                        public void onResponse(JSONObject response) throws JSONException {
                            mEditTextMaXacNhan.setEnabled(true);
                        }

                        @Override
                        public void onResponse(String response) throws JSONException {

                        }

                        @Override
                        public void onError(VolleyError response) throws JSONException {

                        }
                    });
                }
                //Gọi thoại
                if(mRadioButtonThoai.isChecked()){
                    String s = "http://voiceapi.esms.vn/MainService.svc/json/MakeCall?ApiKey=6ED482D2618834551E20E65C59E841&SecretKey=0B237E32CDDE5DA0A295D2C8716419&ApiCode=15378&PassCode=B8Z5fj4THpVgEXPN&Phone=" +
                            mThanhVien.getSdt() + "&VarStr=" + mRandom;
                    JsonResult.registerVoice(XacNhanActivity.this, s, new JsonResult.GetSuccess() {
                        @Override
                        public void onResponse(JSONObject response) throws JSONException {
                            mEditTextMaXacNhan.setEnabled(true);
                        }

                        @Override
                        public void onResponse(String response) throws JSONException {

                        }

                        @Override
                        public void onError(VolleyError response) throws JSONException {

                        }
                    });
                }
                mButtonGui.setEnabled(false);
                mProgressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mButtonGui.setEnabled(true);
                        mProgressBar.setVisibility(View.GONE);
                    }
                }, 60000);
            }
        });
        //Xác nhận
        mButtonXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEditText();
                int xacNhan =  Integer.parseInt(mEditTextMaXacNhan.getText().toString().trim());
                if(xacNhan == mRandom){
                    JsonResult.register(XacNhanActivity.this, mThanhVien, new JsonResult.GetSuccess() {
                        @Override
                        public void onResponse(JSONObject response) throws JSONException {
                            if(response.getInt("code") == 1000){
                                Intent iMain = new Intent(XacNhanActivity.this, MainActivity.class);
                                iMain.putExtra("SDT", mThanhVien.getSdt());
                                iMain.putExtra("MatKhau", mThanhVien.getMatkhau());
                                startActivity(iMain);
                                finish();
                            }else if(response.getInt("code") == 1002){
                                Toast.makeText(XacNhanActivity.this, "Tài khoản tồn tại.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onResponse(String response) throws JSONException {

                        }

                        @Override
                        public void onError(VolleyError response) throws JSONException {

                        }
                    });
                }else{
                    Toast.makeText(XacNhanActivity.this, "Mã xác nhận không đúng.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void checkEditText(){
        String s = mEditTextMaXacNhan.getText().toString().trim();
        if(TextUtils.isEmpty(s)){
            mEditTextMaXacNhan.setError("Không được để trống.");
            return;
        }
    }
}

