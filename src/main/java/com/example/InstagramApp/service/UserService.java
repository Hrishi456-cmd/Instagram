package com.example.InstagramApp.service;

import com.example.InstagramApp.dto.SignInInput;
import com.example.InstagramApp.dto.SignInOutput;
import com.example.InstagramApp.dto.SignUpInput;
import com.example.InstagramApp.dto.SignUpOutput;
import com.example.InstagramApp.model.AuthenticationToken;
import com.example.InstagramApp.model.User;
import com.example.InstagramApp.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserService {
@Autowired
IUserRepo iUserRepo;

@Autowired
AuthenticationService tokenService;
    private String encryptPassword;

    public SignUpOutput signUp(SignUpInput signUpDto) {

    //check if user exist
        User user = iUserRepo.findFirstByPatientEmail(signUpDto.getUserEmail());

        if(user != null)
        {
            throw new IllegalStateException("Patient already Exist...sign in instead");
        }

//encryption
          String encryptedPassword  = null   ;
        try {
            encryptedPassword = encryptPassword(signUpDto.getUserPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        user = new User(signUpDto.getUserFirstName(),signUpDto.getUserLastName(),signUpDto.getUserEmail(),encryptPassword, signUpDto.getUserContact()) ;

                iUserRepo.save(user)   ;

        //token creation and saving

        AuthenticationToken token = new AuthenticationToken(user);

        tokenService.saveToken(token);

        return new SignUpOutput("Patient registered","patient create done");
        

    }

    private String encryptPassword(String userPassword) throws NoSuchAlgorithmException {

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(userPassword.getBytes());
            byte[] digested = md5.digest();

            String hash = DataTypeConverter.printHexBinary(digested)     ;
            return hash;
    }

    public SignInOutput signIn(SignInInput signInDto) {

        //get Email 
         User user = iUserRepo.findFirstByPatientEmail(signInDto.getPatientEmail());

         if(user != null)
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
          boolean isPasswordValid = encryptedPassword.equals(user.getPatientPassword())    ;
           if(!isPasswordValid)
           {
               throw new IllegalStateException("User Invalid...sign Up instead")  ;
           }


        //figure out token
       AuthenticationToken authToken = tokenService.getToken(user);

          //set up output response

              return new SignInOutput("Authentication Successfully",authToken.getToken());

    }
}
