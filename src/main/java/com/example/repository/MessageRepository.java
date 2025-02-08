package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Marks this interface as a Spring Data repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    // Custom method to find messages by a specific user
    List<Message> findByPostedBy(Integer postedBy);
}
