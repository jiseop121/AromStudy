package arom.springtoy.domain.controller.service;

import arom.springtoy.domain.domain.Todolist;
import arom.springtoy.domain.domain.User;
import arom.springtoy.domain.dto.PutTodolistDto;
import arom.springtoy.domain.dto.TodolistDto;
import arom.springtoy.domain.service.TodolistService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/todolist")
@RequiredArgsConstructor
public class TodolistController {

    private final TodolistService todolistService;

    @GetMapping
    public List<Todolist> mainTodolist(HttpServletRequest request) {
        User user = todolistService.getUserFormLoginDto(request);
        return todolistService.findAllTodolistByUser(user);
    }

    @GetMapping("/build")
    public String makeListForm() {
        return "make list here";
    }

    @PostMapping("/build")
    public Todolist makeTodolist(@Valid @RequestBody TodolistDto todolistDto, HttpServletRequest request) {
        User user = todolistService.getUserFormLoginDto(request);

        log.info("maketodolist ={}", todolistDto);
        log.info("todolistName = {}", todolistDto.getTodolistName());
        log.info("writer ={}", todolistDto.getWriter());
        log.info("date ={}", todolistDto.getStartDate());
        log.info("year = {}", todolistDto.getStartDate().getYear());
        Long newTodoId = todolistService.addTodolist(user, todolistDto);
        return todolistService.findById(newTodoId);
    }

    @PostMapping("/{todolistId}/delete")
    public String deleteTodolist(@PathVariable("todolistId") Long todolistId, HttpServletRequest request) {
        User user = todolistService.getUserFormLoginDto(request);

        String deletedTodolistName = todolistService.deleteTodolist(user, todolistId);
        return "[" + deletedTodolistName + "] 리스트 삭제 완료";
    }

    @PostMapping("/{todolistId}/put")
    public Todolist putTodolist(@PathVariable("todolistId") Long todolistId,
        @Valid @RequestBody PutTodolistDto putTodolistDto, HttpServletRequest request) {
        User user = todolistService.getUserFormLoginDto(request);

        return todolistService.modifyTodolist(user,todolistId, putTodolistDto);
    }

}
