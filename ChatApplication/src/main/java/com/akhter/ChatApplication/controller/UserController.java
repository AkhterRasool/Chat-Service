package com.akhter.ChatApplication.controller;

import com.akhter.ChatApplication.model.UserDTO;
import com.akhter.ChatApplication.model.User;
import com.akhter.ChatApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class UserController {

    @Autowired
    private Set<String> onlineUsers;

    @Autowired
    private UserService userService;

    @PutMapping("/user/{userName}")
    public void createUser(@PathVariable String userName) {
        userService.createUser(userName);
    }

    @PutMapping(value = "/contact", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createContact(@RequestBody UserDTO contact) {
        userService.createContact(contact);
    }

    @GetMapping("/contacts/{user}")
    public List<User> fetchAllContacts(@PathVariable String user) {
        return userService.fetchAllContacts(user);
    }

    @PutMapping("/login/{currentUser}")
    public void login(@PathVariable String currentUser) {
        onlineUsers.add(currentUser);
    }

    @GetMapping("/onlinestatus/{otherUser}")
    public boolean isOnline(@PathVariable String otherUser) {
        return onlineUsers.contains(otherUser);
    }

    @DeleteMapping("/logout/{currentUser}")
    public void logout(@PathVariable String currentUser) {
        onlineUsers.remove(currentUser);
    }

}
