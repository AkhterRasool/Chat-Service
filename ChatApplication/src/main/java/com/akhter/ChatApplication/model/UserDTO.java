package com.akhter.ChatApplication.model;

public class UserDTO {
    private String currentUser;
    private String otherUser;

    public UserDTO() {}

    public String getOtherUser() {
        return otherUser;
    }

    public void setOtherUser(String otherUser) {
        this.otherUser = otherUser;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }
}
