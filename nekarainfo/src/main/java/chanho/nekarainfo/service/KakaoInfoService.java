package chanho.nekarainfo.service;

import chanho.nekarainfo.domain.Infos;
import chanho.nekarainfo.domain.KakaoInfos;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class KakaoInfoService {
    private static final String URL = "https://careers.kakao.com/jobs";

    public List<KakaoInfos> getInfo() throws IOException, InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-popup-blocking");
        options.addArguments("headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--blink-settings=imagesEnabled=false");
        ChromeDriver webDriver = new ChromeDriver(options);
        WebDriverWait webDriverWait = new WebDriverWait(webDriver,Duration.ofSeconds(10));
        webDriver.get(URL);
        webDriverWait.until(
                ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".wrap_info"))
        );
        Thread.sleep(1000);
        String title;
        String date;
        String subInfos;
        String link;
        List<KakaoInfos> lst = new ArrayList<>();
        List<WebElement> wrapInfo = webDriver.findElements(By.className("wrap_info"));
        for (WebElement element : wrapInfo){
            String text = element.getText();
            String[] split = text.split("\n");
            lst.add(new KakaoInfos(split[0],split[1],split[2],null));
        }
        webDriver.quit();
        return lst;
    }
}
