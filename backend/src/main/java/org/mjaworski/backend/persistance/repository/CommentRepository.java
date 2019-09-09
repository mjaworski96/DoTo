package org.mjaworski.backend.persistance.repository;

import org.mjaworski.backend.persistance.entity.Comment;

import java.util.Optional;

public interface CommentRepository {
    Optional<Comment> get(int id);
    void save(Comment comment);
    void delete(Comment comment);
}
