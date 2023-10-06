package com.herovired.Blog.Platform.API.repositories;

import com.herovired.Blog.Platform.API.models.BlogPost;
import com.herovired.Blog.Platform.API.models.User;
import com.herovired.Blog.Platform.API.models.UserData;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserDataRepository extends CrudRepository<UserData,Long> {
    UserData findByUserName(String username);

//    @Modifying
//    @Transactional
//    @Query(value = "update UserData userdata set userdata.postId = ?1 where userdata.id = ?1", nativeQuery = true)
//    void updatePostId(@Param("postId") Integer postId , @Param("id") Long id);
}
