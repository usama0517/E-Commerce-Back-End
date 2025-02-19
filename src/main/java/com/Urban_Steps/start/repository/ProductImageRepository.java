package com.Urban_Steps.start.repository;
import com.Urban_Steps.start.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    List<ProductImage> findByProduct_ProductId(Long productId);
    @Query("SELECT pi.imageUrl FROM ProductImage pi WHERE pi.product.productId = :productId ORDER BY pi.imageId ASC LIMIT 1")
    String findFirstImageUrlByProductId(@Param("productId") Long productId);
    void deleteByProduct_ProductId(Long productId);

}