package arom.springtoy.todolist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Getter
@RequiredArgsConstructor
public class DateDto {

    @NotNull @NotBlank
    @Range(min = 2024, max = 2080)
    private final int year;

    @NotNull @NotBlank
    @Range(min = 1, max = 12)
    private final int month;

    @NotNull @NotBlank
    @Range(min = 1, max = 31)
    private final int dayOfMonth;

    @NotNull @NotBlank
    @Range(min = 0, max = 23)
    private final int hour;

    @NotNull @NotBlank
    @Range(min = 0, max = 59)
    private final int minute;
}
