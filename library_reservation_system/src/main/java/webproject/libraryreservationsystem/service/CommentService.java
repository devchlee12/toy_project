package webproject.libraryreservationsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import webproject.libraryreservationsystem.domain.Comment;
import webproject.libraryreservationsystem.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public List<Comment> getAllComment(Long postId){
        List<Comment> allComment = commentRepository.findAllByPostId(postId);
        return allComment;
    }

    public void saveComment(Comment comment){
        commentRepository.save(comment);
    }
}
