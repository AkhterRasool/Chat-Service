package com.akhter.ChatApplication.model;

import javax.persistence.*;

@Entity
public class ConversationRecipients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.MERGE)
    private User otherUser;
    @OneToOne(cascade = CascadeType.MERGE)
    private User currUser;

    public ConversationRecipients() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getOtherUser() {
        return otherUser;
    }

    public void setOtherUser(User otherUser) {
        this.otherUser = otherUser;
    }

    public User getCurrUser() {
        return currUser;
    }

    public void setCurrUser(User currUser) {
        this.currUser = currUser;
    }
}
