package chanho.nekarainfo.service;

import chanho.nekarainfo.domain.Infos;
import chanho.nekarainfo.domain.NaverInfos;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NaverInfoService {
    private static final String URL = "https://recruit.navercorp.com/rcrt/list.do?srchClassCd=1000000";

    public List<NaverInfos> getInfo() throws IOException {
        String title;
        String date;
        String subInfos;
        String link;
        List<NaverInfos> lst = new ArrayList<>();
        Document doc = Jsoup.connect(URL).get();
        Elements elements = doc.select(".card_link");
        for (Element element : elements){
            Elements infoTexts = element.getElementsByClass("info_text");
            subInfos = infoTexts.get(0).text() +
                    " | " + infoTexts.get(1).text() +
                    " | " + infoTexts.get(2).text() +
                    " | " + infoTexts.get(3).text();
            date = infoTexts.get(4).text();
            title = element.select(".card_title").text();
            lst.add(new NaverInfos(title,date,subInfos,null));
        }
        return lst;
    }
}
