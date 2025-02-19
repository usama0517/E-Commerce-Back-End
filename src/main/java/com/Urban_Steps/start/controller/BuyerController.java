package com.Urban_Steps.start.controller;

import com.Urban_Steps.start.dto.BuyerSignUpRequestDTO;
import com.Urban_Steps.start.dto.LoginRequestDTO;
import com.Urban_Steps.start.dto.ProfileDTO;
import com.Urban_Steps.start.model.Buyer;
import com.Urban_Steps.start.service.BuyerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/buyers")
@CrossOrigin("http://localhost:5173")
public class BuyerController {

    private final BuyerService buyerService;

    public BuyerController(BuyerService buyerService) {
        this.buyerService = buyerService;
    }

//    @GetMapping
//    public ResponseEntity<List<Buyer>> getAllBuyers() {
//        List<Buyer> buyers = buyerService.getAllBuyers();
//        return new ResponseEntity<>(buyers, HttpStatus.OK);
//    }
//    @GetMapping("/{buyerId}")
//    public ResponseEntity<Buyer> getBuyerById(@PathVariable Long buyerId) {
//        Buyer buyer = buyerService.getBuyerById(buyerId);
//        if (buyer != null) {
//            return new ResponseEntity<>(buyer, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @PutMapping("/{buyerId}/profile")
    public ResponseEntity<ProfileDTO> updateBuyerProfile(
            @PathVariable Long buyerId,
            @RequestBody ProfileDTO profileDTO
    ) {
        ProfileDTO updatedProfile = buyerService.updateBuyerProfile(buyerId, profileDTO);
        return ResponseEntity.ok(updatedProfile);
    }

    @GetMapping("/{buyerId}")
    public ResponseEntity<ProfileDTO> getBuyerProfile(@PathVariable Long buyerId) {
        ProfileDTO profile = buyerService.getBuyerProfile(buyerId);
        return ResponseEntity.ok(profile);
    }
    // Delete buyer profile
    @DeleteMapping("/{buyerId}")
    public ResponseEntity<Void> deleteBuyer(@PathVariable Long buyerId) {
        buyerService.deleteBuyer(buyerId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/signup")
    public ResponseEntity<Long> signUpBuyer(@RequestBody BuyerSignUpRequestDTO signUpRequest) {
        Long buyerId = buyerService.signUpBuyer(signUpRequest);
        return ResponseEntity.ok(buyerId);
    }
    @PostMapping("/login")
    public ResponseEntity<Long> loginBuyer(@RequestBody LoginRequestDTO loginRequest) {
        Long buyerId = buyerService.loginBuyer(loginRequest);
        return ResponseEntity.ok(buyerId);
    }
}
