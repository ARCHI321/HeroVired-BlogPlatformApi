package com.herovired.Blog.Platform.API.repositories;

import com.herovired.Blog.Platform.API.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long> {

    Optional<User> findByUserName(String username);
}
