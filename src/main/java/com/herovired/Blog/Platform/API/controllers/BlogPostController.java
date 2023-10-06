package com.herovired.Blog.Platform.API.controllers;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.herovired.Blog.Platform.API.exception.DuplicateUserException;
import com.herovired.Blog.Platform.API.models.*;
import com.herovired.Blog.Platform.API.repositories.*;
import com.herovired.Blog.Platform.API.services.BlogPostServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/blog")
public class BlogPostController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlogPostServices blogPostServices;

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private DraftPostRepository draftPostRepository;

    @Autowired
    private DraftPostDataRepository draftPostDataRepository;

    @Autowired
    private DraftUserDataRepository draftUserDataRepository;

    @PostMapping("/save-post")
    public ResponseEntity<?> saveBlogPost(@Valid @RequestBody BlogPost blogPost , BindingResult resultSet , @RequestParam String postNow){
        var flag1 = 0;
        var flag2 = 0;
        var allUsers = userRepository.findAll();
        var errorMap = blogPostServices.validateRequestBody(blogPost,resultSet);
        var allTags = tagRepository.findAll();
        var userId = 0;
        var tagId = 0;
        var draftPost = new DraftPost();
        if(errorMap.size() != 0){
            return new ResponseEntity(errorMap , HttpStatus.BAD_REQUEST);
        }
        for(User u:allUsers){
            if(Objects.equals(u.getUserName(),blogPost.getUserData().getUserName())){
                userId = (int) u.getUserId();
                flag1 = 1;
                break;

            }
        }
        List<String> allPostTags = new ArrayList<>();
        List<String> allExistingTags = new ArrayList<>();
        for(Tags t:allTags){
            allExistingTags.add(t.getTagName());
        }
        var postTags = blogPost.getAllTags();
        for(AllTags a:postTags){
            allPostTags.add(a.getAllTagName());
        }

        if(allExistingTags.containsAll(allPostTags)){
            flag2 = 1;
        }

        if(flag1 == 1 && flag2 == 1){
            blogPost.getUserData().setUserId(userId);
            if(postNow.equals("Yes") || postNow.equals("yes")) {
                    var saveBlog = blogPostRepository.save(blogPost);
                    var postId = blogPost.getPostId();
                    return new ResponseEntity(saveBlog, HttpStatus.ACCEPTED);
            }
            else{
                draftPost.setDraftPostTitle(blogPost.getPostTitle());
                List<DraftAllTags> lDraftAllTags = new ArrayList<>();
                for(AllTags a:blogPost.getAllTags()){
                    DraftAllTags b = new DraftAllTags();
                    b.setAllTagName(a.getAllTagName());
                    lDraftAllTags.add(b);
                }
                draftPost.setDraftAllTags(lDraftAllTags);

                draftPost.setDraftPostContent(blogPost.getPostContent());

                DraftUserData draftUserData = new DraftUserData();

                draftUserData.setDraftUserName(blogPost.getUserData().getUserName());
                draftPost.setDraftUserData(draftUserData);

                draftPost.setDraftDateAdded(blogPost.getDateAdded());

                DraftPostData draftPostData = new DraftPostData();

                var saveDraftBlog = draftPostRepository.save(draftPost);

                draftPostData.setDraftPostId((int) saveDraftBlog.getDraftPostId());
                var newDraft = draftPostRepository.save(draftPost);
                var userName = newDraft.getDraftUserData().getDraftUserName();
                var user = userRepository.findByUserName(userName);
                var draftUserId = user.get().getUserId();
                draftPostRepository.deleteById(newDraft.getDraftPostId());
                newDraft.getDraftUserData().setDraftUserId(draftUserId);
                var newSaveBlog = draftPostRepository.save(newDraft);
                return new ResponseEntity("Draft saved successfully" , HttpStatus.ACCEPTED);
            }
        }
        if(flag1 == 0 && flag2 == 0){
            return new ResponseEntity("Both User and tag doesn't exist" , HttpStatus.ACCEPTED);
        }
        if(flag2 == 0){
            return new ResponseEntity("tag(s) doesn't exist" , HttpStatus.ACCEPTED);
        }
        else{
            return new ResponseEntity("User doesn't exist" , HttpStatus.ACCEPTED);
        }

    }

    @PostMapping("/save-draft-post/{id}")
    public ResponseEntity<?> saveDraftPost(@PathVariable long id){
        var draftPost = draftPostRepository.findByDraftPostId(id);
        if(draftPost.isPresent()){
            BlogPost blogPost = new BlogPost();
            blogPost.setPostContent(draftPost.get().getDraftPostContent());
            blogPost.setPostTitle(draftPost.get().getDraftPostTitle());
            blogPost.setDateAdded(draftPost.get().getDraftDateAdded());

//            System.out.println(blogPost);

            UserData userData = new UserData();
            userData.setUserId((int) draftPost.get().getDraftUserData().getDraftUserId());
            userData.setUserName(draftPost.get().getDraftUserData().getDraftUserName());
            blogPost.setUserData(userData);
//            System.out.println(blogPost);

            List<AllTags> lAllTags = new ArrayList<>();
            for(DraftAllTags d:draftPost.get().getDraftAllTags()){
                AllTags b = new AllTags();
                b.setAllTagName(d.getAllTagName());
                lAllTags.add(b);
            }
            blogPost.setAllTags(lAllTags);
//            System.out.println(blogPost);

            var saveBlog = blogPostRepository.save(blogPost);
//            System.out.println(saveBlog);
            draftPostRepository.deleteById(draftPost.get().getDraftPostId());

            var userName = saveBlog.getUserData().getUserName();
            var user = userRepository.findByUserName(userName);
            var userId = user.get().getUserId();
            blogPostRepository.deleteById(saveBlog.getPostId());
            saveBlog.getUserData().setUserId((int) userId);
            var newSaveBlog = blogPostRepository.save(saveBlog);
            return new ResponseEntity<>(newSaveBlog , HttpStatus.ACCEPTED);

        }
        else{
            return new ResponseEntity<>("No such Post exist" , HttpStatus.ACCEPTED);
        }
    }

    @GetMapping("/get-all-posts")
    public List<BlogPost> getAllPosts(){
        return (List<BlogPost>) blogPostRepository.findAll();
    }

    @GetMapping("/get-all-posts/{id}")
    public ResponseEntity<?> getPostById(@PathVariable long id){
        var post = blogPostRepository.findByPostId(id);
        if(post.isPresent()){
            return new ResponseEntity(post, HttpStatus.ACCEPTED);
        }
        else{
            return new ResponseEntity<>("Post doesn't exist" ,HttpStatus.ACCEPTED );
        }
    }

    @DeleteMapping("/delete-post-by-id/{id}")
    public ResponseEntity<?> deletePostById(@PathVariable long id , @RequestBody String username){
        var post = blogPostRepository.findByPostId(id);
        if(post.isPresent()) {
            var user = post.get().getUserData().getUserName();
            var postId = post.get().getPostId();
            System.out.println(user);
            System.out.println(username);
            if (user.equals(username)) {
                blogPostRepository.deleteById(postId);
                return new ResponseEntity("Post deleted sucessfully", HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity("It's not posted by you", HttpStatus.ACCEPTED);
            }
        }
        else{
            return new ResponseEntity("Post doesn't exist", HttpStatus.ACCEPTED);
        }

    }

    @PutMapping("/edit-post-by-id/{id}")
    public ResponseEntity<?> editPostById(@PathVariable long id , @RequestBody Object newBlogPost , @RequestParam String username){
        var post = blogPostRepository.findByPostId(id);
        if(post.isPresent()) {
            var user = post.get().getUserData().getUserName();
            var postId = post.get().getPostId();
            if (user.equals(username)) {
                String newBlogPost1 = newBlogPost.toString();
                newBlogPost1 = newBlogPost1.substring(1, newBlogPost1.length()-1);           //remove curly brackets
                String[] keyValuePairs = newBlogPost1.split(",");              //split the string to creat key-value pairs
                Map<String,String> map = new HashMap<>();

                for(String pair : keyValuePairs)                        //iterate over the pairs
                {
                    String[] entry = pair.split("=");                   //split the pairs to get key and value
                    map.put(entry[0].trim(), entry[1].trim());          //add them to the hashmap and trim whitespaces
                }
                for(String s:map.keySet()){
                    if(s.toLowerCase().contains("title")){
                        post.get().setPostTitle(map.get(s));
                    }
                    if(s.toLowerCase().contains("content")){
                        post.get().setPostContent(map.get(s));
                    }

                }
                var updatedBlog = blogPostRepository.save(post.get());
                return new ResponseEntity(updatedBlog, HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity("It's not posted by you", HttpStatus.ACCEPTED);
            }
        }
        else{
            return new ResponseEntity("Post doesn't exist", HttpStatus.ACCEPTED);
        }

    }

    @PostMapping("/edit-tags/{id}")
    public ResponseEntity<?> editTags(@PathVariable long id , @RequestBody String newTag){
       var post = blogPostRepository.findByPostId(id);
       var flag = 0;
       if(post.isPresent()){
           var allExistingTags = tagRepository.findAll();
           for(Tags t:allExistingTags){
               if(Objects.equals(t.getTagName() , newTag)){
                   flag = 1;
                   break;
               }
           }
           if(flag == 1) {
               var postTags = post.get().getAllTags();
               for(AllTags a:postTags){
                   if(Objects.equals(a.getAllTagName() , newTag)){
                       return new ResponseEntity("Tag is already mentioned", HttpStatus.ACCEPTED);
                   }
               }
               var allTags = new AllTags();
               allTags.setAllTagName(newTag);
               postTags.add(allTags);
               post.get().setAllTags(postTags);
               var newPostWithNewTags = blogPostRepository.save(post.get());
               return new ResponseEntity(newPostWithNewTags, HttpStatus.ACCEPTED);
           }
           else{
               return new ResponseEntity("Tag doesn't exist", HttpStatus.ACCEPTED);
           }
       }
       else{
           return new ResponseEntity("Post doesn't exist", HttpStatus.ACCEPTED);
       }
    }

    @GetMapping("/search-post-by-tag")
    public ResponseEntity<?> searchPostByTag(@RequestBody String searchTag){
        var allPosts = blogPostRepository.findAll();
        var flag = 0;
        var listPosts = new ArrayList<>();
        for(BlogPost b:allPosts){
            var postTags = b.getAllTags();
            for(AllTags a:postTags){
                if(Objects.equals(a.getAllTagName() , searchTag)){
                    flag = 1;
                    listPosts.add(b);
                }
            }
        }
        if(listPosts.size() == 0){
            return new ResponseEntity<>("Posts with such tag doesn't exist" , HttpStatus.ACCEPTED);
        }
        else{
            return new ResponseEntity<>(listPosts , HttpStatus.ACCEPTED);
        }
    }



}
