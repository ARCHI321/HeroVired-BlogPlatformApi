package com.herovired.Blog.Platform.API.controllers;


import com.herovired.Blog.Platform.API.models.User;
import com.herovired.Blog.Platform.API.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @DeleteMapping("/delete-user-by-id/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable long id){
        var user = userRepository.findById(id);
        if(user.isPresent()){
            userRepository.deleteById(id);
            return new ResponseEntity<>("User deleted" , HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("User not found" , HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete-post-by-id/{id}")
    public ResponseEntity<?> deletePostById(@PathVariable long id){
        var post = blogPostRepository.findById(id);
        if(post.isPresent()){
            blogPostRepository.deleteById(id);
            return new ResponseEntity<>("Post deleted" , HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Post not found" , HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete-comment-by-id/{id}")
    public ResponseEntity<?> deleteCommentById(@PathVariable long id){
        var comment = commentRepository.findById(id);
        if(comment.isPresent()){
            commentRepository.deleteById(id);
            return new ResponseEntity<>("Comment deleted" , HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Comment not found" , HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete-reply-by-id/{id}")
    public ResponseEntity<?> deleteReplyById(@PathVariable long id){
        var reply = replyRepository.findById(id);
        if(reply.isPresent()){
            replyRepository.deleteById(id);
            return new ResponseEntity<>("Reply deleted" , HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Reply not found" , HttpStatus.ACCEPTED);
    }

    @PostMapping("/block-user/{username}")
    public ResponseEntity<?> blockUser(@PathVariable String username){
        var userDataObject = userRepository.findByUserName(username);
        if(userDataObject.isPresent()) {
            userDataObject.get().setBlocked(true);
            return new ResponseEntity<>(userDataObject.get(), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("User not found", HttpStatus.ACCEPTED);

    }

    @PostMapping("/unblock-user/{username}")
    public ResponseEntity<?> unblockUser(@PathVariable String username){
        var userDataObject = userRepository.findByUserName(username);
        if(userDataObject.isPresent()){
            userDataObject.get().setBlocked(false);
            return new ResponseEntity<>(userDataObject.get(), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("User not found", HttpStatus.ACCEPTED);
    }






}
