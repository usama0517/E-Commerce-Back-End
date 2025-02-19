package com.Urban_Steps.start.service;

import com.Urban_Steps.start.dto.ProductDTO;
import com.Urban_Steps.start.exception.ProductNotFoundException;
import com.Urban_Steps.start.exception.SellerNotFoundException;
import com.Urban_Steps.start.model.Product;
import com.Urban_Steps.start.model.ProductImage;
import com.Urban_Steps.start.model.Seller;
import com.Urban_Steps.start.repository.CartRepository;
import com.Urban_Steps.start.repository.ProductImageRepository;
import com.Urban_Steps.start.repository.ProductRepository;
import com.Urban_Steps.start.repository.SellerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final ProductImageRepository productImageRepository;
    private final CartRepository cartRepository;

    public ProductService(ProductRepository productRepository, SellerRepository sellerRepository, ProductImageRepository productImageRepository, CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
        this.productImageRepository = productImageRepository;
        this.cartRepository = cartRepository;
    }

    public ProductDTO createProduct(Long sellerId, ProductDTO productRequest) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new SellerNotFoundException("Seller not found with ID: " + sellerId));

        Product product = new Product();
        product.setName(productRequest.getName());
        product.setBrand(productRequest.getBrand());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setCategory(productRequest.getCategory());
        product.setSeller(seller);
        product.setQuantity(productRequest.getQuantity());
        product.setCreatedAt(LocalDateTime.now());
        Product savedProduct = productRepository.save(product);

        List<ProductImage> imageEntities = productRequest.getImages().stream()
                .map(imageUrl -> new ProductImage(imageUrl, savedProduct))
                .collect(Collectors.toList());
        productImageRepository.saveAll(imageEntities);

        productRequest.setImages(
                imageEntities.stream().map(ProductImage::getImageUrl).collect(Collectors.toList())
        );
        productRequest.setProductId(savedProduct.getProductId());
        productRequest.setSellerId(savedProduct.getSeller().getSellerId());

        return productRequest;
    }

    @Transactional
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        // Check if product exists
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));

        product.setName(productDTO.getName());
        product.setBrand(productDTO.getBrand());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setCategory(productDTO.getCategory());
        product.setQuantity(productDTO.getQuantity());

        Product updatedProduct = productRepository.save(product);

        productImageRepository.deleteByProduct_ProductId(productId);
        List<ProductImage> imageEntities = productDTO.getImages().stream()
                .map(imageUrl -> new ProductImage(imageUrl, updatedProduct))
                .collect(Collectors.toList());
        productImageRepository.saveAll(imageEntities);

        productDTO.setImages(
                imageEntities.stream().map(ProductImage::getImageUrl).collect(Collectors.toList())
        );
        productDTO.setProductId(updatedProduct.getProductId());
        productDTO.setSellerId(updatedProduct.getSeller().getSellerId());

        return productDTO;
    }

    @Transactional
    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException("Product not found with ID: " + productId);
        }

        productImageRepository.deleteByProduct_ProductId(productId);
        cartRepository.deleteByProduct_ProductId(productId);
        productRepository.deleteById(productId);
    }

    private ProductDTO mapToProductDTO(Product product) {
        return new ProductDTO(
                product.getProductId(),
                product.getName(),
                product.getBrand(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory(),
                product.getQuantity(),
                product.getCreatedAt(),
                product.getSeller().getSellerId()
        );
    }
}