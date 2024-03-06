package chanho.nekarainfo.repository;

import chanho.nekarainfo.domain.LineInfos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineInfoRepository extends JpaRepository<LineInfos,Long> {
}
