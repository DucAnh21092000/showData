package com.giang.imagefirebase;

public class Database {

    private String name;
    private String address;
    private String image;

    private String mssv_khoa;
    private String truong;
    private String vien;
    private String cpa;
    private String hovsten;

    public Database(){

    }

    public Database(String name, String address, String image, String mssv_khoa, String truong, String vien,String cpa, String hovsten) {
        this.name = name;
        this.address = address;
        this.image = image;
        this.mssv_khoa = mssv_khoa;
        this.truong = truong;
        this.vien = vien;
        this.cpa = cpa;
        this.hovsten = hovsten;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMSSV_Khoa() {
        return mssv_khoa;
    }

    public void setMSSV_Khoa(String mssv_khoa) {
        this.mssv_khoa = mssv_khoa;
    }

    public String getTruong() {
        return truong;
    }

    public void setTruong(String truong) {
        this.truong = truong;
    }

    public String getVien() {
        return vien;
    }

    public void setVien(String vien) {
        this.vien = vien;
    }

    public String getCpa() {
        return cpa;
    }

    public void setCpa(String cpa) {
        this.cpa = cpa;
    }

    public String getHovsTen() {
        return hovsten;
    }

    public void setHovsTen(String hovsten) {
        this.hovsten = hovsten;
    }

}
