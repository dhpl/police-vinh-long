package vn.hoitinhocvinhlong.policevinhlong.controller.service;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import vn.hoitinhocvinhlong.policevinhlong.util.JsonResult;

/**
 * Created by Long on 9/20/2017.
 */

public class UploadImage extends IntentService {

    public UploadImage() {
        super("Upload");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        File file = (File) intent.getSerializableExtra("File");
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        JsonResult.uploadImage(this, bitmap, new JsonResult.GetSuccess() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                System.out.println("Thành công");
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
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }
}
