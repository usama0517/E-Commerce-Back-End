package com.Urban_Steps.start.controller;

import com.Urban_Steps.start.dto.MessageDTO;
import com.Urban_Steps.start.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin("http://localhost:5173")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // Send a message and return conversation
    @PostMapping("/send")
    public ResponseEntity<List<MessageDTO>> sendMessage(@RequestBody MessageDTO messageRequest) {
        List<MessageDTO> conversation = messageService.sendMessage(messageRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(conversation);
    }

    // Get conversation
    @GetMapping("/conversation")
    public ResponseEntity<List<MessageDTO>> getConversation(
            @RequestParam Long buyerId,
            @RequestParam Long sellerId) {
        List<MessageDTO> conversation = messageService.getConversation(buyerId, sellerId);
        return ResponseEntity.ok(conversation);
    }
}

