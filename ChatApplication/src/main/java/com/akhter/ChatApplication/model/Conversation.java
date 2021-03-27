package com.akhter.ChatApplication.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    private ConversationRecipients conversationRecipients;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ConversationMessage> conversationItemList;

    public Conversation() {}

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public List<ConversationMessage> getConversationItemList() {
        return conversationItemList;
    }

    public void setConversationItemList(List<ConversationMessage> conversationItemList) {
        this.conversationItemList = conversationItemList;
    }

    public void setConversationRecipients(ConversationRecipients conversationRecipients) {
        this.conversationRecipients = conversationRecipients;
    }

    public ConversationRecipients getConversationRecipients() {
        return conversationRecipients;
    }
}

