package com.herovired.Blog.Platform.API.repositories;

import com.herovired.Blog.Platform.API.models.DraftUserData;
import org.springframework.data.repository.CrudRepository;

public interface DraftUserDataRepository extends CrudRepository<DraftUserData , Long> {
}
