package com.herovired.Blog.Platform.API.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import com.herovired.Blog.Platform.API.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.herovired.Blog.Platform.API.models.Tags;

@RestController
@RequestMapping("/tags")
public class TagsController
{

    @Autowired
    private TagRepository tagRepository;

    @PostMapping("/save-tag")
    public Tags saveTag(@Valid @RequestBody Tags tags) {
        return tagRepository.save(tags);
    }

    @GetMapping("/get-all-tags")
    public List<String> getAllTags() {
        var sTagList = new ArrayList<String>();
        var tagList = tagRepository.findAll();
        for (Tags a : tagList) {
            sTagList.add(a.getTagName());
        }
        return sTagList;
    }

    @PostMapping("/edit-tag-by-id/{id}")
    public ResponseEntity<?> editTagById(@PathVariable long id , @RequestBody String newTagName){
       var tag = tagRepository.findByTagId(id);
       if(tag.isPresent()) {
           tag.get().setTagName(newTagName);
           var updatedTag = tagRepository.save(tag.get());
           return new ResponseEntity<>(updatedTag , HttpStatus.ACCEPTED);
       }
       return new ResponseEntity<>("Tag not Found" , HttpStatus.ACCEPTED);
    }

    @DeleteMapping("delete-tag-by-id/{id}")
    public ResponseEntity<?> deleteTagById(@PathVariable long id){
        var tag = tagRepository.findByTagId(id);
        if(tag.isPresent()) {
            tagRepository.deleteById(id);
            return new ResponseEntity<>("Tag Deleted", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Tag not Found" , HttpStatus.ACCEPTED);
    }
}
