package arom.springtoy.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;

@Entity
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotNull
    @NotBlank
    @Column(unique = true)
    @Email
    private String email;

    @NotNull
    @NotBlank
    private String password;
    @NotNull
    @NotBlank
    private String passwordUUID;

    @NotNull @NotBlank
    @Size(min = 1, max = 30)
    @Column(unique = true)
    private String nickname;

    public User(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.passwordUUID = UUID.randomUUID().toString();
    }

    public User() {

    }



}
