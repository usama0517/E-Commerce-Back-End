package com.Urban_Steps.start.service;

import com.Urban_Steps.start.dto.LoginRequestDTO;
import com.Urban_Steps.start.dto.SellerProfileDTO;
import com.Urban_Steps.start.dto.SellerSignUpRequestDTO;
import com.Urban_Steps.start.exception.EmailAlreadyRegisteredException;
import com.Urban_Steps.start.exception.InvalidLoginException;
import com.Urban_Steps.start.exception.InvalidPasswordException;
import com.Urban_Steps.start.exception.SellerNotFoundException;
import com.Urban_Steps.start.model.Seller;
import com.Urban_Steps.start.repository.MessageRepository;
import com.Urban_Steps.start.repository.ProductRepository;
import com.Urban_Steps.start.repository.SellerRepository;
import com.Urban_Steps.start.repository.SoldProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SellerService {

    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;
    private final MessageRepository messageRepository;
    private final SoldProductRepository soldProductRepository;

    public SellerService(SellerRepository sellerRepository, ProductRepository productRepository,
                         MessageRepository messageRepository, SoldProductRepository soldProductRepository) {
        this.sellerRepository = sellerRepository;
        this.productRepository = productRepository;
        this.messageRepository = messageRepository;
        this.soldProductRepository = soldProductRepository;
    }



    public Long signUpSeller(SellerSignUpRequestDTO signUpRequest) {
        // Check if email is already registered
        if (sellerRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new EmailAlreadyRegisteredException("Email already registered: " + signUpRequest.getEmail());
        }

        Seller seller = new Seller();
        seller.setStoreName(signUpRequest.getStoreName());
        seller.setEmail(signUpRequest.getEmail());
        seller.setContactPhone(signUpRequest.getPhoneNumber());
        seller.setPasswordHash(signUpRequest.getPassword());

        Seller savedSeller = sellerRepository.save(seller);
        return savedSeller.getSellerId();
    }

    public Long loginSeller(LoginRequestDTO loginRequest) {

        Seller seller = sellerRepository.findByEmail(loginRequest.getEmail());
        if (seller == null || !seller.getPasswordHash().equals(loginRequest.getPassword())) {
            throw new InvalidLoginException("Invalid email or password");
        }


        return seller.getSellerId();
    }

    @Transactional
    public SellerProfileDTO updateSellerProfile(Long sellerId, SellerProfileDTO profileDTO) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new SellerNotFoundException("Seller not found with ID: " + sellerId));


        if (!seller.getPasswordHash().equals(profileDTO.getOldPassword())) {
            throw new InvalidPasswordException("Old password is incorrect");
        }


        if (profileDTO.getStoreName() != null) {
            seller.setStoreName(profileDTO.getStoreName());
        }
        if (profileDTO.getContactPhone() != null) {
            seller.setContactPhone(profileDTO.getContactPhone());
        }
        if (profileDTO.getEmail() != null) {
            seller.setEmail(profileDTO.getEmail());
        }
        if (profileDTO.getNewPassword() != null && !profileDTO.getNewPassword().isEmpty()) {
            seller.setPasswordHash(profileDTO.getNewPassword());
        }

        sellerRepository.save(seller);

        return new SellerProfileDTO(
                seller.getSellerId(),
                seller.getStoreName(),
                seller.getContactPhone(),
                seller.getEmail()

        );
    }

    @Transactional
    public void deleteSeller(Long sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new SellerNotFoundException("Seller not found with ID: " + sellerId));

        // Delete all seller-related data
        productRepository.deleteBySeller_SellerId(sellerId);
        messageRepository.deleteByReceiver_SellerId(sellerId);

        sellerRepository.delete(seller);
    }

    public SellerProfileDTO getSellerProfile(Long sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new SellerNotFoundException("Seller not found with ID: " + sellerId));

        return new SellerProfileDTO(
                seller.getSellerId(),
                seller.getStoreName(),
                seller.getContactPhone(),
                seller.getEmail()
        );
    }
}