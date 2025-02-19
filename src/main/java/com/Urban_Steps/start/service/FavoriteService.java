package com.Urban_Steps.start.service;

import com.Urban_Steps.start.dto.ProductDTO;
import com.Urban_Steps.start.exception.BuyerNotFoundException;
import com.Urban_Steps.start.exception.ProductNotFoundException;
import com.Urban_Steps.start.model.Buyer;
import com.Urban_Steps.start.model.Favorite;
import com.Urban_Steps.start.model.Product;
import com.Urban_Steps.start.repository.BuyerRepository;
import com.Urban_Steps.start.repository.FavoriteRepository;
import com.Urban_Steps.start.repository.ProductImageRepository;
import com.Urban_Steps.start.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final BuyerRepository buyerRepository;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    public FavoriteService(FavoriteRepository favoriteRepository, BuyerRepository buyerRepository, ProductRepository productRepository, ProductImageRepository productImageRepository) {
        this.favoriteRepository = favoriteRepository;
        this.buyerRepository = buyerRepository;
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
    }


    public boolean toggleFavorite(Long buyerId, Long productId, boolean isFavorited) {
        Buyer buyer = buyerRepository.findById(buyerId)
                .orElseThrow(() -> new BuyerNotFoundException("Buyer not found with ID: " + buyerId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));

        Optional<Favorite> existingFavorite = favoriteRepository.findByBuyerAndProduct(buyer, product);

        if (!isFavorited) {
            if (existingFavorite.isEmpty()) {
                Favorite newFavorite = new Favorite(buyer, product);
                favoriteRepository.save(newFavorite);
            }
            return true;
        } else {
            existingFavorite.ifPresent(favoriteRepository::delete);
            return false;
        }
    }

    // Get all favorite products for a buyer
    public List<ProductDTO> getFavoritesByBuyerId(Long buyerId) {
        List<Favorite> favorites = favoriteRepository.findByBuyer_buyerId(buyerId);
        return favorites.stream()
                .map(favorite -> {
                    ProductDTO productDTO = mapToProductDTO(favorite.getProduct());
                    String imageUrl = productImageRepository.findFirstImageUrlByProductId(favorite.getProduct().getProductId());
                    productDTO.setImageUrl(imageUrl);
                    return productDTO;
                })
                .collect(Collectors.toList());
    }

    private ProductDTO mapToProductDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setProductId(product.getProductId());
        dto.setName(product.getName());
        dto.setBrand(product.getBrand());
        dto.setPrice(product.getPrice());
        return dto;
    }
}