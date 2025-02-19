package com.Urban_Steps.start.controller;

import com.Urban_Steps.start.dto.ProductDTO;
import com.Urban_Steps.start.service.FavoriteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@CrossOrigin("http://localhost:5173")
public class FavoriteController {
    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    // Toggle favorite endpoint
    @PostMapping
    public ResponseEntity<Boolean> toggleFavorite(@RequestParam Long buyerId, @RequestParam Long productId, @RequestParam boolean isFavorited) {
        boolean newState = favoriteService.toggleFavorite(buyerId, productId, isFavorited);
        return ResponseEntity.ok(newState);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getFavorites(@RequestParam Long buyerId) {
        List<ProductDTO> favorites = favoriteService.getFavoritesByBuyerId(buyerId);
        return ResponseEntity.ok(favorites);
    }
}
