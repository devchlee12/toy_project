package webproject.libraryreservationsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webproject.libraryreservationsystem.domain.SiteUser;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<SiteUser,Long> {
    Optional<SiteUser> findByEmail(String email);
}