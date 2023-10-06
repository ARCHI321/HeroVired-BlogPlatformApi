package com.herovired.Blog.Platform.API.controllers;

import com.herovired.Blog.Platform.API.Authentication.UserAuthenticationObject;
import com.herovired.Blog.Platform.API.Authentication.UserLoginRequestObject;
import com.herovired.Blog.Platform.API.exception.DuplicateUserException;
import com.herovired.Blog.Platform.API.exception.UserNotFoundException;
import com.herovired.Blog.Platform.API.models.User;
import com.herovired.Blog.Platform.API.repositories.UserRepository;
import com.herovired.Blog.Platform.API.services.UserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServices userServices;



    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user , BindingResult resultSet){
        var errorMap = userServices.validateRequestBody(user,resultSet);
        if(errorMap.size() != 0){
            return new ResponseEntity(errorMap , HttpStatus.BAD_REQUEST);
        }
        var userName = user.getUserName();
        var existingUserName = userRepository.findByUserName(userName);
        if(existingUserName.isPresent() && Objects.equals(userName, existingUserName.get().getUserName())){
            throw new DuplicateUserException("user "+userName + " already exists");
        }
        var saveUserCategory = userRepository.save(user);
        return new ResponseEntity(saveUserCategory , HttpStatus.ACCEPTED);
    }

    @GetMapping("/login")
    public UserAuthenticationObject attemptLogin(@RequestBody UserLoginRequestObject userLoginRequestObject){
        var username = userLoginRequestObject.getUsername();
        var userObject = userRepository.findByUserName(username);
        var userAuthenticationObject = new UserAuthenticationObject();
        if(userObject == null){
            userAuthenticationObject.setUsername(userAuthenticationObject.getUsername());
            userAuthenticationObject.setPassword(null);
            userAuthenticationObject.setMessage("username does not exists in the DB");
            userAuthenticationObject.setAuthenticated(false);
        }else {
            var dbPassword = userObject.get().getUserPassword();
            var requestPassword = userLoginRequestObject.getPassword();

            if (!Objects.equals(dbPassword, requestPassword)) {
                userAuthenticationObject.setUsername(userLoginRequestObject.getUsername());
                userAuthenticationObject.setPassword(requestPassword);
                userAuthenticationObject.setMessage("password does not match");
                userAuthenticationObject.setAuthenticated(false);
            } else {
                userAuthenticationObject.setUsername(userLoginRequestObject.getUsername());
                userAuthenticationObject.setPassword(requestPassword);
                userAuthenticationObject.setMessage("User is Authenticated");
                userAuthenticationObject.setAuthenticated(true);
            }
        }
        return  userAuthenticationObject;
    }

    @PostMapping("/forgot-password/{id}")
    public ResponseEntity<?> forgotPassword(@PathVariable long id , @RequestParam String newPassword){
        var user= userRepository.findById(id);
        if (user.isPresent()) {
            user.get().setUserPassword(newPassword);
            var updatedUser = userRepository.save(user.get());

            return new ResponseEntity<>(updatedUser,HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Object does not exist",HttpStatus.ACCEPTED);

    }

    @GetMapping("/get-all-users")
    public List<User> getAllUsers(){
        return (List<User>) userRepository.findAll();
    }


    @GetMapping("/get-user-by-id/{id}")
    public Optional<User> getUserById(@PathVariable long id){
        var optionalBookCategoryObject = userRepository.findById(id);
        if(optionalBookCategoryObject.isEmpty()){
            throw new UserNotFoundException("id"+id + " does not exist");

        }
        return optionalBookCategoryObject;
    }



}
