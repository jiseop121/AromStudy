package arom.springtoy.todolist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PutTodolistDto {
    @NotNull @NotBlank
    private String todolistName;

    @Size(min = 1, max = 30)
    private String writer;

    private Boolean isSuccess;

    private DateDto startDate;

    private DateDto endDate;

}
