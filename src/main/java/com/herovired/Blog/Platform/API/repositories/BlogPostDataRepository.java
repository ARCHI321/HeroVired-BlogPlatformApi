package com.herovired.Blog.Platform.API.repositories;

import com.herovired.Blog.Platform.API.models.BlogPost;
import org.springframework.data.repository.CrudRepository;

public interface BlogPostDataRepository extends CrudRepository<BlogPost,Long> {
}
