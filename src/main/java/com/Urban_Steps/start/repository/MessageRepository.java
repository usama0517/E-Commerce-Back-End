package com.Urban_Steps.start.repository;
import com.Urban_Steps.start.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByProduct_ProductId(Long productId);
    List<Message> findBySender_BuyerIdAndReceiver_SellerIdOrderBySentAt(Long buyerId, Long sellerId);
    void deleteByReceiver_SellerId(Long sellerId);

}