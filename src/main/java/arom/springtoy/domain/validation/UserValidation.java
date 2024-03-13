package arom.springtoy.domain.validation;

import arom.springtoy.domain.controller.user.UserSession;
import arom.springtoy.domain.domain.User;
import arom.springtoy.domain.dto.LoginDto;
import arom.springtoy.domain.exception.JoinException;
import arom.springtoy.domain.exception.LoginException;
import arom.springtoy.domain.repository.UserRepository;
import arom.springtoy.global.validation.GlobalValidation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class UserValidation extends GlobalValidation {

    public UserValidation(UserSession userSession) {
        super(userSession);
    }

    public void checkAlreadyExistUserBy(Optional<User> findUserBy){
        if(findUserBy.isPresent()){
            throw new JoinException("you already join, check your email or nickname");

        }
    }

    public void checkNotExistUserBy(Optional<User> findUserBy){
        if(findUserBy.isEmpty()){
            throw new JoinException("you already join, check your email or nickname");

        }
    }
}
