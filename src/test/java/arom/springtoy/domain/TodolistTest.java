package arom.springtoy.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import arom.springtoy.content.repository.ContentRepository;
import arom.springtoy.todolist.domain.Todolist;
import arom.springtoy.todolist.dto.DateDto;
import arom.springtoy.todolist.repository.TodolistRepository;
import arom.springtoy.user.domain.User;
import arom.springtoy.user.repository.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
@Transactional
class TodolistTest {
    @Autowired
    private TodolistRepository todolistRepository;
    @Autowired
    private UserRepository userRepository;

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
    private static final String OUT_OF_SIZE_NAME = "1234567890123456789012345678901";


    @Test
    void 다른_이름_2개_리스트_정상_저장(){
        User user1 = userRepository.findById(1L).get();

        DateDto startDate = new DateDto(
            2024,3,6,14,23
        );
        DateDto endDate = new DateDto(
            2024,4,20,15,30
        );


        Todolist todolist2 = new Todolist(
            user1,
            "testWriter2",
            "testTodolistName2",
            startDate,
            endDate
        );

        todolistRepository.save(todolist2);
    }

    @Test
    void 같은_이름_2개_리스트_저장_validation(){
        User user1 = userRepository.findById(1L).get();

        DateDto startDate = new DateDto(
            2024,3,6,14,23
        );
        DateDto endDate = new DateDto(
            2024,4,20,15,30
        );


        Todolist todolist2 = new Todolist(
            user1,
            "testWriter1",
            "testTodolistName1",
            startDate,
            endDate
        );

        assertThatThrownBy(() -> todolistRepository.save(todolist2))
            .isInstanceOf(RuntimeException.class);
    }

    @Test
    void writer_이름_길이_제한(){
        User user1 = userRepository.findById(1L).get();

        DateDto startDate = new DateDto(
            2024,3,6,14,23
        );
        DateDto endDate = new DateDto(
            2024,4,20,15,30
        );


        Todolist todolist2 = new Todolist(
            user1,
            OUT_OF_SIZE_NAME,
            "testTodolistName2",
            startDate,
            endDate
        );
        log.info("string length = {}",OUT_OF_SIZE_NAME.length());
        Set<ConstraintViolation<Todolist>> validate = VALIDATOR.validate(todolist2);
        validate.stream().forEach(
            val -> {
                log.info("message = {}",val.getMessage());
            }
        );
        assertThat(VALIDATOR.validate(todolist2).isEmpty()).isEqualTo(false);

    }




}