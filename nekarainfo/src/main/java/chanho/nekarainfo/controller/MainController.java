package chanho.nekarainfo.controller;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final NaverInfoService naverInfoService;
    private final KakaoInfoService kakaoInfoService;
    private final LineInfoService lineInfoService;
    private final LineInfoRepository lineInfoRepository;
    private final KakaoInfoRepository kakaoInfoRepository;
    private final NaverInfoRepository naverInfoRepository;

    @GetMapping("/")
    public String mainPage(){
        return "redirect:/naver";
    }

    @GetMapping("/naver")
    public String naverInfo(Model model) throws IOException {
        List<NaverInfos> infoList = naverInfoRepository.findAll();
        if (infoList != null)
            model.addAttribute("infoList", infoList);
        return "home";
    }

    @GetMapping("/kakao")
    public String kakaoInfo(Model model) throws IOException, InterruptedException {
        List<KakaoInfos> infoList = kakaoInfoRepository.findAll();
        if (infoList != null)
            model.addAttribute("infoList", infoList);
        return "home";
    }

    @GetMapping("/line")
    public String lineInfo(Model model) throws IOException {
        List<LineInfos> infoList = lineInfoRepository.findAll();
        model.addAttribute("infoList", infoList);
        return "home";
    }

    @GetMapping("/getinfomanual")
    public String getInfoManual() throws IOException, InterruptedException {
        List<NaverInfos> naverInf = naverInfoService.getInfo();
        List<KakaoInfos> kakaoInf = kakaoInfoService.getInfo();
        List<LineInfos> lineInf = lineInfoService.getInfo();
        naverInfoRepository.deleteAll();
        kakaoInfoRepository.deleteAll();
        lineInfoRepository.deleteAll();
        for (NaverInfos ni : naverInf) {
            naverInfoRepository.save(ni);
        }
        for (LineInfos li : lineInf) {
            lineInfoRepository.save(li);
        }
        for (KakaoInfos ki : kakaoInf) {
            kakaoInfoRepository.save(ki);
        }
        return "redirect:/";
    }
}
