
package com.Urban_Steps.start.dto;

public class CartItemDTO {
    private Long productId;
    private String name;
    private String brand;
    private int quantity;
    private double price;
    private String imageUrl;
    public CartItemDTO() {}

    public CartItemDTO(Long productId, String name, String brand, int quantity, double price, String imageUrl) {
        this.productId = productId;
        this.name = name;
        this.brand = brand;
        this.quantity = quantity;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public CartItemDTO(Long productId, String name, String brand, int quantity, double price) {
        this.productId = productId;
        this.name = name;
        this.brand = brand;
        this.quantity = quantity;
        this.price = price;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
