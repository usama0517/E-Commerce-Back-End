package com.Urban_Steps.start.service;

import com.Urban_Steps.start.dto.ProductDTO;
import com.Urban_Steps.start.dto.ProductDetailDTO;
import com.Urban_Steps.start.exception.BuyerNotFoundException;
import com.Urban_Steps.start.exception.ProductNotFoundException;
import com.Urban_Steps.start.model.Product;
import com.Urban_Steps.start.model.ProductImage;
import com.Urban_Steps.start.repository.CartRepository;
import com.Urban_Steps.start.repository.FavoriteRepository;
import com.Urban_Steps.start.repository.ProductImageRepository;
import com.Urban_Steps.start.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductsService {

    private final ProductRepository productRepository;
    private final FavoriteRepository favoriteRepository;
    private final CartRepository cartRepository;
    private final ProductImageRepository productImageRepository;

    public ProductsService(ProductRepository productRepository, FavoriteRepository favoriteRepository, CartRepository cartRepository, ProductImageRepository productImageRepository) {
        this.productRepository = productRepository;
        this.favoriteRepository = favoriteRepository;
        this.cartRepository = cartRepository;
        this.productImageRepository = productImageRepository;
    }

    public List<ProductDTO> getSortedProducts(String sortType) {
        List<Product> products;

        switch (sortType.toLowerCase()) {
            case "newest":
                products = productRepository.findAllByOrderByCreatedAtDesc();
                break;
            case "cheapest":
                products = productRepository.findAllByOrderByPriceAsc();
                break;
            case "mostexpensive":
                products = productRepository.findAllByOrderByPriceDesc();
                break;
            default:
                throw new IllegalArgumentException("Invalid sort type: " + sortType);
        }

        return products.stream()
                .map(product -> {
                    ProductDTO productDTO = mapToProductDTO(product);
                    String imageUrl = productImageRepository.findFirstImageUrlByProductId(product.getProductId());
                    productDTO.setImageUrl(imageUrl);
                    return productDTO;
                })
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getSortedProductsByCategory(String sortType, String category) {
        List<Product> products;

        switch (sortType.toLowerCase()) {
            case "newest":
                products = productRepository.findByCategoryOrderByCreatedAtDesc(category);
                break;
            case "cheapest":
                products = productRepository.findByCategoryOrderByPriceAsc(category);
                break;
            case "mostexpensive":
                products = productRepository.findByCategoryOrderByPriceDesc(category);
                break;
            default:
                throw new IllegalArgumentException("Invalid sort type: " + sortType);
        }

        return products.stream()
                .map(product -> {
                    ProductDTO productDTO = mapToProductDTO(product);
                    String imageUrl = productImageRepository.findFirstImageUrlByProductId(product.getProductId());
                    productDTO.setImageUrl(imageUrl);
                    return productDTO;
                })
                .collect(Collectors.toList());
    }

    public ProductDetailDTO getProductDetails(Long productId, Long buyerId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));


        if (!favoriteRepository.existsByBuyer_BuyerId(buyerId)) {
            throw new BuyerNotFoundException("Buyer not found with ID: " + buyerId);
        }


        boolean isFavorited = favoriteRepository.existsByBuyer_BuyerIdAndProduct_ProductId(buyerId, productId);
        boolean isInCart = cartRepository.existsByBuyer_BuyerIdAndProduct_ProductId(buyerId, productId);

        List<String> imageUrls = productImageRepository.findByProduct_ProductId(productId)
                .stream()
                .map(ProductImage::getImageUrl)
                .collect(Collectors.toList());

        return new ProductDetailDTO(
                product.getProductId(),
                product.getName(),
                product.getBrand(),
                product.getDescription(),
                product.getSeller().getSellerId(),
                product.getSeller().getStoreName(),
                product.getQuantity(),
                isFavorited,
                isInCart,
                imageUrls
        );
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
        dto.setSellerId(product.getSeller().getSellerId());
        return dto;
    }
}