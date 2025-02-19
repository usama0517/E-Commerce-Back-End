package com.Urban_Steps.start.controller;

import com.Urban_Steps.start.dto.CartPageDTO;
import com.Urban_Steps.start.model.Cart;
import com.Urban_Steps.start.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin("http://localhost:5173")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    //add to cart
    //POST http://localhost:8080/api/cart/{buyerId}/{productId}?quantity={quantity}
    @PostMapping("/{buyerId}/{productId}")
    public ResponseEntity<Long> addToCart(
            @PathVariable Long buyerId,
            @PathVariable Long productId,
            @RequestParam Integer quantity
    ) {
        Cart cartItem = cartService.addToCart(buyerId, productId, quantity);
        return new ResponseEntity<>(cartItem.getCartId(), HttpStatus.CREATED);
    }
    //cart page
    @GetMapping("/{buyerId}")
    public ResponseEntity<CartPageDTO> getCartPage(@PathVariable Long buyerId) {
        CartPageDTO cartPage = cartService.getCartPage(buyerId);
        return ResponseEntity.ok(cartPage);
    }
    //delete btn
    //localhost:8080/api/cart/{buyerId}/items/{cartItemId}
    @DeleteMapping("/{buyerId}/items/{cartItemId}")
    public ResponseEntity<CartPageDTO> deleteCartItem(
            @PathVariable Long buyerId,
            @PathVariable Long cartItemId
    ) {
        cartService.deleteCartItem(cartItemId);
        CartPageDTO updatedCartPage = cartService.getCartPage(buyerId);
        return ResponseEntity.ok(updatedCartPage);
    }
}
