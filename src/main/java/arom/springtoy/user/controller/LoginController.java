package arom.springtoy.user.controller;

import arom.springtoy.user.domain.User;
import arom.springtoy.user.dto.LoginDto;
import arom.springtoy.user.repository.UserRepository;
import arom.springtoy.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final UserSession userSession;

    @GetMapping("/login")
    public String loginForm(HttpServletRequest request){
        LoginDto loginUser = userSession.getLoginDtoFromSession(request);
        if(loginUser==null){
            return "do login here";
    }
        log.info(String.valueOf(loginUser));
        return "welcome!!["+userRepository.findByEmail(loginUser.getEmail()).get().getNickname()+"]";
    }

    @PostMapping("/login")
    @ResponseBody
    public LoginDto login(@RequestBody LoginDto loginDto, HttpServletRequest request){
        userSession.setLoginAttribute(loginDto,request);
        userService.doLogin(loginDto);
        log.info(loginDto.getEmail());
        return loginDto;
    }

    @GetMapping("/logout")
    @ResponseBody
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            return "logout ok";
        }
        else {
            return "you already logout";
        }

    }
}
