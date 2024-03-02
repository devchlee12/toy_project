package webproject.libraryreservationsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webproject.libraryreservationsystem.domain.Reserved;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReserveRepository extends JpaRepository<Reserved,Long> {
    List<Reserved> findAllByUid(Long uid);
    Optional<Reserved> findByUid(Long uid);
    void deleteAllByUid(Long uid);
}
