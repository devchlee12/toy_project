package chanho.nekarainfo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class KakaoInfos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String date;
    private String subInfos;
    private String link;

    public KakaoInfos(String title, String date, String subInfos, String link) {
        this.title = title;
        this.date = date;
        this.subInfos = subInfos;
        this.link = link;
    }
}
