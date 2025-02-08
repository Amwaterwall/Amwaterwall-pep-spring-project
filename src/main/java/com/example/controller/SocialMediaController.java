package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

 @RestController // REST controller, handle HTTP requests
 @RequestMapping // Base path for all endpoints in this controller
public class SocialMediaController {
    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired // Injects dependencies automatically
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    // Registers a new user account
    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@RequestBody Account account) {
        return accountService.registerAccount(account);
    }

    // Logs in a user by verifying username and password
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Account account) {
        return accountService.login(account);
    }

    // Creates a new message
    @PostMapping("/messages")
    public ResponseEntity<?> createMessage(@RequestBody Message message) {
        return messageService.createMessage(message);
    }

    // Retrieves all messages from the database
    @GetMapping("/messages")
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    // Retrieves a specific message by its ID
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<?> getMessageById(@PathVariable Integer messageId) {
        return messageService.getMessageById(messageId);// returning 200 OK even if not found
    }

    // Deletes a message by its ID
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<?> deleteMessage(@PathVariable Integer messageId) {
        return messageService.deleteMessage(messageId); // returning the number of rows affected
    }

    // Updates the text of an existing message
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<?> updateMessage(@PathVariable Integer messageId, @RequestBody Message message) {
        return messageService.updateMessage(messageId, message);
    }

    // Retrieves all messages posted by a specific user
    @GetMapping("/accounts/{accountId}/messages")
    public List<Message> getMessagesByUser(@PathVariable Integer accountId) {
        return messageService.getMessagesByUser(accountId);
    }
}
