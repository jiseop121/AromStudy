package arom.springtoy.content.repository;

import arom.springtoy.content.domain.Content;
import arom.springtoy.todolist.domain.Todolist;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRepository extends JpaRepository<Content,Long> {
    Optional<Content> findByContentIdAndTodolist(Long contentId, Todolist todolist);

    List<Content> findAllByTodolist(Todolist todolist);
}
