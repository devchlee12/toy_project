package webproject.libraryreservationsystem.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginForm{
    @NotEmpty(message = "비밀번호는 필수 입니다.")
    private String password;
    @NotEmpty(message = "이메일은 필수 입니다.")
    @Email
    private String email;
}
