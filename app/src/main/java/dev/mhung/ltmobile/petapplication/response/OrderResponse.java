package dev.mhung.ltmobile.petapplication.response;

import com.google.gson.annotations.SerializedName;

public class OrderResponse {
    @SerializedName("Tổng tiền")
    private long tongTien;

    @SerializedName("Số đơn")
    private int soDon;

    public long getTongTien() {
        return tongTien;
    }

    public int getSoDon() {
        return soDon;
    }
}
