package arom.springtoy.global.exception.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ServiceErrorResult extends ErrorResult {

    private final String error;

    public ServiceErrorResult(LocalDateTime currentTime, int status, String objectName,
        String error) {
        super(currentTime, status, objectName);
        this.error = error;
    }
}
