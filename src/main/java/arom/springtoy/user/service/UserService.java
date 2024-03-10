package arom.springtoy.user.service;

import arom.springtoy.user.domain.User;
import arom.springtoy.user.dto.JoinDto;
import arom.springtoy.user.dto.LoginDto;
import arom.springtoy.user.repository.UserRepository;
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
        if(findUserByEmail.isPresent()){
            throw new RuntimeException();
        }
        Optional<User> findUserByNickname = userRepository.findByNickname(joinDto.getNickname());
        if(findUserByNickname.isPresent()){
            throw new RuntimeException();
        }

        User joinUser = new User(joinDto.getEmail(), joinDto.getPassword(), joinDto.getNickname());
        userRepository.save(joinUser);

        return joinUser;
    }

    public String doSignout(LoginDto loginDto){
        Optional<User> outUser = userRepository.findByEmail(loginDto.getEmail());
        if(outUser.isEmpty()){
            throw new RuntimeException();
        }

        String nickname = outUser.get().getNickname();

        userRepository.delete(outUser.get());

        return nickname;
    }
}
