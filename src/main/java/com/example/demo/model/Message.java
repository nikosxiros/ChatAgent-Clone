package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="messages")
public class Message {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    public Long id;
    @Basic
    @Column(name="message_text")
    public String content;
    @Basic
    @Column(name="thread_id")
    public Long threadId;
    @Basic
    @Column(name="sender_is_user")
    public Boolean isCompletion;

    public String completionModel="llama3-8b-8192";



}
