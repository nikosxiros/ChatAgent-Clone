package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(onlyExplicitlyIncluded = true)
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


    @JsonBackReference(value = "ChatThread")
    @ManyToOne(optional = false)
    @JoinColumn(name="thread_id")
    public ChatThread threadId;


    @Basic
    @Column(name="sender_is_user")
    public Boolean isCompletion;

    public String completionModel="llama3-8b-8192";




}
