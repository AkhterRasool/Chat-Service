package com.akhter.ChatApplication.service;

import com.akhter.ChatApplication.exception.NoConversationFoundException;
import com.akhter.ChatApplication.model.*;
import com.akhter.ChatApplication.repository.ConversationRepository;
import com.akhter.ChatApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class ChatService {

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private UserRepository userRepository;

    public Conversation loadConversation(String otherUser, String currentUser) throws NoConversationFoundException {
        User otherUserObj = userRepository.findByName(otherUser);
        User currUserObj = userRepository.findByName(currentUser);
        Conversation[] bidirectionalConversation = {
                conversationRepository.findConversation(otherUserObj, currUserObj),
                conversationRepository.findConversation(currUserObj, otherUserObj)
        };
        if (bidirectionalConversation[0] == null && bidirectionalConversation[1] == null) {
            throw new NoConversationFoundException(("Conversation not found."));
        }
        return bidirectionalConversation[0] == null ? bidirectionalConversation[1] : bidirectionalConversation[0];
    }

    public void addMessage(MessageDTO messageDTO) {
        Conversation conversation = null;
        ConversationMessage conversationMessage = new ConversationMessage();
        conversationMessage.setSender(messageDTO.getFrom());
        conversationMessage.setMessage(messageDTO.getMessage());
        try {
            conversation = loadConversation(messageDTO.getTo(), messageDTO.getFrom());
        } catch (NoConversationFoundException e) {
            User otherUserObj = userRepository.findByName(messageDTO.getTo());
            User currUserObj = userRepository.findByName(messageDTO.getFrom());
            conversation = conversationRepository.findConversation(otherUserObj, currUserObj);

            if (conversation == null) {
                conversation = new Conversation();
                ConversationRecipients conversationRecipients = new ConversationRecipients();
                conversationRecipients.setCurrUser(currUserObj);
                conversationRecipients.setOtherUser(otherUserObj);
                conversation.setConversationRecipients(conversationRecipients);
                conversation.setConversationItemList(new LinkedList<>());
            }
        }
        conversation.getConversationItemList().add(conversationMessage);
        conversationRepository.save(conversation);
    }
}
