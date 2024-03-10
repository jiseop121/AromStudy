package arom.springtoy.todolist.service;

import arom.springtoy.todolist.domain.Todolist;
import arom.springtoy.todolist.dto.DateDto;
import arom.springtoy.todolist.dto.PutTodolistDto;
import arom.springtoy.todolist.dto.TodolistDto;
import arom.springtoy.todolist.repository.TodolistRepository;
import arom.springtoy.user.domain.User;
import java.time.LocalDateTime;
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

    public LocalDateTime dateDtoToLocal(DateDto dateDto) {
        return LocalDateTime.of(dateDto.getYear(), dateDto.getMonth(),
            dateDto.getDayOfMonth(), dateDto.getHour(), dateDto.getMinute());
    }

    public Long addTodolist(User user, TodolistDto todolistDto){
        Optional<Todolist> todolistByName = todolistRepository.findByTodoListNameAndUser(
            todolistDto.getTodolistName(), user);
        if(todolistByName.isPresent()){
            throw new RuntimeException();
        }

        if(dateDtoToLocal(todolistDto.getStartDate()).isAfter(dateDtoToLocal(todolistDto.getEndDate()))){
            throw new RuntimeException();
        }

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
        log.info("user = {} ",user.getUserId());
        log.info("user = {} ",user.getNickname());
        log.info("user = {}",user.getEmail());
        Optional<Todolist> deleteTodolist = todolistRepository.findById(todolistId);
        log.info("deleteTodolist = {}",deleteTodolist.get().getTodolistId());
        log.info("deleteTodolist = {}",deleteTodolist.get().getTodoListName());
        if(deleteTodolist.isEmpty()){
            throw new RuntimeException( );
        }
        if(deleteTodolist.get().getUser()!=user){
            throw new RuntimeException();
        }
        String deletedTodolistName = deleteTodolist.get().getTodoListName();

        todolistRepository.deleteByTodolistId(todolistId);

        return deletedTodolistName;
    }

    public Todolist modifyTodolist(User user, Long todolistId, PutTodolistDto todolistDto){
        Optional<Todolist> changeTodolist = todolistRepository.findById(todolistId);
        if(changeTodolist.isEmpty()) {
            throw new RuntimeException();
        }
        if(changeTodolist.get().getUser()!=user){
            throw new RuntimeException();
        }

        if(todolistDto.getTodolistName()!=null) changeTodolist.get().modifyOnlyServiceTodoListName(todolistDto.getTodolistName());
        if(todolistDto.getWriter()!=null) changeTodolist.get().modifyOnlyServiceWriter(todolistDto.getWriter());
        if(todolistDto.getIsSuccess()!=null) changeTodolist.get().modifyOnlyServiceIsSuccess(todolistDto.getIsSuccess());
        if(todolistDto.getStartDate()!=null) changeTodolist.get().modifyOnlyServiceStartDate(todolistDto.getStartDate());
        if(todolistDto.getEndDate()!=null) changeTodolist.get().modifyOnlyServiceEndDate(todolistDto.getEndDate());

        return changeTodolist.get();
    }
}
