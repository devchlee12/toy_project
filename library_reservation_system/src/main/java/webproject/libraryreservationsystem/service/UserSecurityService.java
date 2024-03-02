package webproject.libraryreservationsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import webproject.libraryreservationsystem.domain.SiteUser;
import webproject.libraryreservationsystem.repository.UserRepository;
import webproject.libraryreservationsystem.security.UserRole;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SiteUser> _siteUser = userRepository.findByEmail(username);
        if (_siteUser.isEmpty()){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        SiteUser siteUser = _siteUser.get();
        List<GrantedAuthority> authorities= new ArrayList<>();
        if ("admin".equals(siteUser.getUserName())){
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        else{
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        return new User(siteUser.getEmail(),siteUser.getPassword(),authorities);
    }
}
