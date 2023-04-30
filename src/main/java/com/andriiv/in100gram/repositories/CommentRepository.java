package com.andriiv.in100gram.repositories;

import com.andriiv.in100gram.entities.Comment;
import com.andriiv.in100gram.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Roman Andriiv (24.04.2023 - 12:44)
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findAllByPost(Post post);

    Comment findByIdAndUserId(Long id, Long userId);
}
