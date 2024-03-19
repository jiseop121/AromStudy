package arom.springtoy.domain.repository;

import arom.springtoy.domain.domain.Todolist;
import arom.springtoy.domain.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodolistRepository extends JpaRepository<Todolist, Long> {

    Optional<Todolist> findByTodoListName(String todolistName);

    Optional<Todolist> findByTodoListNameAndUser(String todolistName, User user);

    List<Todolist> findAllByUser(User user);

    void deleteByTodolistId(Long todolistId);

    Optional<Todolist> findByTodolistIdAndUser(Long todolistId, User user);

}
