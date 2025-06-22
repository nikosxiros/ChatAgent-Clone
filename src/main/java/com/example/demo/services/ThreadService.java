package com.example.demo.services;

import com.example.demo.exceptions.BootcampException;
import com.example.demo.model.ChatThread;
import com.example.demo.repositories.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ThreadService {


    private ThreadRepository repo;
    @Autowired
    public ThreadService(ThreadRepository repo) {
        this.repo = repo;
    }


    public ArrayList<ChatThread> getThreadChat() throws BootcampException {


            return (ArrayList<ChatThread>) repo.findAll();

    }


    public Optional<ChatThread> getThreadChatById(Long id) throws BootcampException {
        return  repo.findById(id);
    }



    public ChatThread saveThread(ChatThread thread) throws BootcampException {
        return repo.save(thread);
    }



    public List<ChatThread> userThread(Long id) throws BootcampException {
        return repo.userThreads(id);
    }

}
