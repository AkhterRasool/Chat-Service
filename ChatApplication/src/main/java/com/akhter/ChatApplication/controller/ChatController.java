package com.akhter.ChatApplication.controller;

import com.akhter.ChatApplication.exception.NoConversationFoundException;
import com.akhter.ChatApplication.model.Conversation;
import com.akhter.ChatApplication.model.MessageDTO;
import com.akhter.ChatApplication.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/conversation")
    public Conversation loadConversation(@RequestParam String otherUser,
                                         @RequestParam String currentUser) throws NoConversationFoundException {
        return chatService.loadConversation(otherUser, currentUser);
    }

    @PostMapping("/message")
    public void addMessage(@RequestBody MessageDTO messageDTO) {
        chatService.addMessage(messageDTO);
    }
}
