package vn.hoitinhocvinhlong.policevinhlong.model;

/**
 * Created by Long on 9/18/2017.
 */

public class TinNhan {

    private String mTen;
    private String mDiaChi;
    private int mHinhAnh;
    private double mLat;
    private double mLng;

    public TinNhan(String ten, String diaChi, int hinhAnh, double lat, double lng) {
        mTen = ten;
        mDiaChi = diaChi;
        mHinhAnh = hinhAnh;
        mLat = lat;
        mLng = lng;
    }

    public String getTen() {
        return mTen;
    }

    public void setTen(String ten) {
        mTen = ten;
    }

    public String getDiaChi() {
        return mDiaChi;
    }

    public void setDiaChi(String diaChi) {
        mDiaChi = diaChi;
    }

    public int getHinhAnh() {
        return mHinhAnh;
    }

    public void setHinhAnh(int hinhAnh) {
        mHinhAnh = hinhAnh;
    }

    public double getLat() {
        return mLat;
    }

    public void setLat(double lat) {
        mLat = lat;
    }

    public double getLng() {
        return mLng;
    }

    public void setLng(double lng) {
        mLng = lng;
    }
}
