package arom.springtoy.todolist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DateDto {

    @NotNull @NotBlank
    @Range(min = 2024, max = 2080)
    private int year;

    @NotNull @NotBlank
    @Range(min = 1, max = 12)
    private int month;

    @NotNull @NotBlank
    @Range(min = 1, max = 31)
    private int dayOfMonth;

    @NotNull @NotBlank
    @Range(min = 0, max = 23)
    private int hour;

    @NotNull @NotBlank
    @Range(min = 0, max = 59)
    private int minute;
}
