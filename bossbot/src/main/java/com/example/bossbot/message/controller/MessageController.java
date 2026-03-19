package com.example.bossbot.message.controller;

import com.example.bossbot.message.dto.CreateMessageRequest;
import com.example.bossbot.message.dto.MessageResponse;
import com.example.bossbot.message.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final MessageService messageService;

    /**
     * Create a new message
     * POST /api/v1/messages
     */
    @PostMapping
    public ResponseEntity<MessageResponse> create(@Valid @RequestBody CreateMessageRequest request) {
        log.info("REST request to create message in conversation: {}", request.getConversationId());
        MessageResponse response = messageService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get a message by ID
     * GET /api/v1/messages/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<MessageResponse> getById(@PathVariable Long id) {
        log.info("REST request to get message by ID: {}", id);
        MessageResponse response = messageService.getById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all messages for a conversation
     * GET /api/v1/messages?conversationId={id}
     */
    @GetMapping
    public ResponseEntity<List<MessageResponse>> getAll(
            @RequestParam Long conversationId) {
        log.info("REST request to get messages. Conversation ID: {}", conversationId);
        List<MessageResponse> responses = messageService.getAll(conversationId);
        return ResponseEntity.ok(responses);
    }

    /**
     * Soft delete a message
     * DELETE /api/v1/messages/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("REST request to delete message with ID: {}", id);
        messageService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
