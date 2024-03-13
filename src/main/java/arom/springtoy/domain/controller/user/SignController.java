package arom.springtoy.domain.controller.user;

import arom.springtoy.domain.dto.JoinDto;
import arom.springtoy.domain.dto.LoginDto;
import arom.springtoy.domain.service.UserService;
import arom.springtoy.domain.validation.UserValidation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SignController {

    private final UserService userService;
    private final UserValidation userValidation;

    @GetMapping
    public String signupForm(){
        return "do signup here";
    }

    @PostMapping("/signup")
    public JoinDto signup(@Valid @RequestBody JoinDto joinDto, HttpServletRequest request){
        userValidation.checkAlreadyLogin(request);
        userService.doSignUp(joinDto);
        return joinDto;
    }

    @PostMapping("/signout")
    public String signout(HttpServletRequest request){
        String lastNickname = userService.doSignout(userService.getLoginUser(request),request);
        return "Good bye!! ["+lastNickname+"]";
    }
}
