package com.Urban_Steps.start.service;

import com.Urban_Steps.start.dto.ProductDTO;
import com.Urban_Steps.start.exception.BuyerNotFoundException;
import com.Urban_Steps.start.exception.SellerNotFoundException;
import com.Urban_Steps.start.model.SoldProduct;
import com.Urban_Steps.start.repository.BuyerRepository;
import com.Urban_Steps.start.repository.SellerRepository;
import com.Urban_Steps.start.repository.SoldProductRepository;
import com.Urban_Steps.start.repository.ProductImageRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SoldProductService {

    private final SoldProductRepository soldProductRepository;
    private final ProductImageRepository productImageRepository;
    private final BuyerRepository buyerRepository;
    private final SellerRepository sellerRepository;

    public SoldProductService(SoldProductRepository soldProductRepository, ProductImageRepository productImageRepository, BuyerRepository buyerRepository, SellerRepository sellerRepository) {
        this.soldProductRepository = soldProductRepository;
        this.productImageRepository = productImageRepository;
        this.buyerRepository = buyerRepository;
        this.sellerRepository = sellerRepository;
    }

    // Get buyer's purchased products
    public List<ProductDTO> getPurchasedProductsByBuyerId(Long buyerId) {
        // Check if buyer exists
        if (!buyerRepository.existsById(buyerId)) {
            throw new BuyerNotFoundException("Buyer not found with ID: " + buyerId);
        }

        List<SoldProduct> soldProducts = soldProductRepository.findByBuyerId(buyerId);

        // Check if purchased products are found
        if (soldProducts.isEmpty()) {
            throw new RuntimeException("No purchased products found for buyer with ID: " + buyerId);
        }

        return soldProducts.stream()
                .map(soldProduct -> {
                    ProductDTO productDTO = mapToProductDTO(soldProduct);
                    String imageUrl = productImageRepository.findFirstImageUrlByProductId(soldProduct.getSoldProductId());
                    productDTO.setImageUrl(imageUrl);
                    return productDTO;
                })
                .collect(Collectors.toList());
    }

    // Get seller's sold products
    public List<ProductDTO> getSoldProductsBySellerId(Long sellerId) {
        // Check if seller exists
        if (!sellerRepository.existsById(sellerId)) {
            throw new SellerNotFoundException("Seller not found with ID: " + sellerId);
        }

        List<SoldProduct> soldProducts = soldProductRepository.findBySellerId(sellerId);

        // Check if sold products are found
        if (soldProducts.isEmpty()) {
            throw new RuntimeException("No sold products found for seller with ID: " + sellerId);
        }

        return soldProducts.stream()
                .map(soldProduct -> {
                    ProductDTO productDTO = mapToProductDTO(soldProduct);
                    String imageUrl = productImageRepository.findFirstImageUrlByProductId(soldProduct.getSoldProductId());
                    productDTO.setImageUrl(imageUrl);
                    return productDTO;
                })
                .collect(Collectors.toList());
    }

    private ProductDTO mapToProductDTO(SoldProduct soldProduct) {
        return new ProductDTO(
                soldProduct.getSoldProductId(),
                soldProduct.getName(),
                soldProduct.getBrand(),
                soldProduct.getDescription(),
                soldProduct.getPrice(),
                soldProduct.getCategory(),
                soldProduct.getSoldAt(),
                soldProduct.getSellerId(),
                soldProduct.getBuyerId()
        );
    }
}