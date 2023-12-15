package com.example.duan_client.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Dish implements Parcelable, Serializable {
    private String id;
    private String ten;
    private double gia;
    private String img;
    private int soLuong;

    public Dish() {
    }

    public Dish(String id, String ten, double gia, String img) {
        this.id = id;
        this.ten = ten;
        this.gia = gia;
        this.img = img;
    }

    public Dish(String ten, double gia, String img) {
        this.ten = ten;
        this.gia = gia;
        this.img = img;
    }

    protected Dish(Parcel in) {
        id = in.readString();
        ten = in.readString();
        gia = in.readDouble();
        img = in.readString();
        soLuong = in.readInt();
    }

    public static final Creator<Dish> CREATOR = new Creator<Dish>() {
        @Override
        public Dish createFromParcel(Parcel in) {
            return new Dish(in);
        }

        @Override
        public Dish[] newArray(int size) {
            return new Dish[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id='" + id + '\'' +
                ", ten='" + ten + '\'' +
                ", gia=" + gia +
                ", img='" + img + '\'' +
                ", soLuong=" + soLuong +
                '}';
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("gia", gia);
        result.put("ten", ten);
        result.put("img", img);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(ten);
        dest.writeDouble(gia);
        dest.writeString(img);
        dest.writeInt(soLuong);
    }
}
