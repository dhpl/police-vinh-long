package vn.hoitinhocvinhlong.policevinhlong.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Long on 9/21/2017.
 */

public class UploadFileVideo extends AsyncTask<String, String, String> {

    private Context context;
    private PowerManager.WakeLock mWakeLock;
    ProgressDialog mProgressDialog ;
    private static GetSuccess success;

    private static String urlUpload;
    private static String upLoadServerUri = JsonResult.URL +  "uploadvid";
    private static int serverResponseCode = 0;
    private SharedPreferences mSharedPreferences;

    public UploadFileVideo(Context contex, GetSuccess succes)
    {
        context = contex;
        success=succes;
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("A message");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);
        mSharedPreferences = context.getSharedPreferences("TaiKhoanDangNhap", Context.MODE_PRIVATE);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(final String... params) {
        String fileName = params[0];
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "***";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize =  20*2014*1024;
        File sourceFile = new File(params[0]);

        if (!sourceFile.isFile()) {

            ((Activity)context).runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(context, "File không tồn tại", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            try {

                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                final URL url = new URL(upLoadServerUri);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("file", fileName);
                String session = mSharedPreferences.getString("SessionLogin", "");
                if(session!=null) {
                    conn.setRequestProperty("cookie", session);
                }
                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\""
                        + fileName + "\"" + lineEnd);

                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size

                bytesAvailable = fileInputStream.available();
                if (bytesAvailable > maxBufferSize) {
                    ((Activity)context).runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(context, "Dung lượng tối đa là 20mb", Toast.LENGTH_LONG).show();
                        }
                    });

                } else {
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];
                    // read file and write it into form...
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    long all = bytesAvailable;
                    long total = 0;
                    while (bytesRead > 0) {
                        dos.write(buffer, 0, bufferSize);
                        total += bytesRead;
                        publishProgress("" + (int) (total * 100) / all);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    }

                    // send multipart form data necesssary after file data...
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                    // Responses from the server (code and message)
                    serverResponseCode = conn.getResponseCode();
                    String serverResponseMessage = conn.getResponseMessage();

                    Log.i("uploadFile", "HTTP Response is : "
                            + serverResponseMessage + ": " + serverResponseCode);


                    if (serverResponseCode == 200) {
                        final HttpURLConnection finalConn = conn;
                        final CountDownLatch latch = new CountDownLatch(1);
                        ((Activity)context).runOnUiThread(new Runnable() {
                            public void run() {
                                HttpURLConnection finalConn1 = finalConn;
                                InputStream in = null;
                                try {
                                    in = finalConn1.getInputStream();
                                    StringBuilder sb = new StringBuilder();
                                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                                    String read = "";
                                    while ((read = br.readLine()) != null) {
                                        //System.out.println(read);
                                        sb.append(read);
                                    }
                                    Log.e("data string ", sb.toString());
//                                        showUrlFile(sb.toString());
                                    try {
                                        JSONObject object = new JSONObject(sb.toString());
//                                            if(object.getBoolean("status"))
//                                            {
                                        if (object.getInt("code") == 1000) {
                                            urlUpload = object.getJSONObject("data").getString("url");

                                        }
//                                            }
//
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    br.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                latch.countDown();

                            }
                        });
                        try {
                            latch.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
//                            runOnUiThread(t);
//                            t.join();
                    }
                    //close the streams //
                    fileInputStream.close();
                    dos.flush();
                    dos.close();
                }
            } catch (MalformedURLException ex) {


                ex.printStackTrace();

//                runOnUiThread(new Runnable() {
//                    public void run() {
//                        messageText.setText("MalformedURLException Exception : check script url.");
//                        Toast.makeText(UploadToServer.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
//                    }
//                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {

                e.printStackTrace();
            }

        } // End else block
        return null;
    }

    @Override
    protected void onPostExecute(String unused) {
        success.onPostExcute(unused,urlUpload);
    }

    public interface GetSuccess {
        void onPostExcute(JSONObject response);
        void onPostExcute(String unused,String response) ;
        void onError(VolleyError response) ;
    }
}