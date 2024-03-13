package arom.springtoy.global.exception.dto;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResult {

    private LocalDateTime currentTime;
    private int status;

    private String objectName;
}

