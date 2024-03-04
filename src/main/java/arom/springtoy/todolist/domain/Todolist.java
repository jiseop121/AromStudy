package arom.springtoy.todolist.domain;

import arom.springtoy.todolist.dto.DateDto;
import arom.springtoy.user.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
public class Todolist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todolistId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @Size(min = 1, max = 64)
    private String writer;

    @NotNull
    private Boolean isSuccess;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;

    public Todolist(User user, String writer, Boolean isSuccess,
        DateDto startDate, DateDto endDate) {
        this.user = user;
        this.writer = writer;
        this.isSuccess = isSuccess;
        this.startDate = getDate(startDate);
        this.endDate = getDate(endDate);
    }

    private static LocalDateTime getDate(DateDto dateDto) {
        return LocalDateTime.of(dateDto.getYear(), dateDto.getMonth(),
            dateDto.getDayOfMonth(), dateDto.getHour(), dateDto.getMinute());
    }

    public Todolist() {

    }
}
