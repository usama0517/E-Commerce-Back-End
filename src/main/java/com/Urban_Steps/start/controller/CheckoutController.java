package com.Urban_Steps.start.controller;

import com.Urban_Steps.start.service.CheckoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkout")
@CrossOrigin("http://localhost:5173")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/{buyerId}")
    public ResponseEntity<String> checkout(@PathVariable Long buyerId) {
        checkoutService.checkout(buyerId);
        return ResponseEntity.ok("Checkout completed successfully");
    }

}