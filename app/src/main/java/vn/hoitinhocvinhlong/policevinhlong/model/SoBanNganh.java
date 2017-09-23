package vn.hoitinhocvinhlong.policevinhlong.model;

import java.util.List;

/**
 * Created by Long on 9/23/2017.
 */

public class SoBanNganh {

    private int id;
    private String ten;
    private double lat;
    private double lng;
    private String diachi;
    private List<Integer> nhiemvu;
    private String sodienthoai;
    private String cap;
    private String tinh;

    public SoBanNganh() {
    }

    public SoBanNganh(int id, String ten, double lat, double lng, String diachi, List<Integer> nhiemvu, String sodienthoai, String cap, String tinh) {
        this.id = id;
        this.ten = ten;
        this.lat = lat;
        this.lng = lng;
        this.diachi = diachi;
        this.nhiemvu = nhiemvu;
        this.sodienthoai = sodienthoai;
        this.cap = cap;
        this.tinh = tinh;
    }

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

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public List<Integer> getNhiemvu() {
        return nhiemvu;
    }

    public void setNhiemvu(List<Integer> nhiemvu) {
        this.nhiemvu = nhiemvu;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getTinh() {
        return tinh;
    }

    public void setTinh(String tinh) {
        this.tinh = tinh;
    }
}
