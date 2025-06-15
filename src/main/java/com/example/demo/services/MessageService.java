package com.example.demo.services;

import com.example.demo.exceptions.BootcampException;
import com.example.demo.model.Message;
import com.example.demo.model.dto.ChatCompletionRequest;
import com.example.demo.model.dto.ChatCompletionResponse;
import com.example.demo.model.dto.ChatMessage;

import com.example.demo.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
public class MessageService {

private String groqApiKey = "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";


private MessageRepository messageRepository;
@Autowired
public MessageService(MessageRepository messageRepository) {
    this.messageRepository = messageRepository;
}



    public Message createMessageAndGetCompletion(Message newMessage){

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer " + groqApiKey);


        ChatCompletionRequest chatCompletionRequest = new ChatCompletionRequest();
        chatCompletionRequest.setModel(newMessage.getCompletionModel());


        ChatMessage systemMessage = new ChatMessage("system","You are a helpful assistant");
        ChatMessage userMessage = new ChatMessage("user", newMessage.getContent());

        chatCompletionRequest.setMessages(List.of(systemMessage, userMessage));

        HttpEntity<ChatCompletionRequest> httpEntity =
                new HttpEntity<>(chatCompletionRequest, headers);


        System.out.println(chatCompletionRequest);

        ChatCompletionResponse response = restTemplate.postForEntity(
                "https://api.groq.com/openai/v1/chat/completions",
                httpEntity,
                ChatCompletionResponse.class
        ).getBody();


        Message responseMessage=new Message();
        responseMessage.setIsCompletion(true);
        responseMessage.setContent(response.getChoices().get(0).getMessage().getContent());
        responseMessage.setCompletionModel(response.getModel());

        responseMessage.setThreadId(newMessage.getThreadId());


        //save responseMessage in DB
        messageRepository.save(newMessage);
        messageRepository.save(responseMessage);
        //return completion message

        return responseMessage;
    }


    public List<Message> getAllMessages() throws BootcampException {
         return  (List<Message>) messageRepository.findAll();
    }




    public void deleteMessages() throws BootcampException {
         messageRepository.deleteAll();

    }

}
