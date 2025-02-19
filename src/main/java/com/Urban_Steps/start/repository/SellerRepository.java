package com.Urban_Steps.start.repository;


import com.Urban_Steps.start.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    Seller findByEmail(String email);
    boolean existsByEmail(String email);
}