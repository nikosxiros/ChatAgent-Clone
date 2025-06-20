package com.example.demo.repositories;

import com.example.demo.model.ChatThread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ThreadRepository extends JpaRepository<ChatThread, Long> {




    @Query(nativeQuery = true, value = " SELECT * FROM thread WHERE thread_id =?1 ")
    Optional<ChatThread> findChatThreadById(Long id);











}
