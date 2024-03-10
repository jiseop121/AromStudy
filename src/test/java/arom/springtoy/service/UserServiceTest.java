package arom.springtoy.service;

import static org.assertj.core.api.Assertions.*;

import arom.springtoy.user.domain.User;
import arom.springtoy.user.dto.JoinDto;
import arom.springtoy.user.dto.LoginDto;
import arom.springtoy.user.repository.UserRepository;
import arom.springtoy.user.service.UserService;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    private static final LoginDto RIGHT_LOGIN_DTO = new LoginDto(
        "inittest1@naver.com",
        "123"
    );

    private static final JoinDto RIGHT_JOIN_DTO = new JoinDto(
        "newJoinTest@naver.com",
        "1234",
        "testNewNickname"
    );

    @Test
    void 비밀번호_UUID_Equal_확인() {
        assertThat(UUID.nameUUIDFromBytes(RIGHT_LOGIN_DTO.getPassword().getBytes()).toString())
            .isEqualTo(
                userRepository.findByEmail(RIGHT_LOGIN_DTO.getEmail()).get().getPasswordUUID());
    }

    @Test
    void 정상_로그인() {
        User rightUser = userService.doLogin(RIGHT_LOGIN_DTO);
        assertThat(rightUser.getUserId())
            .isEqualTo(userRepository.findByEmail(RIGHT_LOGIN_DTO.getEmail()).get().getUserId());
    }

    @Test
    void 비정상_로그인_wrong_password() {
        LoginDto wrongPasswordLogin = new LoginDto(
            "inittest1@naver.com",
            "wrongPassword"
        );
        assertThatThrownBy(() -> userService.doLogin(wrongPasswordLogin)).isInstanceOf(
            RuntimeException.class);
    }

    @Test
    void 비정상_로그인_wrong_email() {
        LoginDto wrongEmailLogin = new LoginDto(
            "wrongEmail@naver.com",
            "123"
        );
        assertThatThrownBy(() -> userService.doLogin(wrongEmailLogin)).isInstanceOf(
            RuntimeException.class);
    }

    @Test
    void 비정상_로그인_empty_blank_value() {
        LoginDto emptyLogin = new LoginDto(
        );
        assertThatThrownBy(() -> userService.doLogin(emptyLogin)).isInstanceOf(
            RuntimeException.class);
    }

    @Test
    void 정상_회원가입() {
        User user = userService.doSignUp(RIGHT_JOIN_DTO);
        assertThat(user.getUserId())
            .isEqualTo(userRepository.findByEmail(RIGHT_JOIN_DTO.getEmail()).get().getUserId());
    }

    @Test
    void 비정상_회원가입_이미_존재하는_이메일() {
        JoinDto wrongJoin = new JoinDto(
            RIGHT_LOGIN_DTO.getEmail(),
            "1234",
            "testNewNickname"
        );
        assertThatThrownBy(() -> userService.doSignUp(wrongJoin)).isInstanceOf(
            RuntimeException.class);
    }

    @Test
    void 비정상_회원가입_이미_존재하는_닉네임() {
        String existedNickname = userRepository.findById(1L).get().getNickname();
        JoinDto wrongJoin = new JoinDto(
            "newJoinTest@naver.com",
            "1234",
            existedNickname
        );
        assertThatThrownBy(() -> userService.doSignUp(wrongJoin)).isInstanceOf(
            RuntimeException.class);
    }

    @Test
    void 비정상_회원가입_닉네임_길이_제한() {
        JoinDto wrongJoin = new JoinDto(
            "newJoinTest@naver.com",
            "1234",
            "1234512345123451234512345123451" //31글자
        );
        assertThatThrownBy(() -> userService.doSignUp(wrongJoin)).isInstanceOf(
            RuntimeException.class);
    }
}
