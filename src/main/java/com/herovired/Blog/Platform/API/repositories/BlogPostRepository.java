package com.herovired.Blog.Platform.API.repositories;

import com.herovired.Blog.Platform.API.models.BlogPost;
import com.herovired.Blog.Platform.API.models.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BlogPostRepository extends CrudRepository<BlogPost,Long> {
    Optional<BlogPost> findByPostId(long postId);


}
