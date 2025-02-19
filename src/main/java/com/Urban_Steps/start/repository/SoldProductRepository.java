package com.Urban_Steps.start.repository;


import com.Urban_Steps.start.model.SoldProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SoldProductRepository extends JpaRepository<SoldProduct, Long> {

    List<SoldProduct> findByBuyerId(Long buyerId);
    List<SoldProduct> findBySellerId(Long sellerId);
    Optional<SoldProduct> findBySoldProductIdAndBuyerId(Long soldProductId, Long buyerId);

}