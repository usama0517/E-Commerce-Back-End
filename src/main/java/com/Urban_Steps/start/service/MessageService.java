package com.Urban_Steps.start.service;

import com.Urban_Steps.start.dto.MessageDTO;
import com.Urban_Steps.start.exception.BuyerNotFoundException;
import com.Urban_Steps.start.exception.SellerNotFoundException;
import com.Urban_Steps.start.exception.ProductNotFoundException;
import com.Urban_Steps.start.model.Buyer;
import com.Urban_Steps.start.model.Message;
import com.Urban_Steps.start.model.Product;
import com.Urban_Steps.start.model.Seller;
import com.Urban_Steps.start.repository.BuyerRepository;
import com.Urban_Steps.start.repository.MessageRepository;
import com.Urban_Steps.start.repository.ProductRepository;
import com.Urban_Steps.start.repository.SellerRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final BuyerRepository buyerRepository;
    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;

    public MessageService(MessageRepository messageRepository, BuyerRepository buyerRepository,
                          SellerRepository sellerRepository, ProductRepository productRepository) {
        this.messageRepository = messageRepository;
        this.buyerRepository = buyerRepository;
        this.sellerRepository = sellerRepository;
        this.productRepository = productRepository;
    }

    // Send a message and return the full conversation
    public List<MessageDTO> sendMessage(MessageDTO messageRequest) {
        Buyer buyer = buyerRepository.findById(messageRequest.getBuyerId())
                .orElseThrow(() -> new BuyerNotFoundException("Buyer not found with ID: " + messageRequest.getBuyerId()));

        Seller seller = sellerRepository.findById(messageRequest.getSellerId())
                .orElseThrow(() -> new SellerNotFoundException("Seller not found with ID: " + messageRequest.getSellerId()));

        Product product = productRepository.findById(messageRequest.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + messageRequest.getProductId()));

        Message message = new Message(messageRequest.getMessageText(), buyer, seller, product);
        messageRepository.save(message);

        return getConversation(messageRequest.getBuyerId(), messageRequest.getSellerId());
    }

    // Get conversation between buyer and seller
    public List<MessageDTO> getConversation(Long buyerId, Long sellerId) {
        List<Message> messages = messageRepository.findBySender_BuyerIdAndReceiver_SellerIdOrderBySentAt(buyerId, sellerId);

        // Check if conversation is empty
        if (messages.isEmpty()) {
            throw new RuntimeException("No messages found for the conversation between buyer ID " + buyerId + " and seller ID " + sellerId);
        }

        return messages.stream()
                .sorted(Comparator.comparing(Message::getSentAt))
                .map(msg -> new MessageDTO(msg.getMessageId(), msg.getMessageText(), msg.getSentAt(),
                        msg.getSender().getBuyerId(), msg.getReceiver().getSellerId(), msg.getProduct().getProductId()))
                .collect(Collectors.toList());
    }
}