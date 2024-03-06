package chanho.nekarainfo.schedule;

import chanho.nekarainfo.domain.Infos;
import chanho.nekarainfo.domain.KakaoInfos;
import chanho.nekarainfo.domain.LineInfos;
import chanho.nekarainfo.domain.NaverInfos;
import chanho.nekarainfo.repository.KakaoInfoRepository;
import chanho.nekarainfo.repository.LineInfoRepository;
import chanho.nekarainfo.repository.NaverInfoRepository;
import chanho.nekarainfo.service.KakaoInfoService;
import chanho.nekarainfo.service.LineInfoService;
import chanho.nekarainfo.service.NaverInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {
    private final NaverInfoService naverInfoService;
    private final KakaoInfoService kakaoInfoService;
    private final LineInfoService lineInfoService;
    private final LineInfoRepository lineInfoRepository;
    private final KakaoInfoRepository kakaoInfoRepository;
    private final NaverInfoRepository naverInfoRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void updateInfo() throws IOException, InterruptedException {
        List<NaverInfos> naverInf = naverInfoService.getInfo();
        List<KakaoInfos> kakaoInf = kakaoInfoService.getInfo();
        List<LineInfos> lineInf = lineInfoService.getInfo();
        naverInfoRepository.deleteAll();
        kakaoInfoRepository.deleteAll();
        lineInfoRepository.deleteAll();
        for (NaverInfos ni : naverInf){
            naverInfoRepository.save(ni);
        }
        for (LineInfos li : lineInf){
            lineInfoRepository.save(li);
        }
        for (KakaoInfos ki : kakaoInf){
            kakaoInfoRepository.save(ki);
        }
    }
}
