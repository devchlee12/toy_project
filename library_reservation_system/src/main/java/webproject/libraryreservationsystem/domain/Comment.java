package webproject.libraryreservationsystem.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    Long commentId;
    Long postId;
    String writer;
    String content;
    @Column(name = "commented_date")
    LocalDateTime commentedDate;

    public Comment(String writer, String content,Long postId) {
        this.writer = writer;
        this.content = content;
        this.postId = postId;
        this.commentedDate = LocalDateTime.now();
    }
}
