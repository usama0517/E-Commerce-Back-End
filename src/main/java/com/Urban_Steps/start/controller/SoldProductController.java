package com.Urban_Steps.start.controller;
import com.Urban_Steps.start.dto.ProductDTO;
import com.Urban_Steps.start.service.SoldProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sold")
@CrossOrigin("http://localhost:5173")
public class SoldProductController {

    private final SoldProductService soldProductService;

    public SoldProductController(SoldProductService soldProductService) {
        this.soldProductService = soldProductService;
    }

    // Get buyer's purchased products
    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<List<ProductDTO>> getPurchasedProductsByBuyerId(@PathVariable Long buyerId) {
        List<ProductDTO> products = soldProductService.getPurchasedProductsByBuyerId(buyerId);
        return ResponseEntity.ok(products);
    }

    // Get seller's sold products
    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<ProductDTO>> getSoldProductsBySellerId(@PathVariable Long sellerId) {
        List<ProductDTO> products = soldProductService.getSoldProductsBySellerId(sellerId);
        return ResponseEntity.ok(products);
    }
}