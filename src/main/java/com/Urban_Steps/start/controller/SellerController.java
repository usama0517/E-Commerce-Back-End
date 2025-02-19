package com.Urban_Steps.start.controller;

import com.Urban_Steps.start.dto.LoginRequestDTO;
import com.Urban_Steps.start.dto.SellerProfileDTO;
import com.Urban_Steps.start.dto.SellerSignUpRequestDTO;
import com.Urban_Steps.start.model.Seller;
import com.Urban_Steps.start.service.SellerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/sellers")
@CrossOrigin("http://localhost:5173")
public class SellerController {

    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }


//    @GetMapping
//    public ResponseEntity<List<Seller>> getAllSellers() {
//        List<Seller> sellers = sellerService.getAllSellers();
//        if (sellers.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // 204 if no sellers found
//        }
//        return new ResponseEntity<>(sellers, HttpStatus.OK);  // 200 if sellers found
//    }
//    @GetMapping("/{sellerId}")
//    public ResponseEntity<Seller> getSellerById(@PathVariable Long sellerId) {
//        Seller seller = sellerService.getSellerById(sellerId);
//        if (seller == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // 404 if seller not found
//        }
//        return new ResponseEntity<>(seller, HttpStatus.OK);  // 200 if seller found
//    }

    @PutMapping("/{sellerId}/profile")
    public ResponseEntity<SellerProfileDTO> updateSellerProfile(
            @PathVariable Long sellerId,
            @RequestBody SellerProfileDTO profileDTO
    ) {
        SellerProfileDTO updatedProfile = sellerService.updateSellerProfile(sellerId, profileDTO);
        return ResponseEntity.ok(updatedProfile);
    }
    @GetMapping("/{sellerId}")
    public ResponseEntity<SellerProfileDTO> getSellerProfile(@PathVariable Long sellerId) {
        SellerProfileDTO profile = sellerService.getSellerProfile(sellerId);
        return ResponseEntity.ok(profile);
    }
    @DeleteMapping("/{sellerId}")
    public ResponseEntity<Void> deleteSeller(@PathVariable Long sellerId) {
        sellerService.deleteSeller(sellerId);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/signup")
    public ResponseEntity<Long> signUpSeller(@RequestBody SellerSignUpRequestDTO signUpRequest) {
        Long sellerId = sellerService.signUpSeller(signUpRequest);
        return ResponseEntity.ok(sellerId);
    }
    @PostMapping("/login")
    public ResponseEntity<Long> loginSeller(@RequestBody LoginRequestDTO loginRequest) {
        Long sellerId = sellerService.loginSeller(loginRequest);
        return ResponseEntity.ok(sellerId);
    }
}
