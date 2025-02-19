package com.Urban_Steps.start.service;

import com.Urban_Steps.start.dto.BuyerSignUpRequestDTO;
import com.Urban_Steps.start.dto.LoginRequestDTO;
import com.Urban_Steps.start.dto.ProfileDTO;
import com.Urban_Steps.start.exception.BuyerNotFoundException;
import com.Urban_Steps.start.exception.EmailAlreadyRegisteredException;
import com.Urban_Steps.start.exception.InvalidLoginException;
import com.Urban_Steps.start.exception.InvalidPasswordException;
import com.Urban_Steps.start.model.Buyer;
import com.Urban_Steps.start.repository.BuyerRepository;
import com.Urban_Steps.start.repository.CartRepository;
import com.Urban_Steps.start.repository.FavoriteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class BuyerService {
    private final BuyerRepository buyerRepository;
    private final CartRepository cartRepository;
    private final FavoriteRepository favoriteRepository;


    public BuyerService(BuyerRepository buyerRepository, CartRepository cartRepository, FavoriteRepository favoriteRepository) {
        this.buyerRepository = buyerRepository;
        this.cartRepository = cartRepository;
        this.favoriteRepository = favoriteRepository;
    }

    public ProfileDTO getBuyerProfile(Long buyerId) {
        Buyer buyer = buyerRepository.findById(buyerId)
                .orElseThrow(() -> new BuyerNotFoundException("Buyer not found with ID: " + buyerId));

        int cartItemsCount = cartRepository.countCartItemsByBuyerId(buyerId);
        int favoriteItemsCount = favoriteRepository.countFavoritesByBuyerId(buyerId);

        return new ProfileDTO(
                buyer.getBuyerId(),
                buyer.getFirstName(),
                buyer.getLastName(),
                buyer.getEmail(),
                cartItemsCount,
                favoriteItemsCount
        );
    }

    public Long signUpBuyer(BuyerSignUpRequestDTO signUpRequest) {
        // Check if email is already registered
        if (buyerRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new EmailAlreadyRegisteredException("Email already registered: " + signUpRequest.getEmail());
        }

        Buyer buyer = new Buyer();
        buyer.setFirstName(signUpRequest.getFirstName());
        buyer.setLastName(signUpRequest.getLastName());
        buyer.setEmail(signUpRequest.getEmail());
        buyer.setPasswordHash(signUpRequest.getPassword()); // Hash the password in production

        Buyer savedBuyer = buyerRepository.save(buyer);
        return savedBuyer.getBuyerId();
    }

    public Long loginBuyer(LoginRequestDTO loginRequest) {
        // Find buyer by email
        Buyer buyer = buyerRepository.findByEmail(loginRequest.getEmail());

        // Validate password
        if (buyer == null || !buyer.getPasswordHash().equals(loginRequest.getPassword())) {
            throw new InvalidLoginException("Invalid email or password");
        }

        // Return buyer ID
        return buyer.getBuyerId();
    }

    @Transactional
    public ProfileDTO updateBuyerProfile(Long buyerId, ProfileDTO profileDTO) {
            Buyer buyer = buyerRepository.findById(buyerId)
                    .orElseThrow(() -> new BuyerNotFoundException("Buyer not found with ID: " + buyerId));

            // Validate old password
            if (!buyer.getPasswordHash().equals(profileDTO.getOldPassword())) { // Replace with password hashing in production
                throw new InvalidPasswordException("Old password is incorrect");
            }

            // Update fields
            buyer.setFirstName(profileDTO.getFirstName());
            buyer.setLastName(profileDTO.getLastName());
            buyer.setEmail(profileDTO.getEmail());

            // Update password if new password is provided
            if (profileDTO.getNewPassword() != null && !profileDTO.getNewPassword().isEmpty()) {
                buyer.setPasswordHash(profileDTO.getNewPassword()); // Hash the password in production
            }

            buyerRepository.save(buyer);

            // Fetch updated counts
            int cartCount = cartRepository.countCartItemsByBuyerId(buyerId);
            int favoriteCount = favoriteRepository.countFavoritesByBuyerId(buyerId);

            return new ProfileDTO(
                    buyerId,
                    buyer.getFirstName(),
                    buyer.getLastName(),
                    buyer.getEmail(),
                    cartCount,
                    favoriteCount
            );
        }

        @Transactional
        public void deleteBuyer(Long buyerId) {
            Buyer buyer = buyerRepository.findById(buyerId)
                    .orElseThrow(() -> new BuyerNotFoundException("Buyer not found with ID: " + buyerId));


            cartRepository.deleteByBuyer_BuyerId(buyerId);
            favoriteRepository.deleteByBuyer_BuyerId(buyerId);
            buyerRepository.delete(buyer);
        }
    }
