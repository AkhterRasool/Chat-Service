package com.akhter.ChatApplication.service;

import com.akhter.ChatApplication.model.User;
import com.akhter.ChatApplication.model.UserDTO;
import com.akhter.ChatApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void createUser(String userName) {
        if (userRepository.findByName(userName) == null) {
            userRepository.save(new User(userName));
        }
    }

    public void createContact(UserDTO contact) {
        User user  = userRepository.findByName(contact.getCurrentUser());
        if (user != null) {
            User newContact = userRepository.findByName(contact.getOtherUser());
            newContact = newContact == null ? new User(contact.getOtherUser()) : newContact;
            List<User> contactList = user.getContactList();
            if (contactList == null) {
                contactList = new LinkedList<>();
            }

            if (!contactList.contains(newContact)) {
                contactList.add(newContact);
                userRepository.save(user);
            }
        }
    }

    public List<User> fetchAllContacts(String user) {
        return userRepository.findByName(user).getContactList();
    }
}
