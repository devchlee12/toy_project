package webproject.libraryreservationsystem.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class PostForm {
    private String title;
    private String content;
}
