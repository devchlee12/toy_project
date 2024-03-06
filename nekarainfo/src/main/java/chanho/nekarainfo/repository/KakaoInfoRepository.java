package chanho.nekarainfo.repository;

import chanho.nekarainfo.domain.Infos;
import chanho.nekarainfo.domain.KakaoInfos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KakaoInfoRepository extends JpaRepository<KakaoInfos,Long> {
}
