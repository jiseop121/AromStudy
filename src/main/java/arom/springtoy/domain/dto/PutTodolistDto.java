package arom.springtoy.domain.dto;

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

    @Size(min = 1, max = 30,message = "너무 길게 입력하지마세요")
    private String writer;

    private Boolean isSuccess;

    private DateDto startDate;

    private DateDto endDate;

}
