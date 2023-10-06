package com.herovired.Blog.Platform.API.repositories;

import com.herovired.Blog.Platform.API.models.DraftPost;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DraftPostRepository extends CrudRepository<DraftPost , Long> {
  Optional<DraftPost> findByDraftPostId(long id);
}
