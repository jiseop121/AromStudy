package arom.springtoy.domain.service;

import arom.springtoy.domain.controller.user.UserSession;
import arom.springtoy.domain.domain.Todolist;
import arom.springtoy.domain.dto.DateDto;
import arom.springtoy.domain.dto.LoginDto;
import arom.springtoy.domain.dto.PutTodolistDto;
import arom.springtoy.domain.dto.TodolistDto;
import arom.springtoy.domain.repository.TodolistRepository;
import arom.springtoy.domain.domain.User;
import arom.springtoy.domain.repository.UserRepository;
import arom.springtoy.domain.validation.TodolistValidation;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
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
public class TodolistService {

    private final TodolistRepository todolistRepository;
    private final TodolistValidation todolistValidation;
    private final UserRepository userRepository;
    private final UserSession userSession;

    public List<Todolist> findAllTodolistByUser(User user){
        return todolistRepository.findAllByUser(user);
    }

    public Todolist findById(Long id){
        return todolistRepository.findById(id).get();
    }

    public User getUserFormLoginDto(HttpServletRequest request) {
        todolistValidation.checkAlreadyLogout(request);
        return getUser(userSession.getLoginDtoFromSession(request));
    }

    private User getUser(LoginDto loginUser) {
        Optional<User> user = userRepository.findByEmail(loginUser.getEmail());
        todolistValidation.checkRightLogin(user);
        return user.get();
    }

    public Long addTodolist(User user, TodolistDto todolistDto){
        Optional<Todolist> todolistByName = todolistRepository.findByTodoListNameAndUser(
            todolistDto.getTodolistName(), user);

        todolistValidation.checkTodolistEmpty(todolistByName);
        todolistValidation.checkCorrectLocalDate(
            dateDtoToLocal(todolistDto.getStartDate()),
            dateDtoToLocal(todolistDto.getEndDate())
        );

        Todolist newTodoList = new Todolist(
            user,
            todolistDto.getWriter(),
            todolistDto.getTodolistName(),
            todolistDto.getStartDate(),
            todolistDto.getEndDate()
        );
        todolistRepository.save(newTodoList);
        return newTodoList.getTodolistId();
    }

    public String deleteTodolist(User user, Long todolistId){
        Optional<Todolist> deleteTodolist = todolistRepository.findById(todolistId);

        todolistValidation.checkTodolistExist(deleteTodolist);
        todolistValidation.checkTodolistRightUser(deleteTodolist.get().getUser(), user);

        String deletedTodolistName = deleteTodolist.get().getTodoListName();

        todolistRepository.deleteByTodolistId(todolistId);

        return deletedTodolistName;
    }

    public Todolist modifyTodolist(User user, Long todolistId, PutTodolistDto todolistDto){
        Optional<Todolist> changeTodolist = todolistRepository.findById(todolistId);

        todolistValidation.checkTodolistExist(changeTodolist);
        todolistValidation.checkTodolistRightUser(changeTodolist.get().getUser(), user);

        if(todolistDto.getTodolistName()!=null) changeTodolist.get().modifyOnlyServiceTodoListName(todolistDto.getTodolistName());
        if(todolistDto.getWriter()!=null) changeTodolist.get().modifyOnlyServiceWriter(todolistDto.getWriter());
        if(todolistDto.getIsSuccess()!=null) changeTodolist.get().modifyOnlyServiceIsSuccess(todolistDto.getIsSuccess());
        if(todolistDto.getStartDate()!=null) changeTodolist.get().modifyOnlyServiceStartDate(todolistDto.getStartDate());
        if(todolistDto.getEndDate()!=null) changeTodolist.get().modifyOnlyServiceEndDate(todolistDto.getEndDate());

        return changeTodolist.get();
    }

    private LocalDateTime dateDtoToLocal(DateDto dateDto) {
        return LocalDateTime.of(dateDto.getYear(), dateDto.getMonth(),
            dateDto.getDayOfMonth(), dateDto.getHour(), dateDto.getMinute());
    }
}
