package com.herovired.Blog.Platform.API.repositories;

import com.herovired.Blog.Platform.API.models.DraftPostData;
import org.springframework.data.repository.CrudRepository;

public interface DraftPostDataRepository extends CrudRepository<DraftPostData , Long> {
}
