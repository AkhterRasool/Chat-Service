package com.akhter.ChatApplication.repository;

import com.akhter.ChatApplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByName(String name);
}
