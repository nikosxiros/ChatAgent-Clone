package com.example.demo.controllers;


import com.example.demo.exceptions.BootcampException;
import com.example.demo.model.Message;
import com.example.demo.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MessagesController {

    private final MessageService messageService;

    @Autowired
    public MessagesController(MessageService messageService) {
        this.messageService = messageService;
    }


    @PostMapping("/messages")
    public Message createMessage(@RequestBody Message message){
        //return response from llm
        System.out.println(message);

       Message responseMessage = messageService.createMessageAndGetCompletion(message);
        System.out.println(responseMessage);
       //return message;
       return responseMessage;
    }

    @GetMapping("/messages")
    public List<Message> getAllMessages() throws BootcampException {
       return messageService.getAllMessages();
    }


    @DeleteMapping("/messages")
    public void deleteMessage() throws BootcampException {

        messageService.deleteMessages();
    }











}
