package arom.springtoy.global.exception;

import arom.springtoy.domain.exception.JoinException;
import arom.springtoy.domain.exception.LoginException;
import arom.springtoy.global.exception.dto.ServiceErrorResult;
import arom.springtoy.global.exception.dto.ValidErrorResult;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
@Slf4j
public class ExceptionControllerAdvice {
    /**
     * Valid annotation Error ResponseEntity generator
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidErrorResult exceptionValid(MethodArgumentNotValidException e){
        Map<String, String> errorMessages = new HashMap<>();
        e.getBindingResult().getAllErrors()
            .forEach(c -> errorMessages.put(((FieldError) c).getField(), c.getDefaultMessage()));
        String objectName = e.getBindingResult().getAllErrors().get(0).getObjectName();
        return new ValidErrorResult(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(),objectName,errorMessages);
    }

    @ExceptionHandler(LoginException.class)
    public ServiceErrorResult exceptionLogin(LoginException e){
        log.info("exceptionLogin LoginException= {}",e.toString());
        String message = e.getMessage();
        return new ServiceErrorResult(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(),"LoginController",e.getMessage() );
    }

    @ExceptionHandler(JoinException.class)
    public ServiceErrorResult exceptionJoin(JoinException e){
        log.info("exceptionLogin JoinException= {}",e.toString());
        String message = e.getMessage();
        return new ServiceErrorResult(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(),"JoinController",e.getMessage() );
    }
}
