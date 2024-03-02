package webproject.libraryreservationsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webproject.libraryreservationsystem.domain.Announcement;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement,Long> {
}
