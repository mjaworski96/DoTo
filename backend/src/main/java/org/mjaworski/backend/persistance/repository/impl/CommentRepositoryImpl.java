package org.mjaworski.backend.persistance.repository.impl;

import org.mjaworski.backend.persistance.entity.Comment;
import org.mjaworski.backend.persistance.repository.CommentRepository;
import org.mjaworski.backend.persistance.repository.impl.jpa.CommentJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CommentRepositoryImpl implements CommentRepository {
    private CommentJpaRepository commentJpaRepository;

    @Autowired
    public CommentRepositoryImpl(CommentJpaRepository commentJpaRepository) {
        this.commentJpaRepository = commentJpaRepository;
    }

    @Override
    public Optional<Comment> get(int id) {
        return commentJpaRepository.findById(id);
    }

    @Override
    public void save(Comment comment) {
        commentJpaRepository.save(comment);
    }

    @Override
    public void delete(Comment comment) {
        commentJpaRepository.delete(comment);
    }
}
