package dev.mhung.ltmobile.petapplication.request;

import java.util.List;

public class OrderDetailRequest {
    private String order_detail_id;
    private ProductRequest product;
    private int quantity;
    private double price;

    public OrderDetailRequest(){}
    public OrderDetailRequest(String order_detail_id, ProductRequest product, int quantity, double price) {
        this.order_detail_id = order_detail_id;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public String getOrder_detail_id() {
        return order_detail_id;
    }

    public void setOrder_detail_id(String order_detail_id) {
        this.order_detail_id = order_detail_id;
    }

    public ProductRequest getProduct() {
        return product;
    }

    public void setProduct(ProductRequest product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
