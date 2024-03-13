package arom.springtoy.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DateDto {

    @NotNull
    @NotBlank(message = "년도를 입력해주세요")
    @Range(min = 2024, max = 2080, message = "정상적인 범위 내에서 입력해주세요")
    private int year;

    @NotNull
    @NotBlank(message = "월을 입력해주세요")
    @Range(min = 1, max = 12, message = "정상적인 범위 내에서 입력해주세요")
    private int month;

    @NotNull
    @NotBlank(message = "날짜를 입력해주세요")
    @Range(min = 1, max = 31, message = "정상적인 범위 내에서 입력해주세요")
    private int dayOfMonth;

    @NotNull
    @NotBlank(message = "시간을 입력해주세요")
    @Range(min = 0, max = 23, message = "정상적인 범위 내에서 입력해주세요")
    private int hour;

    @NotNull
    @NotBlank(message = "분을 입력해주세요")
    @Range(min = 0, max = 59, message = "정상적인 범위 내에서 입력해주세요")
    private int minute;
}
