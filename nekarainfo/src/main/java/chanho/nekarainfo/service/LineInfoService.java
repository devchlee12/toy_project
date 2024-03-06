package chanho.nekarainfo.service;

import chanho.nekarainfo.domain.Infos;
import chanho.nekarainfo.domain.LineInfos;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class LineInfoService {
    private static final String URL = "https://careers.linecorp.com/jobs?ca=Engineering&ci=Seoul,Bundang&co=East%20Asia";

    public List<LineInfos> getInfo() throws IOException {
        String title;
        String date;
        String subInfos;
        String link;
        List<LineInfos> lst = new ArrayList<>();
        Document doc = Jsoup.connect(URL).get();
        Elements elements = doc.select(".job_list");
        for (Element element : elements){
            Elements infoElements = element.getElementsByTag("li");
            for (Element infoElement : infoElements) {
                title = infoElement.select(".title").text();
                date = infoElement.select(".date").text();
                subInfos = infoElement.select(".text_filter").text();
                lst.add(new LineInfos(title, date, subInfos, null));
            }
        }
        return lst;
    }
}
