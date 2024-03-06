package arom.springtoy.content.domain;

import arom.springtoy.todolist.domain.Todolist;
import jakarta.persistence.CascadeType;
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
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

@Entity
@Getter
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contentId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "todolist_id")
    private Todolist todolist;

    @NotNull @NotBlank
    @Size(min = 1, max = 30)
    private String contentName;

    @NotNull @NotBlank
    private String description;

    @NotNull
    private Boolean isSuccess;

    public Content(Todolist todolist, String contentName, String description) {
        this.todolist = todolist;
        this.contentName = contentName;
        this.description = description;
        this.isSuccess = false;
    }

    public Content() {

    }
}
