package com.example.InstagramApp.service;

import com.example.InstagramApp.dto.SignInInput;
import com.example.InstagramApp.dto.SignInOutput;
import com.example.InstagramApp.dto.SignUpInput;
import com.example.InstagramApp.dto.SignUpOutput;
import com.example.InstagramApp.model.AuthenticationToken;
import com.example.InstagramApp.model.post;
import com.example.InstagramApp.repository.IpostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
@Service
public class PostService {
    @Autowired
    IpostRepo ipostRepo;

    @Autowired
    AuthenticationService tokenService;
    private String encryptPassword;

    public SignUpOutput signUp(SignUpInput signUpDto) {

        //check if post exist
        post post = ipostRepo.findFirstByUserEmail(signUpDto.getUserEmail());

        if(post != null)
        {
            throw new IllegalStateException("Patient already Exist...sign in instead");
        }

//encryption
        String encryptedPassword  = null   ;
        try {
            encryptedPassword = encryptPassword(signUpDto.getpostPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        post = new post(signUpDto.getpostFirstName(),signUpDto.getpostLastName(),signUpDto.getpostEmail(),encryptPassword, signUpDto.getpostContact()) ;

        ipostRepo.save(post)   ;

        //token creation and saving

        AuthenticationToken token = new AuthenticationToken(post);

        tokenService.saveToken(token);

        return new SignUpOutput("Patient registered","patient create done");


    }

    private String encryptPassword(String postPassword) throws NoSuchAlgorithmException {

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(postPassword.getBytes());
        byte[] digested = md5.digest();

        String hash = DataTypeConverter.printHexBinary(digested)     ;
        return hash;
    }

    public SignInOutput signIn(SignInInput signInDto) {

        //get Email 
        post post = ipostRepo.findFirstByPatientEmail(signInDto.getPatientEmail());

        if(post != null)
        {
            throw new IllegalStateException("Patient Invalid.....sign up Instead");
        }


        //encrypt the password

        String encryptPassword  = null;
        try {
            encryptPassword = encryptPassword(signInDto.getPatientPassword());
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        //match it with database encrypted password  
        boolean isPasswordValid = encryptedPassword.equals(post.getPatientPassword())    ;
        if(!isPasswordValid)
        {
            throw new IllegalStateException("post Invalid...sign Up instead")  ;
        }


        //figure out token
        AuthenticationToken authToken = tokenService.getToken(post);

        //set up output response

        return new SignInOutput("Authentication Successfully",authToken.getToken());

    }
}
