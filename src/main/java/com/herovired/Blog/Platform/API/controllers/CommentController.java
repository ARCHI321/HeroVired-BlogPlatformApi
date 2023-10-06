package com.herovired.Blog.Platform.API.controllers;

import com.herovired.Blog.Platform.API.models.*;
import com.herovired.Blog.Platform.API.repositories.TagRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Objects;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import com.herovired.Blog.Platform.API.repositories.UserRepository;
import com.herovired.Blog.Platform.API.repositories.BlogPostRepository;
import com.herovired.Blog.Platform.API.services.CommentServices;
import org.springframework.beans.factory.annotation.Autowired;
import com.herovired.Blog.Platform.API.repositories.CommentRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
public class CommentController
{
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentServices commentServices;
    @Autowired
    private BlogPostRepository blogPostRepository;
    @Autowired
    private UserRepository userRepository;



    @PostMapping("/add-comment")
    public ResponseEntity<?> addComment(@Valid @RequestBody Comment comment, BindingResult resultSet) {
        var errorMap = commentServices.validateRequestBody(comment, resultSet);
        var allUsers = (userRepository.findAll());
        var allPosts = blogPostRepository.findAll();

        var userId = 0;
        var postId = 0;
        var flag1 = 0;
        var flag2 = 0;
        if (errorMap.size() != 0) {
            return new ResponseEntity(errorMap, HttpStatus.BAD_REQUEST);
        }
        for (User u : allUsers) {
            if (Objects.equals(u.getUserName(), comment.getUserCommentData().getUserName())) {
                userId = (int) u.getUserId();
                flag1 = 1;
                break;
            }
        }
        for (BlogPost b : allPosts) {
            var a = b.getPostId();
            var b2 = comment.getBlogPostData().getPostId();
            var c = b.getPostTitle();
            var d = comment.getBlogPostData().getPostTitle();
            if (a == b2 && Objects.equals(c, d)) {
                postId = (int) b.getPostId();
                flag2 = 1;
            }
        }
        if (flag1 == 1 && flag2 == 1) {
            comment.getUserCommentData().setUserId(userId);
            comment.getBlogPostData().setPostId(postId);
            var newComment = commentRepository.save(comment);
            return new ResponseEntity(newComment, HttpStatus.ACCEPTED);
        }
        if (flag2 == 0 && flag1 == 0) {
            return new ResponseEntity("User and Post both doesn't exist", HttpStatus.ACCEPTED);
        }
        if (flag1 == 0) {
            return new ResponseEntity("User doesn't exist", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity("Post doesn't exist", HttpStatus.ACCEPTED);
    }

    @PostMapping("/update-comment-by-id/{id}")
    public ResponseEntity<?> updateComment(@PathVariable long id, @RequestParam String username, @RequestParam long postId, @RequestBody String updatedComment) {
        var post = blogPostRepository.findByPostId(postId);
        var user = userRepository.findByUserName(username);
        var comment = commentRepository.findByCommentId(id);
        if (!user.isPresent()) {
            return new ResponseEntity("User does not exist", HttpStatus.ACCEPTED);
        }
        if (!post.isPresent()) {
            return new ResponseEntity("Post does not exist", HttpStatus.ACCEPTED);
        }
        comment.get().setComment(updatedComment);
        var newUpdatedComment = commentRepository.save(comment.get());
        return new ResponseEntity(newUpdatedComment, HttpStatus.ACCEPTED);
    }

    @PostMapping("/add-reply-to-a-comment/{id}")
    public ResponseEntity<?> addReplyToComment(@PathVariable long id, @RequestBody String replyMessage) {
        var comment = commentRepository.findByCommentId(id);
        if (comment.isPresent()) {
            var reply = new Reply();
            reply.setReplyMessage(replyMessage);
            var prevReply = comment.get().getReply();
            prevReply.add(reply);
            comment.get().setReply(prevReply);
            var newCommentWithReply = commentRepository.save(comment.get());
            return new ResponseEntity(newCommentWithReply, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity("Comment not Found", HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete-comment-by-id/{id}")
    public ResponseEntity<?> deleteCommentById(@PathVariable long id, @RequestParam String userName) {
        var comment = commentRepository.findByCommentId(id);
        var user = userRepository.findByUserName(userName);
        if (!comment.isPresent()) {
            return new ResponseEntity("Comment does not exist", HttpStatus.ACCEPTED);
        }
        if (Objects.equals(userName, comment.get().getUserCommentData().getUserName())) {
            commentRepository.deleteById(id);
            return new ResponseEntity("Comment deleted", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity("It's not commented by you", HttpStatus.ACCEPTED);
    }
}