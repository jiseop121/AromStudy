package arom.springtoy.domain.service;

import arom.springtoy.domain.controller.user.UserSession;
import arom.springtoy.domain.domain.User;
import arom.springtoy.domain.dto.JoinDto;
import arom.springtoy.domain.dto.LoginDto;
import arom.springtoy.domain.exception.LoginException;
import arom.springtoy.domain.repository.UserRepository;
import arom.springtoy.domain.validation.UserValidation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserSession userSession;
    private final UserValidation userValidation;

    public String userOfNickNameFindByEmail(String email){
        return userRepository.findByEmail(email).get().getNickname();
    }

    public boolean checkAlreadyLogin(HttpServletRequest request){
        try{
            userValidation.checkAlreadyLogin(request);
            return false;
        }catch (LoginException e){
            return true;
        }
    }

    public LoginDto getLoginUser(HttpServletRequest request){
        userValidation.checkAlreadyLogout(request);
        return userSession.getLoginDtoFromSession(request);
    }

    public User doLogin(LoginDto loginDto){

        String email = loginDto.getEmail();
        Optional<User> findUser = userRepository.findByEmail(email);
        if(findUser.isEmpty()){
            throw new RuntimeException();
        }

        User user = findUser.get();

        if(!user.getEmail().equals(loginDto.getEmail())){
            throw new RuntimeException();
        }
        if(!checkPassword(findUser.get(),loginDto.getPassword())){
            throw new RuntimeException();
        }

        return user;
    }
    private boolean checkPassword(User findUser, String password){
        return findUser.getPasswordUUID().equals(UUID.nameUUIDFromBytes(password.getBytes()).toString());
    }

    public User doSignUp(JoinDto joinDto){
        Optional<User> findUserByEmail = userRepository.findByEmail(joinDto.getEmail());
        userValidation.checkAlreadyExistUserBy(findUserByEmail);

        Optional<User> findUserByNickname = userRepository.findByNickname(joinDto.getNickname());
        userValidation.checkAlreadyExistUserBy(findUserByNickname);

        User joinUser = new User(joinDto.getEmail(), joinDto.getPassword(), joinDto.getNickname());
        userRepository.save(joinUser);

        return joinUser;
    }
    public void doLogout(HttpServletRequest request){
        HttpSession session = userValidation.checkAlreadyLogout(request);
        session.invalidate();
    }

    public String doSignout(LoginDto loginDto,HttpServletRequest request){
        userValidation.checkAlreadyLogout(request);
        Optional<User> outUser = userRepository.findByEmail(loginDto.getEmail());
        userValidation.checkNotExistUserBy(outUser);

        String nickname = outUser.get().getNickname();

        userRepository.delete(outUser.get());

        request.getSession(false).invalidate();

        return nickname;
    }
}
