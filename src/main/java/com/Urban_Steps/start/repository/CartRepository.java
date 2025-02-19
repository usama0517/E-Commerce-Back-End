package com.Urban_Steps.start.repository;



import com.Urban_Steps.start.model.Buyer;
import com.Urban_Steps.start.model.Cart;
import com.Urban_Steps.start.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByBuyer_BuyerId(Long buyerId);
    @Query("SELECT COALESCE(SUM(c.quantity), 0) FROM Cart c WHERE c.buyer.buyerId = :buyerId")
    int countCartItemsByBuyerId(@Param("buyerId") Long buyerId);
    boolean existsByBuyer_BuyerIdAndProduct_ProductId(Long buyerId, Long productId);
    Optional<Cart> findByBuyer_BuyerIdAndProduct_ProductId(Long buyerId, Long productId);
    void deleteByProduct_ProductId(Long productId);
    void deleteByBuyer_BuyerId(Long buyerId);



}