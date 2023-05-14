package com.example.InstagramApp.repository;

import com.example.InstagramApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface IUserRepo extends JpaRepository<User,Long> {

    User findByUser(Long userId);

    User findFirstByPatientEmail(String userEmail);
}
