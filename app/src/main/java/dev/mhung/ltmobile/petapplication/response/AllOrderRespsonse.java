package dev.mhung.ltmobile.petapplication.response;

public class AllOrderRespsonse {
    private String orderId;
    private String orderDate;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String note;
    private long totalAmount;
    private String status;

    // Getters
    public String getOrderId() { return orderId; }
    public String getOrderDate() { return orderDate; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getNote() { return note; }
    public long getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }
}
