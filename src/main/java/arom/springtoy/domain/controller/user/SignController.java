package arom.springtoy.domain.controller.user;

import arom.springtoy.domain.dto.JoinDto;
import arom.springtoy.domain.dto.LoginDto;
import arom.springtoy.domain.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignController {

    private final UserService userService;

    @GetMapping
    public String signupForm(){
        return "do signup here";
    }

    @PostMapping("/signup")
    public JoinDto signup(@RequestBody JoinDto joinDto, HttpServletRequest request){
        userService.doSignUp(joinDto, request);
        return joinDto;
    }

    @PostMapping("/signout")
    public String signout(HttpServletRequest request){
        String lastNickname = userService.doSignout(getLoginDto(request),request);
        return "Good bye!! ["+lastNickname+"]";
    }

    private LoginDto getLoginDto(HttpServletRequest request) {
        return userService.getLoginUser(request);
    }
}
