package com.Urban_Steps.start.dto;

import java.util.List;

public class HomeDTO {
    private List<ProductDTO> latestProducts;

    public HomeDTO() {}

    public HomeDTO(List<ProductDTO> latestProducts) {
        this.latestProducts = latestProducts;
    }

    public List<ProductDTO> getLatestProducts() {
        return latestProducts;
    }

    public void setLatestProducts(List<ProductDTO> latestProducts) {
        this.latestProducts = latestProducts;
    }
}