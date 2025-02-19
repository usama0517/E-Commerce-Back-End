package com.Urban_Steps.start.controller;

import com.Urban_Steps.start.dto.ProductDTO;
import com.Urban_Steps.start.dto.ProductDetailDTO;
import com.Urban_Steps.start.service.ProductsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product_page")
public class ProductsPageController {

    private final ProductsService productsService;
    public ProductsPageController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping("/sort/{sortType}")
    @CrossOrigin("http://localhost:5173")
    public ResponseEntity<List<ProductDTO>> getSortedProducts(
            @PathVariable String sortType // "newest", "cheapest", "mostexpensive"
    ) {
        List<ProductDTO> products = productsService.getSortedProducts(sortType);
        return ResponseEntity.ok(products);
    }
    //catagory click
    //localhost:8080/api/products/sort/{sortType}/category/{category}
    @GetMapping("/sort/{sortType}/category/{category}")
    public ResponseEntity<List<ProductDTO>> getSortedProductsByCategory(
            @PathVariable String sortType, // "newest", "cheapest", "mostexpensive"
            @PathVariable String category  // "casual", "sport", "designer", "other"
    ) {
        List<ProductDTO> products = productsService.getSortedProductsByCategory(sortType, category);
        return ResponseEntity.ok(products);
    }
    //details page
    //localhost:8080/api/products/details/{productId}?buyerId={buyerId}
    @GetMapping("/details/{productId}")
    public ResponseEntity<ProductDetailDTO> getProductDetails(
            @PathVariable Long productId,
            @RequestParam Long buyerId
    ) {
        ProductDetailDTO productDetails = productsService.getProductDetails(productId, buyerId);
        return ResponseEntity.ok(productDetails);
    }
}