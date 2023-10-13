package com.herovired.Blog.Platform.API.controllers;

import com.herovired.Blog.Platform.API.Authentication.UserAuthenticationObject;
import com.herovired.Blog.Platform.API.Authentication.UserLoginRequestObject;
import com.herovired.Blog.Platform.API.exception.DuplicateUserException;
import com.herovired.Blog.Platform.API.exception.UserNotFoundException;
import com.herovired.Blog.Platform.API.models.Admin;
import com.herovired.Blog.Platform.API.models.Authority;
import com.herovired.Blog.Platform.API.models.User;
import com.herovired.Blog.Platform.API.repositories.AdminRepository;
import com.herovired.Blog.Platform.API.repositories.AuthorityRepository;
import com.herovired.Blog.Platform.API.repositories.UserRepository;
import com.herovired.Blog.Platform.API.services.UserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityRepository authorityRepository;



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
        var password1 = user.getUserPassword();
        user.setUserPassword(passwordEncoder.encode(password1));
        var saveUserCategory = userRepository.save(user);
        var authority = new Authority();
        authority.setAuthority("ROLE_USER");
        authority.setUser(saveUserCategory);
        authorityRepository.save(authority);
        if(saveUserCategory.isAdmin()){
            var admin = new Admin();
            var username = saveUserCategory.getUserName();
            var password = saveUserCategory.getUserPassword();
            var userId = saveUserCategory.getUserId();
            admin.setUsername(username);
            admin.setPassword(password);
            adminRepository.save(admin);

            var userAuth = authorityRepository.findAll();
            for(Authority a:userAuth){
                if(Objects.equals(admin.getUsername() , a.getUser().getUserName())){
                    a.setAuthority("ROLE_ADMIN");
                    authorityRepository.save(a);
                    break;
                }
            }

            return new ResponseEntity("Welcome Admin" , HttpStatus.ACCEPTED);
        }
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


            if (!passwordEncoder.matches(requestPassword,dbPassword)) {
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

    @GetMapping("/logout/{id}")
    public ResponseEntity<?> logOutById(@PathVariable long id){
        var authority = authorityRepository.findById(id);
        if(authority.isPresent()){
            authority.get().setAuthority("");
            return new ResponseEntity<>("Logged out successfully" , HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("User not found" , HttpStatus.ACCEPTED);
    }

    @PostMapping("/forgot-password/{id}")
    public ResponseEntity<?> forgotPassword(@PathVariable long id , @RequestParam String newPassword){
        var user= userRepository.findById(id);
        if (user.isPresent()) {
            user.get().setUserPassword(newPassword);
            var updatedUser = userRepository.save(user.get());
            if(user.get().isAdmin()){
                var admin = adminRepository.findById(id);
                admin.get().setPassword(newPassword);
                adminRepository.save(admin.get());
            }
            return new ResponseEntity<>(updatedUser,HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Object does not exist",HttpStatus.ACCEPTED);

    }

    @GetMapping("/get-all-users")
    public List<User> getAllUsers(){

        var user = userRepository.findAll();
        var displayedUser = new ArrayList<User>();
        for(User u:user){
            var newuser = new User();
            newuser.setUserId(u.getUserId());
            newuser.setUserName(u.getUserName());
            newuser.setBlocked(u.isBlocked());
            newuser.setAdmin(u.isAdmin());
            displayedUser.add(newuser);
        }
        return displayedUser;
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
