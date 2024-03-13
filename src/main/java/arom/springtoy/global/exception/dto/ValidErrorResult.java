package arom.springtoy.global.exception.dto;


import java.time.LocalDateTime;
import java.util.Map;
import lombok.Getter;

@Getter
public class ValidErrorResult extends ErrorResult {


    private final Map<String, String> error;

    public ValidErrorResult(LocalDateTime currentTime, int status, String path,
        Map<String, String> error) {
        super(currentTime, status, path);
        this.error = error;
    }
}
