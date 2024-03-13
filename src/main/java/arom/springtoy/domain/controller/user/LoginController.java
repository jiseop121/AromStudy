package arom.springtoy.domain.controller.user;

import arom.springtoy.domain.dto.LoginDto;
import arom.springtoy.domain.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final UserService userService;

    @GetMapping("/login")
    public String loginForm(HttpServletRequest request){
        if(userService.checkAlreadyLogin(request)){
            LoginDto loginUser = userService.getLoginUser(request);
            return "welcome!!["+userService.userOfNickNameFindByEmail(loginUser.getEmail())+"]";
        }
        return "do login here";
    }

    @PostMapping("/login")
    @ResponseBody
    public LoginDto login(@Valid @RequestBody LoginDto loginDto, HttpServletRequest request){
        userService.doLogin(loginDto,request);
        log.info(loginDto.getEmail());
        return loginDto;
    }

    @GetMapping("/logout")
    @ResponseBody
    public String logout(HttpServletRequest request){
        userService.doLogout(request);
        return "logout ok";
    }
}
