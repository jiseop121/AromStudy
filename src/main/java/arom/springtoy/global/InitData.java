package arom.springtoy.global;

import arom.springtoy.domain.domain.Content;
import arom.springtoy.domain.repository.ContentRepository;
import arom.springtoy.domain.domain.Todolist;
import arom.springtoy.domain.dto.DateDto;
import arom.springtoy.domain.repository.TodolistRepository;
import arom.springtoy.domain.domain.User;
import arom.springtoy.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitData {

    private final UserRepository userRepository;
    private final TodolistRepository todolistRepository;
    private final ContentRepository contentRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void init(){
        DateDto startDate = new DateDto(
            2024,3,6,14,23
        );
        DateDto endDate = new DateDto(
            2024,4,20,15,30
        );

        User user1 = new User(
            "inittest1@naver.com",
            "123",
            "initTestNickname1"
        );
        User user2 = new User(
            "inittest2@naver.com",
            "1234",
            "initTestNickname2"
        );

        userRepository.save(user1);
        userRepository.save(user2);

        Todolist todolist1 = new Todolist(
            user1,
            "testWriter1",
            "testTodolistName1",
            startDate,
            endDate
        );

        todolistRepository.save(todolist1);

        Content content1 = new Content(
            todolist1,
            "testContent1",
            "write down what to do"
        );
        Content content2 = new Content(
            todolist1,
            "testContent2",
            "write down what to do"
        );
        contentRepository.save(content1);
        contentRepository.save(content2);
    }
}
