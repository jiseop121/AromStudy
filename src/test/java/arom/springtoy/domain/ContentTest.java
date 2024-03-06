package arom.springtoy.domain;

import arom.springtoy.content.domain.Content;
import arom.springtoy.todolist.domain.Todolist;
import arom.springtoy.todolist.repository.TodolistRepository;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
@Transactional
class ContentTest {
    @Autowired
    private TodolistRepository todolistRepository;

    private static final String LONG_CONTENT_NAME = "1234567890123456789012345678901";
    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void 컨텐츠_명_사이즈_제한(){

        Todolist todolist = todolistRepository.findById(1L).get();

        Content content1 = new Content(
            todolist,
            LONG_CONTENT_NAME,
            "write down what to do"
        );

        VALIDATOR.validate(content1).stream().forEach(
            contentConstraintViolation -> {
                Assertions.assertThat(contentConstraintViolation.getMessage()).contains("30");
                log.info(contentConstraintViolation.getMessage());
            }
        );
    }
}