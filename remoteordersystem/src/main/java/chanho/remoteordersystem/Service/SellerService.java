package chanho.remoteordersystem.Service;

import chanho.remoteordersystem.domain.Seller;
import chanho.remoteordersystem.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class SellerService {
    private final SellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;
    public Seller create(String email, String sellerName, String password){
        Seller seller = new Seller(email, sellerName,passwordEncoder.encode(password));
        sellerRepository.save(seller);
        return seller;
    }

    public Seller getSellerById(Long id){
        Optional<Seller> byId = sellerRepository.findById(id);
        if (byId.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"seller with the id not found");
        }
        return byId.get();
    }
    public Seller getSellerByEmail(String email){
        Optional<Seller> byEmail = sellerRepository.findByEmail(email);
        if (byEmail.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"seller not found");
        }
        return byEmail.get();
    }

}
