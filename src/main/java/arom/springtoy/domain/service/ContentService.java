package arom.springtoy.domain.service;

import arom.springtoy.domain.domain.Content;
import arom.springtoy.domain.dto.ContentDto;
import arom.springtoy.domain.dto.PutContentDto;
import arom.springtoy.domain.repository.ContentRepository;
import arom.springtoy.domain.domain.Todolist;
import arom.springtoy.domain.repository.TodolistRepository;
import arom.springtoy.domain.domain.User;
import arom.springtoy.domain.dto.LoginDto;
import arom.springtoy.domain.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ContentService {

    private final ContentRepository contentRepository;
    private final TodolistRepository todolistRepository;
    private final UserRepository userRepository;

    public List<Content> findAllByTodolistId(Long todolistId){
        return  contentRepository.findAllByTodolist(todolistRepository.findById(todolistId).get());
    }

    public void blockContent(LoginDto loginUser, Long todolistId) {
        User user = userRepository.findByEmail(loginUser.getEmail()).get();
        if (todolistRepository.findByTodolistIdAndUser(todolistId, user).isEmpty()) {
            throw new RuntimeException();
        }
    }


    public Content addContent(Long todolistId, ContentDto contentDto) {
        Todolist todolist = getTodolist(todolistId);

        Content newContent = new Content(
            todolist,
            contentDto.getContentName(),
            contentDto.getDescription()
        );

        return contentRepository.save(newContent);
    }



    public Content modifyContent(Long todolistId, Long contentId, PutContentDto putContentDto) {
        Content changeContent = getContent(todolistId,contentId);

        if (putContentDto.getIsSuccess() != null) {
            changeContent.modifyOnlyServiceIsSuccess(putContentDto.getIsSuccess());
        }
        if (putContentDto.getContentName() != null) {
            changeContent.modifyOnlyServiceContentName(putContentDto.getContentName());
        }
        if (putContentDto.getDescription() != null) {
            changeContent.modifyOnlyServiceDescription(putContentDto.getDescription());
        }
        if (putContentDto.getTodolistName() != null) {
            changeContent.modifyOnlyServiceTodolist(
                todolistRepository.findByTodoListName(putContentDto.getTodolistName()).get()
            );
        }

        return contentRepository.findById(contentId).orElseThrow(() ->new NullPointerException());
    }

    public String deleteContent(Long todolistId, Long contentId){
        Content content = getContent(todolistId,contentId);
        String contentName = content.getContentName();
        contentRepository.delete(content);

        return contentName;
    }

    private Content getContent(Long todolistId, Long contentId) {
        Optional<Content> changeContent = contentRepository.findByContentIdAndTodolist(contentId,getTodolist(todolistId));
        if (changeContent.isEmpty()) {
            throw new RuntimeException();
        }
        return changeContent.get();
    }

    private Todolist getTodolist(Long todolistId) {
        Optional<Todolist> todolist = todolistRepository.findById(todolistId);
        if (todolist.isEmpty()) {
            throw new RuntimeException();
        }
        return todolist.get();
    }
}
