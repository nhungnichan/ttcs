package com.example.duan_client.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class OrderDish implements Parcelable, Serializable {
    private Dish dish;
    private int quantity;

    public OrderDish() {
    }

    public OrderDish(Dish dish, int quantity) {
        this.dish = dish;
        this.quantity = quantity;
    }

    protected OrderDish(Parcel in) {
        dish = in.readParcelable(Dish.class.getClassLoader());
        quantity = in.readInt();
    }

    public static final Creator<OrderDish> CREATOR = new Creator<OrderDish>() {
        @Override
        public OrderDish createFromParcel(Parcel in) {
            return new OrderDish(in);
        }

        @Override
        public OrderDish[] newArray(int size) {
            return new OrderDish[size];
        }
    };

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(dish, flags);
        dest.writeInt(quantity);
    }
}
