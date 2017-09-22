package vn.hoitinhocvinhlong.policevinhlong.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Long on 9/18/2017.
 */

public class TinNhan implements Parcelable{

    private int id;
    private int iduser;
    private String noidung;
    private List<String> hinhanh;
    private List<String> video;
    private double lat;
    private double lng;
    private int nhiemvu;
    private long thoigiantao;


    public TinNhan() {
    }

    public TinNhan(int id, int iduser, String noidung, List<String> hinhanh, List<String> video, double lat, double lng, int nhiemvu, long thoigiantao) {
        this.id = id;
        this.iduser = iduser;
        this.noidung = noidung;
        this.hinhanh = hinhanh;
        this.video = video;
        this.lat = lat;
        this.lng = lng;
        this.nhiemvu = nhiemvu;
        this.thoigiantao = thoigiantao;
    }

    protected TinNhan(Parcel in) {
        id = in.readInt();
        iduser = in.readInt();
        noidung = in.readString();
        hinhanh = in.createStringArrayList();
        video = in.createStringArrayList();
        lat = in.readDouble();
        lng = in.readDouble();
        nhiemvu = in.readInt();
        thoigiantao = in.readLong();
    }

    public static final Creator<TinNhan> CREATOR = new Creator<TinNhan>() {
        @Override
        public TinNhan createFromParcel(Parcel in) {
            return new TinNhan(in);
        }

        @Override
        public TinNhan[] newArray(int size) {
            return new TinNhan[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public List<String> getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(List<String> hinhanh) {
        this.hinhanh = hinhanh;
    }

    public List<String> getVideo() {
        return video;
    }

    public void setVideo(List<String> video) {
        this.video = video;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getNhiemvu() {
        return nhiemvu;
    }

    public void setNhiemvu(int nhiemvu) {
        this.nhiemvu = nhiemvu;
    }

    public long getThoigiantao() {
        return thoigiantao;
    }

    public void setThoigiantao(long thoigiantao) {
        this.thoigiantao = thoigiantao;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(iduser);
        parcel.writeString(noidung);
        parcel.writeStringList(hinhanh);
        parcel.writeStringList(video);
        parcel.writeDouble(lat);
        parcel.writeDouble(lng);
        parcel.writeInt(nhiemvu);
        parcel.writeLong(thoigiantao);
    }
}
