package chanho.nekarainfo.repository;

import chanho.nekarainfo.domain.NaverInfos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NaverInfoRepository extends JpaRepository<NaverInfos,Long> {
}
