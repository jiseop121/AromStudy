package arom.springtoy.domain.domain;

import arom.springtoy.domain.dto.DateDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;

@Entity
@Getter
public class Todolist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todolistId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull @NotBlank
    @Column(unique = true)
    private String todoListName;

    @NotNull @NotBlank
    @Size(min = 1, max = 30)
    private String writer;

    @NotNull
    private Boolean isSuccess;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;

    public Todolist() {

    }

    public void modifyOnlyServiceTodoListName(String todoListName) {
        this.todoListName = todoListName;
    }

    public void modifyOnlyServiceWriter(String writer) {
        this.writer = writer;
    }

    public void modifyOnlyServiceIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public void modifyOnlyServiceStartDate(DateDto startDate) {
        this.startDate =getDate(startDate);
    }

    public void modifyOnlyServiceEndDate(DateDto endDate) {
        this.endDate = getDate(endDate);
    }

    public Todolist(User user, String writer, String todoListName,
        DateDto startDate, DateDto endDate) {
        this.user = user;
        this.writer = writer;
        this.todoListName = todoListName;
        this.isSuccess = false;
        this.startDate = getDate(startDate);
        this.endDate = getDate(endDate);
    }

    private LocalDateTime getDate(DateDto dateDto) {
        return LocalDateTime.of(dateDto.getYear(), dateDto.getMonth(),
            dateDto.getDayOfMonth(), dateDto.getHour(), dateDto.getMinute());
    }
}
