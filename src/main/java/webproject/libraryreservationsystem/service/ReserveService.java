package webproject.libraryreservationsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import webproject.libraryreservationsystem.domain.Reserved;
import webproject.libraryreservationsystem.domain.SiteUser;
import webproject.libraryreservationsystem.repository.ReserveRepository;
import webproject.libraryreservationsystem.repository.UserRepository;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReserveService {
    private final ReserveRepository reserveRepository;
    private final UserRepository userRepository;

    public List<Reserved> getReserveData(){
        List<Reserved> all = reserveRepository.findAll();
        ArrayList<Reserved> ar = new ArrayList<Reserved>();
        for (Reserved r : all){
            if (r.getDate().isAfter(LocalDateTime.now())){
                ar.add(r);
            }
        }
        return (ar);
    }

    public Optional<Reserved> getUsersReserved(String email){
        Optional<SiteUser> user = userRepository.findByEmail(email);
        Optional<Reserved> seat = reserveRepository.findByUid(user.get().getId());
        if (!seat.isEmpty()){
            if (seat.get().getDate().isBefore(LocalDateTime.now())){
                seat = Optional.empty();
            }
        }
        return seat;
    }

    public boolean hasSeated(Long uid){
        List<Reserved> allUid = reserveRepository.findAllByUid(uid);
        for (Reserved reserved : allUid){
            if (reserved.getDate().isAfter(LocalDateTime.now()))
                return true;
        }
        return false;
    }


    public void saveReserveData(Reserved reserved){
        //아직 예약중인 좌석에 예약을 했다면 예약되지 않음
        Optional<Reserved> rsv = reserveRepository.findById(reserved.getNum());
        if (rsv.isPresent()){
            if(rsv.get().getDate().isAfter(LocalDateTime.now())) return ;
        }
        reserveRepository.deleteById(reserved.getNum());
        reserveRepository.deleteAllByUid(reserved.getUid());
        reserveRepository.save(reserved);
    }

    public void deleteUsersReserved(Principal principal){
        Optional<SiteUser> byEmail = userRepository.findByEmail(principal.getName());
        if (byEmail.isEmpty()) return;
        Long uid = byEmail.get().getId();
        reserveRepository.deleteAllByUid(uid);
    }
}