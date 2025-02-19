package com.Urban_Steps.start.service;

import com.Urban_Steps.start.dto.CartItemDTO;
import com.Urban_Steps.start.dto.CartPageDTO;
import com.Urban_Steps.start.exception.BuyerNotFoundException;
import com.Urban_Steps.start.exception.CartItemNotFoundException;
import com.Urban_Steps.start.exception.ProductNotFoundException;
import com.Urban_Steps.start.model.Buyer;
import com.Urban_Steps.start.model.Cart;
import com.Urban_Steps.start.model.Product;
import com.Urban_Steps.start.repository.BuyerRepository;
import com.Urban_Steps.start.repository.CartRepository;
import com.Urban_Steps.start.repository.ProductImageRepository;
import com.Urban_Steps.start.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final BuyerRepository buyerRepository;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    public CartService(CartRepository cartRepository, BuyerRepository buyerRepository, ProductRepository productRepository, ProductImageRepository productImageRepository) {
        this.cartRepository = cartRepository;
        this.buyerRepository = buyerRepository;
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
    }

    public Cart addToCart(Long buyerId, Long productId, Integer quantity) {
        Buyer buyer = buyerRepository.findById(buyerId)
                .orElseThrow(() -> new BuyerNotFoundException("Buyer not found with ID: " + buyerId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));

        Cart cartItem = cartRepository.findByBuyer_BuyerIdAndProduct_ProductId(buyerId, productId)
                .orElse(new Cart(0, buyer, product));

        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        return cartRepository.save(cartItem);
    }

    public void deleteCartItem(Long cartItemId) {
        if (!cartRepository.existsById(cartItemId)) {
            throw new CartItemNotFoundException("Cart item not found with ID: " + cartItemId);
        }
        cartRepository.deleteById(cartItemId);
    }

    public CartPageDTO getCartPage(Long buyerId) {
        List<Cart> cartItems = cartRepository.findByBuyer_BuyerId(buyerId);


        List<CartItemDTO> cartItemDTOs = cartItems.stream()
                .map(cartItem -> {
                    CartItemDTO cartItemDTO = mapToCartItemDTO(cartItem);
                    String imageUrl = productImageRepository.findFirstImageUrlByProductId(cartItem.getProduct().getProductId());
                    cartItemDTO.setImageUrl(imageUrl);
                    return cartItemDTO;
                })
                .collect(Collectors.toList());


        double totalPrice = cartItemDTOs.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        return new CartPageDTO(cartItemDTOs, totalPrice);
    }

    private CartItemDTO mapToCartItemDTO(Cart cart) {
        return new CartItemDTO(
                cart.getProduct().getProductId(),
                cart.getProduct().getName(),
                cart.getProduct().getBrand(),
                cart.getQuantity(),
                cart.getProduct().getPrice()
        );
    }
}