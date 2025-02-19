package com.Urban_Steps.start.repository;
import com.Urban_Steps.start.model.Buyer;
import com.Urban_Steps.start.model.Favorite;
import com.Urban_Steps.start.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    @Query("SELECT COUNT(f) FROM Favorite f WHERE f.buyer.buyerId = :buyerId")
    int countFavoritesByBuyerId(@Param("buyerId") Long buyerId);
    boolean existsByBuyer_BuyerIdAndProduct_ProductId(Long buyerId, Long productId);
    // Find a favorite by buyer and product
    Optional<Favorite> findByBuyerAndProduct(Buyer buyer, Product product);

    // Find all favorites for a specific buyer using buyer.id
    List<Favorite> findByBuyer_buyerId(Long buyerId);
    boolean existsByBuyer_BuyerId(Long buyerId);
    void deleteByBuyer_BuyerId(Long buyerId);
}

