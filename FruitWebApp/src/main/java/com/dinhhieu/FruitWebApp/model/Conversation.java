package com.dinhhieu.FruitWebApp.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "conversation_name")
    private String conversationName;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;



}
