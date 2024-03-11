package arom.springtoy.domain.controller.service;

import arom.springtoy.domain.controller.user.UserSession;
import arom.springtoy.domain.domain.Todolist;
import arom.springtoy.domain.dto.PutTodolistDto;
import arom.springtoy.domain.dto.TodolistDto;
import arom.springtoy.domain.repository.TodolistRepository;
import arom.springtoy.domain.service.TodolistService;
import arom.springtoy.domain.domain.User;
import arom.springtoy.domain.dto.LoginDto;
import arom.springtoy.domain.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
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
@RequestMapping("/todolist")
@RequiredArgsConstructor
public class TodolistController {

    private final TodolistRepository todolistRepository;
    private final TodolistService todolistService;
    private final UserSession userSession;
    private final UserRepository userRepository;

    @GetMapping
    public List<Todolist> mainTodolist(HttpServletRequest request) {
        LoginDto loginUser = getLoginDto(request);

        return todolistRepository.findAllByUser(
            userRepository.findByEmail(loginUser.getEmail()).get());
    }

    @GetMapping("/build")
    public String makeListForm() {
        return "make list here";
    }

    @PostMapping("/build")
    public Todolist makeTodolist(@RequestBody TodolistDto todolistDto, HttpServletRequest request) {
        LoginDto loginUser = getLoginDto(request);
        Optional<User> user = getUser(loginUser);

        log.info("maketodolist ={}", todolistDto);
        log.info("todolistName = {}", todolistDto.getTodolistName());
        log.info("writer ={}", todolistDto.getWriter());
        log.info("date ={}", todolistDto.getStartDate());
        log.info("year = {}", todolistDto.getStartDate().getYear());
        Long newTodoId = todolistService.addTodolist(user.get(), todolistDto);
        return todolistRepository.findById(newTodoId).get();
    }

    @PostMapping("/{todolistId}/delete")
    public String deleteTodolist(@PathVariable("todolistId") Long todolistId, HttpServletRequest request) {
        Optional<User> user = getUser(getLoginDto(request));

        String deletedTodolistName = todolistService.deleteTodolist(user.get(), todolistId);
        return "[" + deletedTodolistName + "] 리스트 삭제 완료";
    }

    @PostMapping("/{todolistId}/put")
    public Todolist putTodolist(@PathVariable("todolistId") Long todolistId,
        @RequestBody PutTodolistDto putTodolistDto, HttpServletRequest request) {
        Optional<User> user = getUser(getLoginDto(request));

        return todolistService.modifyTodolist(user.get(),todolistId, putTodolistDto);
    }

    private LoginDto getLoginDto(HttpServletRequest request) {
        LoginDto loginUser = userSession.getLoginDtoFromSession(request);
        if (loginUser == null) {
            throw new RuntimeException();
        }
        return loginUser;
    }

    private Optional<User> getUser(LoginDto loginUser) {
        Optional<User> user = userRepository.findByEmail(loginUser.getEmail());
        if (user.isEmpty()) {
            throw new RuntimeException();
        }
        return user;
    }
}
