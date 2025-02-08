package com.example.service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Spring service
public class MessageService {
    private final MessageRepository messageRepository;
    
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // Creates a new message
    public ResponseEntity<?> createMessage(Message message) {
        if (message.getMessageText() == null || message.getMessageText().isBlank() || message.getMessageText().length() > 255) {
            return ResponseEntity.badRequest().build(); // Return 400 if validation fails
        }

        if (!accountRepository.existsById(message.getPostedBy())) { // Check if user exists
            return ResponseEntity.badRequest().build(); // Return 400 if user is not found
        }
        
        return ResponseEntity.ok(messageRepository.save(message)); // Save and return the new message
    }

    // Retrieves all messages from the database
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    // Retrieves a message by its id
    public ResponseEntity<?> getMessageById(Integer id) {
        Optional<Message> message = messageRepository.findById(id);
        return message.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.ok().build());
    }

    // Deletes a message by its id
    public ResponseEntity<?> deleteMessage(Integer id) {
        if (messageRepository.existsById(id)) {
            messageRepository.deleteById(id);
            return ResponseEntity.ok(1); // Return 1 to indicate successful deletion
        }
        return ResponseEntity.ok().build(); // Return empty response if doesn't exist
    }

    // Updates a message's text
    public ResponseEntity<?> updateMessage(Integer id, Message updatedMessage) {
        if (updatedMessage.getMessageText() == null || updatedMessage.getMessageText().isBlank() || updatedMessage.getMessageText().length() > 255) {
            return ResponseEntity.badRequest().build(); // Return 400 if validation fails
        }
        return messageRepository.findById(id)
                .map(message -> {
                    message.setMessageText(updatedMessage.getMessageText());
                    messageRepository.save(message);
                    return ResponseEntity.ok(1); // Return 1 to indicate successful update
                })
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    // Retrieves all messages posted by a specific user
    public List<Message> getMessagesByUser(Integer accountId) {
        return messageRepository.findByPostedBy(accountId);
    }
}