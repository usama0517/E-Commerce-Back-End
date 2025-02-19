package com.Urban_Steps.start.dto;

import java.time.LocalDateTime;

public class SellerProfileDTO {
    private Long sellerId;
    private String storeName;
    private String contactPhone;
    private String email;
    private String oldPassword;
    private String newPassword;



    public SellerProfileDTO() {}

    public SellerProfileDTO(Long sellerId, String storeName, String contactPhone,
                            String email) {
        this.sellerId = sellerId;
        this.storeName = storeName;
        this.contactPhone = contactPhone;
        this.email = email;

    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }


}