package com.herovired.Blog.Platform.API.services;

import com.herovired.Blog.Platform.API.models.BlogPost;
import com.herovired.Blog.Platform.API.models.User;
import com.herovired.Blog.Platform.API.repositories.BlogPostRepository;
import com.herovired.Blog.Platform.API.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;

@Service
public class BlogPostServices {
    private BlogPostRepository blogPostRepository;

    public HashMap<String,String> validateRequestBody(BlogPost blogPost, BindingResult resultSet){
        HashMap<String,String> errorMap = new HashMap<>();
        if(resultSet.hasErrors()){
            for(FieldError fieldError : resultSet.getFieldErrors()){
                errorMap.put(fieldError.getField() , fieldError.getDefaultMessage());
            }

        }

        return errorMap;
    }
}
