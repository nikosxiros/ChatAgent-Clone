package com.example.demo.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
    @Basic
    @Column(name="user_id")
    private Long userId;





}
