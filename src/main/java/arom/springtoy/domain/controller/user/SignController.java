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
    private final UserSession userSession;

    @GetMapping
    public String signupForm(){
        return "do signup here";
    }

    @PostMapping("/signup")
    public JoinDto signup(@RequestBody JoinDto joinDto){
        userService.doSignUp(joinDto);
        return joinDto;
    }

    @PostMapping("/signout")
    public String signout(HttpServletRequest request){
        LoginDto loginUser = userSession.getLoginDtoFromSession(request);
        if(loginUser==null){
            return "do login first";
        }
        request.getSession(false).invalidate();
        String lastNickname = userService.doSignout(loginUser);
        return "Good bye!! ["+lastNickname+"]";
    }
}
