package arom.springtoy.domain.service;

import arom.springtoy.domain.controller.user.UserSession;
import arom.springtoy.domain.domain.Content;
import arom.springtoy.domain.dto.ContentDto;
import arom.springtoy.domain.dto.PutContentDto;
import arom.springtoy.domain.repository.ContentRepository;
import arom.springtoy.domain.domain.Todolist;
import arom.springtoy.domain.repository.TodolistRepository;
import arom.springtoy.domain.domain.User;
import arom.springtoy.domain.dto.LoginDto;
import arom.springtoy.domain.repository.UserRepository;
import arom.springtoy.domain.validation.ContentValidation;
import jakarta.servlet.http.HttpServletRequest;
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
    private final ContentValidation contentValidation;
    private final UserSession userSession;

    public List<Content> findAllByTodolistId(Long todolistId){
        return  contentRepository.findAllByTodolist(todolistRepository.findById(todolistId).get());
    }

    public void blockContent(HttpServletRequest request, Long todolistId) {
        User user = userRepository.findByEmail(getLoginDto(request).getEmail()).get();
        Optional<Todolist> todolist = todolistRepository.findByTodolistIdAndUser(
            todolistId, user);
        contentValidation.checkTodolistExist(todolist);
    }

    private LoginDto getLoginDto(HttpServletRequest request) {
        contentValidation.checkAlreadyLogin(request);
        return userSession.getLoginDtoFromSession(request);
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
        contentValidation.checkModifyContentRoute(contentRepository.findById(contentId));
        return contentRepository.findById(contentId).get();
    }

    public String deleteContent(Long todolistId, Long contentId){
        Content content = getContent(todolistId,contentId);
        String contentName = content.getContentName();
        contentRepository.delete(content);

        return contentName;
    }

    private Content getContent(Long todolistId, Long contentId) {
        Optional<Content> changeContent = contentRepository.findByContentIdAndTodolist(contentId,getTodolist(todolistId));
        contentValidation.checkContentExist(changeContent);
        return changeContent.get();
    }

    private Todolist getTodolist(Long todolistId) {
        Optional<Todolist> todolist = todolistRepository.findById(todolistId);
        contentValidation.checkTodolistExist(todolist);
        return todolist.get();
    }
}
