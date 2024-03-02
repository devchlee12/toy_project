package webproject.libraryreservationsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webproject.libraryreservationsystem.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
}
