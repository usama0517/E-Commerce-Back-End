package com.Urban_Steps.start.service;

import com.Urban_Steps.start.exception.BuyerNotFoundException;
import com.Urban_Steps.start.exception.CartEmptyException;
import com.Urban_Steps.start.exception.InsufficientProductQuantityException;
import com.Urban_Steps.start.model.Cart;
import com.Urban_Steps.start.model.Product;
import com.Urban_Steps.start.model.SoldProduct;
import com.Urban_Steps.start.repository.BuyerRepository;
import com.Urban_Steps.start.repository.CartRepository;
import com.Urban_Steps.start.repository.ProductImageRepository;
import com.Urban_Steps.start.repository.ProductRepository;
import com.Urban_Steps.start.repository.SoldProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CheckoutService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final SoldProductRepository soldProductRepository;
    private final ProductImageRepository productImageRepository;
    private final BuyerRepository buyerRepository;

    public CheckoutService(CartRepository cartRepository, ProductRepository productRepository, SoldProductRepository soldProductRepository, ProductImageRepository productImageRepository, BuyerRepository buyerRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.soldProductRepository = soldProductRepository;
        this.productImageRepository = productImageRepository;
        this.buyerRepository = buyerRepository;
    }

    @Transactional
    public void checkout(Long buyerId) {
        // Check if buyer exists
        if (!buyerRepository.existsById(buyerId)) {
            throw new BuyerNotFoundException("Buyer not found with ID: " + buyerId);
        }

        // Fetch all cart items for the buyer
        List<Cart> cartItems = cartRepository.findByBuyer_BuyerId(buyerId);

        // Check if cart is empty
        if (cartItems.isEmpty()) {
            throw new CartEmptyException("Cart is empty for buyer with ID: " + buyerId);
        }

        String imageUrl = "";
        for (Cart cartItem : cartItems) {
            Product product = cartItem.getProduct();
            int quantity = cartItem.getQuantity();

            // Check if product exists
            if (product == null) {
                throw new RuntimeException("Product not found in cart for buyer with ID: " + buyerId);
            }

            // Check if product quantity is sufficient
            if (product.getQuantity() < quantity) {
                throw new InsufficientProductQuantityException(
                        "Insufficient quantity for product: " + product.getName() + ". Available: " + product.getQuantity()
                );
            }


            product.setQuantity(product.getQuantity() - quantity);
            if (product.getQuantity() <= 0) {
                cartRepository.delete(cartItem);
                imageUrl = productImageRepository.findFirstImageUrlByProductId(product.getProductId());
                productImageRepository.deleteByProduct_ProductId(product.getProductId());
                productRepository.delete(product);
            } else {
                productRepository.save(product);
            }


            Optional<SoldProduct> existingSoldProduct = soldProductRepository.findBySoldProductIdAndBuyerId(
                    product.getProductId(), buyerId
            );

            if (existingSoldProduct.isPresent()) {
                // Update quantity if the product is already sold
                SoldProduct soldProduct = existingSoldProduct.get();
                soldProduct.setQuantity(soldProduct.getQuantity() + quantity);
                soldProductRepository.save(soldProduct);
            } else {
                // Create a new sold product entry
                SoldProduct soldProduct = new SoldProduct(
                        product.getProductId(),
                        product.getName(),
                        product.getBrand(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getCategory(),
                        LocalDateTime.now(),
                        buyerId,
                        product.getSeller().getSellerId(),
                        quantity,
                        imageUrl
                );
                soldProductRepository.save(soldProduct);
            }
            cartRepository.delete(cartItem);
        }
    }
}