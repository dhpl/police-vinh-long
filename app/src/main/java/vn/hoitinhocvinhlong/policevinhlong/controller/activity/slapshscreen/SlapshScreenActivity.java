package vn.hoitinhocvinhlong.policevinhlong.controller.activity.slapshscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import vn.hoitinhocvinhlong.policevinhlong.R;
import vn.hoitinhocvinhlong.policevinhlong.controller.activity.dangnhap.DangNhapActivity;

public class SlapshScreenActivity extends AppCompatActivity {

    private TextView mTextViewLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_slapsh_screen);
        //View
        mTextViewLogo = (TextView) findViewById(R.id.textViewLogo);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.logo);
        mTextViewLogo.setAnimation(animation);
        animation.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent iDangNhap = new Intent(SlapshScreenActivity.this, DangNhapActivity.class);
                startActivity(iDangNhap);
                finish();

            }
        }, 3000);
    }
}
