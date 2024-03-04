package arom.springtoy.todolist.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DateDto {
    private final int year;
    private final int month;
    private final int dayOfMonth;
    private final int hour;
    private final int minute;
}
