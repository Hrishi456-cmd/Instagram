package com.example.InstagramApp.repository;

import com.example.InstagramApp.model.AuthenticationToken;
import com.example.InstagramApp.model.User;
import jdk.jfr.DataAmount;
import org.springframework.data.jpa.repository.JpaRepository;
@DataAmount
public interface ITokenRepo extends JpaRepository<AuthenticationToken,Long> {


    AuthenticationToken findByPatient(User user);
}
