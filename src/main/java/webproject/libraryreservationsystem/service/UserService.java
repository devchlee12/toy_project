package webproject.libraryreservationsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import webproject.libraryreservationsystem.domain.Reserved;
import webproject.libraryreservationsystem.domain.SiteUser;
import webproject.libraryreservationsystem.repository.UserRepository;

import java.security.Principal;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SiteUser create(String userName, String email, String password){
        SiteUser user = new SiteUser();
        user.setUserName(userName);
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        this.userRepository.save(user);
        return user;
    }

    public Optional<SiteUser> getUser(String email){
        Optional<SiteUser> user = userRepository.findByEmail(email);
        return user;
    }

    public void initializeUid(Reserved reserved, Principal principal){;
        String email = principal.getName();
        Optional<SiteUser> usr = userRepository.findByEmail(email);
        if (usr.isEmpty()) return ;
        reserved.setUid(usr.get().getId());
    }
}
