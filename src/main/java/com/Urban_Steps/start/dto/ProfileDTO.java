package com.Urban_Steps.start.dto;

public class ProfileDTO {
    private Long buyerId;
    private String firstName;
    private String lastName;
    private String email;
    private String newPassword;
    private String oldPassword;
    private int cartItemsCount;
    private int favoriteItemsCount; // New field


    public ProfileDTO() {}
    public ProfileDTO(Long buyerId, String firstName, String lastName, String email, int cartItemsCount, int favoriteItemsCount) {
        this.buyerId = buyerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.cartItemsCount = cartItemsCount;
        this.favoriteItemsCount = favoriteItemsCount;
    }
    public ProfileDTO(Long buyerId, String firstName, String email, int cartItemsCount, int favoriteItemsCount) {
        this.buyerId = buyerId;
        this.firstName = firstName;
        this.email = email;
        this.cartItemsCount = cartItemsCount;
        this.favoriteItemsCount = favoriteItemsCount;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCartItemsCount() {
        return cartItemsCount;
    }

    public void setCartItemsCount(int cartItemsCount) {
        this.cartItemsCount = cartItemsCount;
    }

    public int getFavoriteItemsCount() {
        return favoriteItemsCount;
    }

    public void setFavoriteItemsCount(int favoriteItemsCount) {
        this.favoriteItemsCount = favoriteItemsCount;
    }

    public String getLastName() {return lastName;}

    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getNewPassword() {return newPassword;}

    public void setNewPassword(String newPassword) {this.newPassword = newPassword;}

    public String getOldPassword() {return oldPassword;}

    public void setOldPassword(String oldPassword) {this.oldPassword = oldPassword;}
}