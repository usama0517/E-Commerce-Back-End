package com.Urban_Steps.start.dto;

import java.time.LocalDateTime;

public class MessageDTO {
    private Long messageId;
    private String messageText;
    private LocalDateTime sentAt;
    private Long buyerId;
    private Long sellerId;
    private Long productId;

    public MessageDTO(){}
    // Constructor for returning message details
    public MessageDTO(Long messageId, String messageText, LocalDateTime sentAt) {
        this.messageId = messageId;
        this.messageText = messageText;
        this.sentAt = sentAt;
    }

    // Constructor for creating a message
    public MessageDTO(Long buyerId, Long sellerId, Long productId, String messageText) {
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.productId = productId;
        this.messageText = messageText;
    }

    // Constructor for returning message details with all data
    public MessageDTO(Long messageId, String messageText, LocalDateTime sentAt, Long buyerId, Long sellerId, Long productId) {
        this.messageId = messageId;
        this.messageText = messageText;
        this.sentAt = sentAt;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.productId = productId;
    }

    // Getters
    public Long getMessageId() { return messageId; }
    public String getMessageText() { return messageText; }
    public LocalDateTime getSentAt() { return sentAt; }
    public Long getBuyerId() { return buyerId; }
    public Long getSellerId() { return sellerId; }
    public Long getProductId() { return productId; }
}


