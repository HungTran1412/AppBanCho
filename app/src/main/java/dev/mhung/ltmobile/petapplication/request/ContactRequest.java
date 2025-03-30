package dev.mhung.ltmobile.petapplication.request;

import dev.mhung.ltmobile.petapplication.service.ContactApiService;

public class ContactRequest {
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String content;

    public ContactRequest(String fullName, String email, String phone, String address, String content) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.content = content;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
