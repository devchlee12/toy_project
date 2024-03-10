package chanho.remoteordersystem.security;

import chanho.remoteordersystem.domain.Seller;
import chanho.remoteordersystem.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {
    private final SellerRepository sellerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Seller> _seller = sellerRepository.findByEmail(username);
        if (_seller.isEmpty()){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다");
        }
        Seller seller = _seller.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equals(username)){
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        }
        else{
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        return new User(seller.getEmail(),seller.getPassword(),authorities);
    }
}
