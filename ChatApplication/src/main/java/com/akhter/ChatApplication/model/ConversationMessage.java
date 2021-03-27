package com.akhter.ChatApplication.model;

import javax.persistence.*;

@Entity
public class ConversationMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String sender;
    @Column
    private String message;


    public ConversationMessage() {}

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
