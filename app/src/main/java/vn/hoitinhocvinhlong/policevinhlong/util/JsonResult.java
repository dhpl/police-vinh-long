package vn.hoitinhocvinhlong.policevinhlong.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import vn.hoitinhocvinhlong.policevinhlong.model.ThanhVien;

/**
 * Created by Long on 9/19/2017.
 */

public class JsonResult {

    private static int MY_SOCKET_TIMEOUT_MS=30000;
    public static final String EVENT="event";
    public static String tag_json_obj = "json_obj_req";
    public static String TAG="Json Result";
    public static final String URL= "http://police.xekia.vn:9020/";

    public static void register(final Context context, final ThanhVien user, final GetSuccess getSuccess) {
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL + "users/register", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    getSuccess.onResponse(jsonObject);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                try {
                    getSuccess.onError(error);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ten", user.getTen());
                params.put("sdt", user.getSdt());
                params.put("username", user.getSdt());
                params.put("matkhau", user.getMatkhau());
                params.put("thoigiantao", new Date().getTime()+"");
                params.put("email", user.getEmail());
                return checkParams(params);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                //params.put("Content-Type", "application/json; charset=utf-8");
                return checkParams(params);
            }

        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(strReq);
    }

    public static void checkRegister(final Context context, final ThanhVien user, final GetSuccess getSuccess) {
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL + "users/checkregister", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    getSuccess.onResponse(jsonObject);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                try {
                    getSuccess.onError(error);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ten", user.getTen());
                params.put("sdt", user.getSdt());
                params.put("username", user.getSdt());
                params.put("matkhau", user.getMatkhau());
                params.put("thoigiantao", new Date().getTime()+"");
                params.put("email", user.getEmail());
                return checkParams(params);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                //params.put("Content-Type", "application/json; charset=utf-8");
                return checkParams(params);
            }

        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(strReq);
    }

    public static void registerSMS(final Context context, String param, final GetSuccess getSuccess) {
        StringRequest strReq = new StringRequest(Request.Method.GET,
                param , new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    getSuccess.onResponse(jsonObject);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                try {
                    getSuccess.onError(error);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return checkParams(params);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                //params.put("Content-Type", "application/json; charset=utf-8");
                return checkParams(params);
            }

        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(strReq);
    }

    public static void login(final Context context, final String username, final String password, final GetSuccess getSuccess) {
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL + "users/login", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    getSuccess.onResponse(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                try {
                    getSuccess.onError(error);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("matkhau", password);
                return checkParams(params);
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String session = null;
                Log.e("rese ", response.headers.toString());
                if(response.headers != null) {
                    session = response.headers.get("Set-Cookie");
                    String[] arr = null;
                    if(session!=null) {
                        arr = session.split(";");
                        Log.i("OUTPUT SESSION - ", arr[0]);
                        SharedPreferences sharedPreferences = context.getSharedPreferences("TaiKhoanDangNhap", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("SessionLogin", arr[0]);
                        editor.commit();
                    }
                }
                return super.parseNetworkResponse(response);

            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                //params.put("Content-Type", "application/json; charset=utf-8");
                return checkParams(params);
            }

        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(strReq);
    }

    public static void registerVoice(final Context context, String param, final GetSuccess getSuccess) {
        StringRequest strReq = new StringRequest(Request.Method.GET,
                param , new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    getSuccess.onResponse(jsonObject);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                try {
                    getSuccess.onError(error);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return checkParams(params);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                //params.put("Content-Type", "application/json; charset=utf-8");
                return checkParams(params);
            }

        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(strReq);
    }

    public static String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    public static void uploadImage(final Context context, Bitmap bitmap, final GetSuccess getSuccess){

        final String file = getStringImage(bitmap);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "upload", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    getSuccess.onResponse(jsonObject);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                try {
                    getSuccess.onError(error);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("filename", file);
                params.put("name", "file");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = context.getSharedPreferences("TaiKhoanDangNhap", Context.MODE_PRIVATE);
                String session = sharedPreferences.getString("SessiionLogin", "");
                Map<String, String> params = new HashMap<>();
                params.put("cookie", session);
                return super.getHeaders();
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }


    private static Map<String, String> checkParams(Map<String, String> map){
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> pairs = (Map.Entry<String, String>)it.next();
            if(pairs.getValue()==null){
                map.put(pairs.getKey(), "");
            }
        }
        return map;
    }


    public interface GetSuccess {
        void onResponse(JSONObject response) throws JSONException;
        void onResponse(String response) throws JSONException;
        void onError(VolleyError response) throws JSONException;
    }
}
