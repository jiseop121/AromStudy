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
public class TodolistDto {
    @NotNull @NotBlank
    private String todolistName;

    @NotNull @NotBlank
    @Size(min = 1, max = 30)
    private String writer;

    @NotNull
    private DateDto startDate;

    @NotNull
    private DateDto endDate;
}
