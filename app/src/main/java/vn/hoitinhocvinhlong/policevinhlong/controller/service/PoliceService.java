package vn.hoitinhocvinhlong.policevinhlong.controller.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.List;

import vn.hoitinhocvinhlong.policevinhlong.R;
import vn.hoitinhocvinhlong.policevinhlong.controller.activity.main.MainActivity;
import vn.hoitinhocvinhlong.policevinhlong.model.ThanhVien;
import vn.hoitinhocvinhlong.policevinhlong.model.TinNhan;

/**
 * Created by Long on 9/22/2017.
 */

public class PoliceService extends Service {

    private Socket mSocket;
    private int mId;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mId = intent.getIntExtra("ID", 0);
        try {
            mSocket = IO.socket("http://police.xekia.vn:9020/");
            mSocket.connect();
            connectChat();
            mSocket.on("onnewmessage", onNewMessage);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return START_STICKY;
    }

    private void connectChat(){
        mSocket.emit("join-myroom", mId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mSocket.off("join-myroom");
        mSocket.disconnect();
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String json = String.valueOf(args[0]);
            try {
                JSONArray jsonArray = new JSONArray(json);
                String jsonTinNhan = String.valueOf(jsonArray.get(0));
                JSONObject jsonObjectTinNhan = new JSONObject(jsonTinNhan);
                int id = jsonObjectTinNhan.getInt("id");
                int iduser = jsonObjectTinNhan.getInt("iduser");
                String noidung = jsonObjectTinNhan.getString("noidung");
                String hinhanh = jsonObjectTinNhan.getString("hinhanh");
                String video = jsonObjectTinNhan.getString("video");
                List<String> hinhanhList = new Gson().fromJson(hinhanh, List.class);
                List<String> videoList = new Gson().fromJson(video, List.class);
                double lat = jsonObjectTinNhan.getDouble("lat");
                double lng = jsonObjectTinNhan.getDouble("lng");
                int nhiemvu = jsonObjectTinNhan.getInt("nhiemvu");
                long thoigiantao = jsonObjectTinNhan.getLong("thoigiantao");
                String jsonUser = String.valueOf(jsonObjectTinNhan.getString("user"));
                ThanhVien thanhVien = new Gson().fromJson(jsonUser, ThanhVien.class);
                TinNhan tinNhan = new TinNhan();
                tinNhan.setId(id);
                tinNhan.setIduser(iduser);
                tinNhan.setNoidung(noidung);
                tinNhan.setHinhanh(hinhanhList);
                tinNhan.setVideo(videoList);
                tinNhan.setLat(lat);
                tinNhan.setLng(lng);
                tinNhan.setNhiemvu(nhiemvu);
                tinNhan.setThoigiantao(thoigiantao);
                tinNhan.setUser(jsonUser);
                createNotification(thanhVien, tinNhan);
                Intent iBroadcastTinNhan = new Intent("vn.hoitinhocvinhlong.broadcast.tinnhan");
                iBroadcastTinNhan.putExtra("TinNhan", tinNhan);
                sendBroadcast(iBroadcastTinNhan);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };


    private void createNotification(ThanhVien thanhVien, TinNhan tinNhan){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(thanhVien.getTen())
                .setContentText(tinNhan.getNoidung())
                .setAutoCancel(true);
        Uri uriSound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.ring);
        mBuilder.setSound(uriSound);
        Intent iMain = new Intent(this, MainActivity.class);
        iMain.putExtra("Position", tinNhan.getNhiemvu() - 1);
        iMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, tinNhan.getId(), iMain, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(tinNhan.getId(), mBuilder.build());
    }


}
