package com.Urban_Steps.start.repository;
import com.Urban_Steps.start.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(String category);
    List<Product> findTop8ByOrderByCreatedAtDesc();
    List<Product> findAllByOrderByCreatedAtDesc();      // Newest first
    List<Product> findAllByOrderByPriceAsc();           // Cheapest first
    List<Product> findAllByOrderByPriceDesc();
    List<Product> findByCategoryOrderByCreatedAtDesc(String category); // Newest first
    List<Product> findByCategoryOrderByPriceAsc(String category);      // Cheapest first
    List<Product> findByCategoryOrderByPriceDesc(String category);
    List<Product> findBySeller_SellerId(Long sellerId);
    void deleteBySeller_SellerId(Long sellerId);
}