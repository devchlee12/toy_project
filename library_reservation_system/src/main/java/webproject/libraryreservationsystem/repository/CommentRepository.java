package webproject.libraryreservationsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webproject.libraryreservationsystem.domain.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByPostId(Long postId);
}
