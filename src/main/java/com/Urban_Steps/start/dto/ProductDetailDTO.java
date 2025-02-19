// ProductDetailDTO.java
package com.Urban_Steps.start.dto;

import java.util.List;

public class ProductDetailDTO {
    private Long productId;
    private String name;
    private String brand;
    private String description;
    private Long sellerId;
    private String sellerName;
    private int quantity;
    private boolean isFavorited; // New field
    private boolean isInCart;    // New field
    private List<String> images;
    public ProductDetailDTO() {}

    public ProductDetailDTO(Long productId, String name, String brand, String description, Long sellerId, String sellerName, int quantity, boolean isFavorited, boolean isInCart, List<String> images) {
        this.productId = productId;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.quantity = quantity;
        this.isFavorited = isFavorited;
        this.isInCart = isInCart;
        this.images = images;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public boolean isFavorited() {
        return isFavorited;
    }

    public void setFavorited(boolean favorited) {
        isFavorited = favorited;
    }

    public boolean isInCart() {
        return isInCart;
    }

    public void setInCart(boolean inCart) {
        isInCart = inCart;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}