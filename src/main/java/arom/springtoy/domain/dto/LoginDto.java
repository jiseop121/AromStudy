package arom.springtoy.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @Email
    private String email;

    @NotNull @NotBlank
    private String password;
}
