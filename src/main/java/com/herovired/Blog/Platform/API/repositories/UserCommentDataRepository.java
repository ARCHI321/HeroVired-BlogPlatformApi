package com.herovired.Blog.Platform.API.repositories;

import com.herovired.Blog.Platform.API.models.UserCommentData;
import org.springframework.data.repository.CrudRepository;

public interface UserCommentDataRepository extends CrudRepository<UserCommentData,Long> {
}
