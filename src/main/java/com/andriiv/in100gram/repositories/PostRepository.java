package com.andriiv.in100gram.repositories;

import com.andriiv.in100gram.entities.Post;
import com.andriiv.in100gram.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Roman Andriiv (24.04.2023 - 12:23)
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findPostByIdAndUser(Long postId, User user);

    List<Post> findAllByUserOrderByCreatedDateDesc(User user);

    List<Post> findAllByOrderByCreatedDateDesc();
}
