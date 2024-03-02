package webproject.libraryreservationsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import webproject.libraryreservationsystem.domain.Announcement;
import webproject.libraryreservationsystem.domain.AnnouncementForm;
import webproject.libraryreservationsystem.domain.SiteUser;
import webproject.libraryreservationsystem.repository.AnnouncementRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;

    public List<Announcement> getAnnouncements(){
        return announcementRepository.findAll();
    }

    public void saveAnnouncement(AnnouncementForm announcementForm, SiteUser siteUser){
        announcementRepository.save(new Announcement(siteUser.getUserName(),announcementForm.getTitle(),announcementForm.getContent(), LocalDateTime.now()));
    }

    public Optional<Announcement> getAnnouncement(Long id){
        Optional<Announcement> byId = announcementRepository.findById(id);
        return byId;
    }
}
