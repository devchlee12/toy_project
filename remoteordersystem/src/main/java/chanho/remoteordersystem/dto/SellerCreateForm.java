package chanho.remoteordersystem.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class SellerCreateForm {
    private String email;
    private String username;
    private String password;
    private String passwordConfirm;
}
