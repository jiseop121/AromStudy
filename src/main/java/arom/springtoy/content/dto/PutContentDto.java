package arom.springtoy.content.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PutContentDto {

    private String todolistName;
    private String contentName;
    private String description;
    private Boolean isSuccess;

}
