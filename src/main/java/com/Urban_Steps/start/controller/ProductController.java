package com.Urban_Steps.start.controller;

import com.Urban_Steps.start.dto.ProductDTO;
import com.Urban_Steps.start.model.Product;
import com.Urban_Steps.start.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin("http://localhost:5173")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/{sellerId}")
    public ResponseEntity<ProductDTO> createProduct(
            @PathVariable Long sellerId,
            @RequestBody ProductDTO productRequest
    ) {
        ProductDTO productDTO = productService.createProduct(sellerId, productRequest);
        return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
    }

    // Update a product
    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Long productId,
            @RequestBody ProductDTO productDTO
    ) {
        ProductDTO updatedProduct = productService.updateProduct(productId, productDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    // Delete a product
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
//    @GetMapping
//    public ResponseEntity<List<Product>> getAllProducts() {
//        List<Product> products = productService.getAllProducts();
//        if (products.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // 204 if no products found
//        }
//        return new ResponseEntity<>(products, HttpStatus.OK);  // 200 if products found
//    }
//    @GetMapping("/{productId}")
//    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
//        Product product = productService.getProductById(productId);
//        if (product == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // 404 if product not found
//        }
//        return new ResponseEntity<>(product, HttpStatus.OK);  // 200 if product found
//    }
}
