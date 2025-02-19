package com.Urban_Steps.start.repository;


import com.Urban_Steps.start.model.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Long> {
    boolean existsByEmail(String email);
    Buyer findByEmail(String email);
}