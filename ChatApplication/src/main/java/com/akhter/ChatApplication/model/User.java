package com.akhter.ChatApplication.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "contactList"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, table = "user")
    private String name;

    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "contacts_table",
            joinColumns = @JoinColumn(name = "user1", referencedColumnName = "id", unique = false),
            inverseJoinColumns = @JoinColumn(name = "user2", referencedColumnName = "id", unique = false)
    )
    private List<User> contactList;

    public User() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getContactList() {
        return contactList;
    }

    public void setContactList(List<User> contactList) {
        this.contactList = contactList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return name.equals(user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
