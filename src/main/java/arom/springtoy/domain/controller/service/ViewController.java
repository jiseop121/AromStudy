package arom.springtoy.domain.controller.service;

import arom.springtoy.domain.controller.user.UserSession;
import arom.springtoy.domain.domain.Content;
import arom.springtoy.domain.domain.User;
import arom.springtoy.domain.dto.LoginDto;
import arom.springtoy.domain.service.ContentService;
import arom.springtoy.domain.service.TodolistService;
import arom.springtoy.domain.service.UserService;
import arom.springtoy.domain.validation.UserValidation;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ViewController {

    private final UserService userService;
    private final UserValidation userValidation;
    private final UserSession userSession;
    private final ContentService contentService;
    private final TodolistService todolistService;

    @GetMapping("/api/todolist")
    public String allTodolist(HttpServletRequest request, Model model){

        User user = todolistService.getUserFormLoginDto(request);
        model.addAttribute("allTodolist",todolistService.findAllTodolistByUser(user));

        return "allTodolist";
    }

    @GetMapping("/api/login")
    public String loginForm(HttpServletRequest request,Model model){
        if(userService.checkAlreadyLogin(request)){
            userService.getLoginUser(request);
            return "welcome";
        }
        model.addAttribute("loginDto",new LoginDto());
        return "loginForm";
    }

    @GetMapping("/api/todolist/{todolistId}/content")
    public String mainContent(@PathVariable("todolistId") Long todolistId, HttpServletRequest request, Model model){
        contentService.blockContent(request,todolistId);
        List<Content> allByTodolistId = contentService.findAllByTodolistId(todolistId);
        for (Content content : allByTodolistId) {
            log.info(content.getContentName());
        }
        model.addAttribute("allContentList",allByTodolistId);

        return "allContent";
    }
}
