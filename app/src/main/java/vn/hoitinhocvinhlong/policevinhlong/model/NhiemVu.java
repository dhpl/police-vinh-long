package vn.hoitinhocvinhlong.policevinhlong.model;

/**
 * Created by Long on 9/22/2017.
 */

public class NhiemVu {

    private int id;
    private String ten;


    public NhiemVu(int id, String ten) {
        this.id = id;
        this.ten = ten;
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

    @Override
    public String toString() {
        return ten;
    }
}
