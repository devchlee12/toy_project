package webproject.libraryreservationsystem.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "posted_id")
    Long postedId;
    String writer;
    String title;
    String content;
    Long views = 0L;
    @Column(name = "posted_date")
    LocalDateTime postedDate;

    public Post(String writer, String title, String content, LocalDateTime postedDate) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.postedDate = postedDate;
    }
}
