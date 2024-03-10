package arom.springtoy.user.controller;


import arom.springtoy.user.dto.LoginDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class UserSession {

    private static final String USER_SESSION_ATTRIBUTE_NAME = "loginUser";

    public LoginDto getLoginDtoFromSession(HttpServletRequest request) {
        try{
            request.getSession(false).getAttribute(USER_SESSION_ATTRIBUTE_NAME);
        }catch (NullPointerException e){
            return null;
        }
        return (LoginDto) request.getSession(false).getAttribute(USER_SESSION_ATTRIBUTE_NAME);
    }

    public void setLoginAttribute(LoginDto loginDto, HttpServletRequest request){
        request.getSession().setAttribute(USER_SESSION_ATTRIBUTE_NAME,loginDto);
    }
}
