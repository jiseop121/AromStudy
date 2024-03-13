package arom.springtoy.global.validation;

import arom.springtoy.domain.controller.user.UserSession;
import arom.springtoy.domain.domain.User;
import arom.springtoy.domain.dto.LoginDto;
import arom.springtoy.domain.exception.LoginException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GlobalValidation {

    private final UserSession userSession;

    public HttpSession checkAlreadyLogout(HttpServletRequest request) {
        if(request.getSession(false) == null){
            throw new LoginException("you already logout");
        }
        return request.getSession(false);
    }

    public void checkAlreadyLogin(HttpServletRequest request) {
        LoginDto loginUser = userSession.getLoginDtoFromSession(request);
        if (loginUser != null) {
            throw new LoginException("you already login");
        }
    }

    public void checkRightLogin(Optional<User> user){
        if (user.isEmpty()) {
            throw new LoginException("you login again");
        }
    }
}
