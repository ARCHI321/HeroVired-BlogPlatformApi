package com.herovired.Blog.Platform.API.repositories;

import com.herovired.Blog.Platform.API.models.Tags;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TagRepository extends CrudRepository<Tags,Long> {

    Optional<Tags> findByTagId(Long id);
}

