package com.herovired.Blog.Platform.API.repositories;

import com.herovired.Blog.Platform.API.models.Admin;
import com.herovired.Blog.Platform.API.models.Authority;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AdminRepository extends CrudRepository<Admin,Long> {


}