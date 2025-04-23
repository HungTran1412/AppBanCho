package dev.mhung.ltmobile.petapplication.request;

import java.util.List;

import dev.mhung.ltmobile.petapplication.OrdersController;

public class OrderRequest {
    private String orderId;
    private String orderDate;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String totalAmount;
    private String status;
    private String note;
    private String payment;
    private List<OrderDetailRequest> orderDetails;

    public OrderRequest(){}
    public OrderRequest(String orderId, String orderDate, String fullName, String email, String phone, String address, String totalAmount, String status, String note, String payment, List<OrderDetailRequest> orderDetails) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.totalAmount = totalAmount;
        this.status = status;
        this.note = note;
        this.payment = payment;
        this.orderDetails = orderDetails;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public List<OrderDetailRequest> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailRequest> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
