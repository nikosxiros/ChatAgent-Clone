package com.example.demo.services;

import com.example.demo.repositories.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ThreadService {


    private ThreadRepository repo;
    @Autowired
    public ThreadService(ThreadRepository repo) {
        this.repo = repo;
    }
}
