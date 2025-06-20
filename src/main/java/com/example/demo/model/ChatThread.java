package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "thread")
public class ChatThread {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="thread_id")
    private   Long id;
    @Basic
    @Column(name="name")
    private String threadName;

    @ManyToOne(optional = false)
    @JoinColumn(name="user_id")
    private User user;



    @JsonManagedReference(value = "ChatThread")
    @OneToMany(mappedBy = "threadId",cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<Message> messages = new ArrayList<>();
    private List<Message> messages ;




}
