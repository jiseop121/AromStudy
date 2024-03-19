package arom.springtoy.domain.validation;

import arom.springtoy.domain.controller.user.UserSession;
import arom.springtoy.domain.domain.Todolist;
import arom.springtoy.domain.domain.User;
import arom.springtoy.domain.exception.TodolistException;
import arom.springtoy.global.validation.GlobalValidation;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class TodolistValidation extends GlobalValidation {

    public TodolistValidation(UserSession userSession) {
        super(userSession);
    }

    public void checkTodolistExist(Optional<Todolist> todolist) {
        if(todolist.isEmpty()){
            throw new TodolistException("cannot search your todolist");
        }
    }

    public void checkTodolistRightUser(User checkUser, User loginUser){
        if(checkUser!=loginUser){
            throw new TodolistException("cannot access cause of incorrect user");
        }
    }

    public void checkTodolistEmpty(Optional<Todolist> todolist) {
        if(todolist.isPresent()){
            throw new TodolistException("you already have todolist named this name");
        }
    }

    public void checkCorrectLocalDate(LocalDateTime startDate, LocalDateTime endDate){
        if(startDate.isAfter(endDate)){
            throw new TodolistException("write correct Date down");
        }
    }
}
