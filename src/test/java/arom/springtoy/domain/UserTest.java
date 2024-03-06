package arom.springtoy.domain;

import static org.assertj.core.api.Assertions.*;

import arom.springtoy.user.domain.User;
import arom.springtoy.user.repository.UserRepository;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    void 유저_2명이상_정상등록(){
        User user1 = new User(
            "test1@naver.com",
            "123",
            "testNickname1"
        );
        User user2 = new User(
            "test2@naver.com",
            "1234",
            "testNickname2"
        );
        userRepository.save(user1);
        userRepository.save(user2);
    }

    @Test
    @Transactional
    void 유저_중복_닉네임_확인(){
        User user1 = new User(
            "test2@naver.com",
            "1234",
            "initTestNickname2"
        );

        assertThatThrownBy(() -> userRepository.save(user1))
            .isInstanceOf(RuntimeException.class);
    }

    @Test
    @Transactional
    void 유저_중복_이메일_확인(){
        User user1 = new User(
            "inittest1@naver.com",
            "1234",
            "TestNickname123"
        );

        assertThatThrownBy(() -> userRepository.save(user1))
            .isInstanceOf(RuntimeException.class);
    }
}