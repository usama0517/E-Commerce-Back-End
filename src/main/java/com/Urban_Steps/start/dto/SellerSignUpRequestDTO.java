package com.Urban_Steps.start.dto;

public class SellerSignUpRequestDTO {
    private String storeName; // Changed from companyName
    private String email;
    private String phoneNumber;
    private String password; // Changed from passwordHash (we'll hash it later)

    public SellerSignUpRequestDTO() {}

    public SellerSignUpRequestDTO(String storeName, String email, String phoneNumber, String password) {
        this.storeName = storeName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    // Getters and setters
    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}