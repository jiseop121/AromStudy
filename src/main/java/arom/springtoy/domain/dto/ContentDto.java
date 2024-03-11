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
public class ContentDto {

    @NotNull @NotBlank
    @Size(min = 1, max = 30)
    private String contentName;

    @NotNull @NotBlank
    private String description;
}
