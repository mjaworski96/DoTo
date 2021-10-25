package org.mjaworski.backend.converter;

import org.mjaworski.backend.dto.comment.CommentDto;
import org.mjaworski.backend.dto.comment.CommentDtoWithId;
import org.mjaworski.backend.persistance.entity.Comment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class CommentConverter extends BaseConverter {
    public static CommentDtoWithId getCommentDtoWithId(Comment comment) {
        return mapper.map(comment, CommentDtoWithId.class);
    }
    public static List<CommentDtoWithId> getCommentDtoWithIdList(Collection<Comment> comments) {
        List<CommentDtoWithId> result = new ArrayList<>(comments.size());

        comments.forEach(item -> result.add(getCommentDtoWithId(item)));

        return result;
    }
    public static Comment getComment(CommentDto commentDto) {
        return mapper.map(commentDto, Comment.class);
    }
    public static void rewrite(Comment destination, CommentDto source) {
        mapper.map(source, destination);
    }
}
