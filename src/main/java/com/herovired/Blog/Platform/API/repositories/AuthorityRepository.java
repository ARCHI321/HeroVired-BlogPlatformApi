package com.herovired.Blog.Platform.API.repositories;

import com.herovired.Blog.Platform.API.models.Authority;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AuthorityRepository extends CrudRepository<Authority , Long> {

}
