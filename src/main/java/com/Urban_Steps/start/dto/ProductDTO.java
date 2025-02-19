package com.Urban_Steps.start.dto;
import java.time.LocalDateTime;
import java.util.List;

public class ProductDTO {
    private Long productId;
    private String name;
    private String brand;
    private String description;
    private Double price;
    private String category;
    private int quantity;
    private LocalDateTime createdAt;
    private Long sellerId;
    //
    private LocalDateTime soldAt;
    private Long buyerId;
    //
    private List<String> images;
    private String imageUrl;
    public ProductDTO() {
    }
 //product with image list
    public ProductDTO(Long productId, String name, String brand, String description, Double price, String category, int quantity, LocalDateTime createdAt, Long sellerId, List<String> images) {
        this.productId = productId;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.sellerId = sellerId;
        this.images = images;
    }
//normal product
    public ProductDTO(Long productId, String name, String brand, String description, Double price, String category, int quantity, LocalDateTime createdAt, Long sellerId) {
        this.productId = productId;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.sellerId = sellerId;
    }
//sold product
public ProductDTO(Long productId, String name, String brand, String description, Double price, String category,  LocalDateTime soldAt, Long sellerId, Long buyerId
) {
    this.productId = productId;
    this.name = name;
    this.brand = brand;
    this.description = description;
    this.price = price;
    this.category = category;
    this.soldAt = soldAt;
    this.sellerId = sellerId;
    this.buyerId = buyerId;
}
    public Long getBuyerId() {return buyerId;}
    public void setBuyerId(Long buyerId) {this.buyerId = buyerId;}
    public LocalDateTime getSoldAt() {return soldAt;}
    public void setSoldAt(LocalDateTime soldAt) {this.soldAt = soldAt;}
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public Long getSellerId() { return sellerId; }
    public void setSellerId(Long sellerId) { this.sellerId = sellerId; }
    public int getQuantity() {return quantity;}
    public void setQuantity(int quantity) {this.quantity = quantity;}
    public List<String> getImages() {return images;}
    public void setImages(List<String> images) {this.images = images;}
    public String getImageUrl() {return imageUrl;}
    public void setImageUrl(String imageUrl) {this.imageUrl = imageUrl;}
}