package com.example.demo.controllers;

import com.example.demo.exceptions.BootcampException;
import com.example.demo.model.ChatThread;
import com.example.demo.services.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ThreadController {



    private final ThreadService threadService;

    @Autowired
    public ThreadController(ThreadService threadService) {
        this.threadService = threadService;
    }




    @GetMapping(value = "/thread")
    public ArrayList<ChatThread> getChatThread() throws BootcampException {

        return threadService.getThreadChat();
    }

    @GetMapping(value = "/thread/{id}")
    public Optional<ChatThread> getChatThreadById(@PathVariable Long id) throws BootcampException {
        return threadService.getThreadChatById(id);
    }

    @PostMapping(value ="/thread")
    public ChatThread saveThread(@RequestBody ChatThread thread) throws BootcampException {

        return threadService.saveThread(thread);

    }




    @GetMapping(value = "/thread/user/{user_id}")
    public List<ChatThread> getUserThreads(@PathVariable Long user_id) throws BootcampException {
        return threadService.userThread(user_id);
    }




}
