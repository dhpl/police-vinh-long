package vn.hoitinhocvinhlong.policevinhlong.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Long on 9/19/2017.
 */

public class ThanhVien implements Parcelable {

    private int id;
    private String ten;
    private String username;
    private String sdt;
    private String matkhau;
    private String email;
    private double lat;
    private double lng;
    private long thoigiantao;
    private long thoigiancuoicung;

    public ThanhVien() {
    }

    public ThanhVien(int id, String ten, String username, String sdt, String matkhau, String email, double lat, double lng, long thoigiantao, long thoigiancuoicung) {
        this.id = id;
        this.ten = ten;
        this.username = username;
        this.sdt = sdt;
        this.matkhau = matkhau;
        this.email = email;
        this.lat = lat;
        this.lng = lng;
        this.thoigiantao = thoigiantao;
        this.thoigiancuoicung = thoigiancuoicung;
    }

    protected ThanhVien(Parcel in) {
        id = in.readInt();
        ten = in.readString();
        username = in.readString();
        sdt = in.readString();
        matkhau = in.readString();
        email = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
        thoigiantao = in.readLong();
        thoigiancuoicung = in.readLong();
    }

    public static final Creator<ThanhVien> CREATOR = new Creator<ThanhVien>() {
        @Override
        public ThanhVien createFromParcel(Parcel in) {
            return new ThanhVien(in);
        }

        @Override
        public ThanhVien[] newArray(int size) {
            return new ThanhVien[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public long getThoigiantao() {
        return thoigiantao;
    }

    public void setThoigiantao(long thoigiantao) {
        this.thoigiantao = thoigiantao;
    }

    public long getThoigiancuoicung() {
        return thoigiancuoicung;
    }

    public void setThoigiancuoicung(long thoigiancuoicung) {
        this.thoigiancuoicung = thoigiancuoicung;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(ten);
        parcel.writeString(username);
        parcel.writeString(sdt);
        parcel.writeString(matkhau);
        parcel.writeString(email);
        parcel.writeDouble(lat);
        parcel.writeDouble(lng);
        parcel.writeLong(thoigiantao);
        parcel.writeLong(thoigiancuoicung);
    }
}
