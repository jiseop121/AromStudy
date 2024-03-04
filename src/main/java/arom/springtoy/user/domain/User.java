package arom.springtoy.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Builder;
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
    @NotNull
    @NotBlank
    private String nickname;

    public User(Long userId, String email, String password, String nickname) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.passwordUUID = UUID.randomUUID().toString();
    }

    public User() {

    }



}
