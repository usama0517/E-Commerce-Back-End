package com.Urban_Steps.start.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String messageText;

    @Column(nullable = false)
    private LocalDateTime sentAt = LocalDateTime.now();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private Buyer sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private Seller receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    public Message() {}

    public Message(String messageText, Buyer sender, Seller receiver, Product product) {
        this.messageText = messageText;
        this.sender = sender;
        this.receiver = receiver;
        this.product = product;
    }


    public Long getMessageId() { return messageId; }
    public void setMessageId(Long messageId) { this.messageId = messageId; }
    public String getMessageText() { return messageText; }
    public void setMessageText(String messageText) { this.messageText = messageText; }
    public LocalDateTime getSentAt() { return sentAt; }
    public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }
    public Buyer getSender() { return sender; }
    public void setSender(Buyer sender) { this.sender = sender; }
    public Seller getReceiver() { return receiver; }
    public void setReceiver(Seller receiver) { this.receiver = receiver; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
}