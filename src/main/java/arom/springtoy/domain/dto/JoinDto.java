package arom.springtoy.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JoinDto {
    @Email
    private String email;

    @NotNull
    @NotBlank(message = "비밀번호를 다시 입력해주세요")
    private String password;

    @NotNull @NotBlank(message = "비밀번호를 다시 입력해주세요")
    @Size(min = 1, max = 30)
    private String nickname;

}
