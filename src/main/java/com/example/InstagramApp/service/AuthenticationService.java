package com.example.InstagramApp.service;

import com.example.InstagramApp.model.AuthenticationToken;
import com.example.InstagramApp.model.User;
import com.example.InstagramApp.repository.ITokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    ITokenRepo iTokenRepo;

   public void saveToken(AuthenticationToken token)
   {
       iTokenRepo.save(token);
   }


    public AuthenticationToken getToken(User user) {
       
       return iTokenRepo.findByPatient(user);
   }
}
