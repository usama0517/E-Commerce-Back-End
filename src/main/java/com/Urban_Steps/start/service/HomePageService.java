package com.Urban_Steps.start.service;

import com.Urban_Steps.start.dto.HomeDTO;
import com.Urban_Steps.start.dto.ProductDTO;
import com.Urban_Steps.start.dto.ProfileDTO;
import com.Urban_Steps.start.exception.BuyerNotFoundException;
import com.Urban_Steps.start.model.Buyer;
import com.Urban_Steps.start.model.Product;
import com.Urban_Steps.start.repository.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomePageService {

    private final ProductRepository productRepository;
    private final BuyerRepository buyerRepository;
    private final CartRepository cartRepository;
    private final FavoriteRepository favoriteRepository;
    private final ProductImageRepository productImageRepository;

    public HomePageService(ProductRepository productRepository, BuyerRepository buyerRepository, CartRepository cartRepository, FavoriteRepository favoriteRepository, ProductImageRepository productImageRepository) {
        this.productRepository = productRepository;
        this.buyerRepository = buyerRepository;
        this.cartRepository = cartRepository;
        this.favoriteRepository = favoriteRepository;
        this.productImageRepository = productImageRepository;
    }

    public HomeDTO getHomePageData() {
        List<Product> latestProducts = productRepository.findTop8ByOrderByCreatedAtDesc();

        // Check if products are found
        if (latestProducts.isEmpty()) {
            throw new RuntimeException("No products found for the home page");
        }

        List<ProductDTO> productDTOs = latestProducts.stream()
                .map(product -> {
                    ProductDTO productDTO = mapToProductDTO(product);
                    String imageUrl = productImageRepository.findFirstImageUrlByProductId(product.getProductId());
                    productDTO.setImageUrl(imageUrl);
                    return productDTO;
                })
                .collect(Collectors.toList());

        return new HomeDTO(productDTOs);
    }

    private ProductDTO mapToProductDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setProductId(product.getProductId());
        dto.setName(product.getName());
        dto.setBrand(product.getBrand());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setCategory(product.getCategory());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setQuantity(product.getQuantity());
        dto.setSellerId(product.getSeller().getSellerId());
        return dto;
    }

    public ProfileDTO getProfileData(Long buyerId) {
        // Check if buyer exists
        Buyer buyer = buyerRepository.findById(buyerId)
                .orElseThrow(() -> new BuyerNotFoundException("Buyer not found with ID: " + buyerId));

        int cartItemsCount = cartRepository.countCartItemsByBuyerId(buyerId);
        int favoriteItemsCount = favoriteRepository.countFavoritesByBuyerId(buyerId);

        return new ProfileDTO(
                buyer.getBuyerId(),
                buyer.getFirstName(),
                buyer.getEmail(),
                cartItemsCount,
                favoriteItemsCount
        );
    }
}