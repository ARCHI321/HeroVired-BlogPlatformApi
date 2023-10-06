package com.herovired.Blog.Platform.API.repositories;

import com.herovired.Blog.Platform.API.models.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CommentRepository extends CrudRepository<Comment,Long> {
    Optional<Comment> findByCommentId(long commentId);
}
