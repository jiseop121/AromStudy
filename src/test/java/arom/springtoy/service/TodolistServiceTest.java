package arom.springtoy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import arom.springtoy.todolist.domain.Todolist;
import arom.springtoy.todolist.dto.DateDto;
import arom.springtoy.todolist.dto.PutTodolistDto;
import arom.springtoy.todolist.dto.TodolistDto;
import arom.springtoy.todolist.repository.TodolistRepository;
import arom.springtoy.todolist.service.TodolistService;
import arom.springtoy.user.domain.User;
import arom.springtoy.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
@Transactional
class TodolistServiceTest {
    @Autowired
    private TodolistService todolistService;
    @Autowired
    private TodolistRepository todolistRepository;
    @Autowired
    private UserRepository userRepository;

    private User existedUser;
    private Todolist existedTodolist;

    private static final DateDto RIGHT_START_DATE = new DateDto(
        2024, 3, 12, 14, 30
    );
    private static final DateDto RIGHT_END_DATE = new DateDto(
        2024, 4, 13, 15, 59
    );



    @BeforeEach
    void setUp() {
        existedUser = userRepository.findById(1L).get();
        existedTodolist = todolistRepository.findAllByUser(existedUser).get(0);
    }

    /**
     * ADD TEST
     */
    @Test
    void 정상_등록(){
        TodolistDto rightTodolistDto = new TodolistDto(
            "testTodoListName",
            "jiseop",
            RIGHT_START_DATE,
            RIGHT_END_DATE
        );
        Long addTodolistId = todolistService.addTodolist(existedUser, rightTodolistDto);
        assertThat(todolistRepository.findById(addTodolistId).get().getTodoListName()).isEqualTo(rightTodolistDto.getTodolistName());
        assertThat(todolistRepository.findById(addTodolistId).get().getUser()).isEqualTo(existedUser);
    }

    @Test
    void 비정상_등록_유저_리스트중_이미_있는_네임(){
        String existedTodolistName = existedTodolist.getTodoListName();

        TodolistDto wrongTodolistDto = new TodolistDto(
            existedTodolistName,
            "jiseop",
            RIGHT_START_DATE,
            RIGHT_END_DATE
        );

        assertThatThrownBy(() -> todolistService.addTodolist(existedUser, wrongTodolistDto)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void 비정상_등록_startdate_is_after_than_enddate(){
        TodolistDto wrongTodolistDto = new TodolistDto(
            "new_test_test_todolist_name",
            "jiseop",
            RIGHT_END_DATE,
            RIGHT_START_DATE

        );
        assertThatThrownBy(() -> todolistService.addTodolist(existedUser, wrongTodolistDto)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void 비정상_등록_dto_value_empty(){
        TodolistDto wrongTodolistDto = new TodolistDto(
        );
        assertThatThrownBy(() -> todolistService.addTodolist(existedUser, wrongTodolistDto)).isInstanceOf(
            NullPointerException.class);
    }

    /**
     * PUT TEST
     * NULL값은 수정이 없고, 값이 들어있는 것만 수정
     */
    @Test
    void 정상_수정(){

        String beforeWriter = existedTodolist.getWriter();
        Boolean beforeIsSuccess = existedTodolist.getIsSuccess();
        LocalDateTime beforeStartDate = existedTodolist.getStartDate();

        PutTodolistDto rightPutTodolistDto = new PutTodolistDto(
            existedTodolist.getTodoListName(),
            "new_put_writer",
            true,
            null,
            null
        );
        Todolist rightPutTodolist = todolistService.modifyTodolist(existedUser,existedTodolist.getTodolistId() ,rightPutTodolistDto);
        Todolist todolistAfterPut = todolistRepository.findAllByUser(existedUser).get(0);

        assertThat(rightPutTodolist.getTodolistId())
            .isEqualTo(todolistAfterPut.getTodolistId());
        assertThat(rightPutTodolist.getStartDate())
            .isEqualTo(todolistAfterPut.getStartDate());

        assertThat(beforeWriter)
            .isNotEqualTo(todolistAfterPut.getWriter());
        assertThat(beforeIsSuccess)
            .isNotEqualTo(todolistAfterPut.getIsSuccess());
        assertThat(beforeStartDate)
            .isEqualTo(todolistAfterPut.getStartDate());
        assertThat(beforeStartDate.isEqual(todolistAfterPut.getStartDate()))
            .isEqualTo(true);
    }

    @Test
    void 비정상_수정_투두리스트_이름은_존재_유저의_리스트가_아닌경우(){
        PutTodolistDto wrongUserTodolist = new PutTodolistDto(
            existedTodolist.getTodoListName(),
            "new_put_writer",
            true,
            null,
            null
        );
        User otherUser = userRepository.findById(2L).get();
        assertThatThrownBy(() -> todolistService.modifyTodolist(otherUser,existedTodolist.getTodolistId(),wrongUserTodolist))
            .isInstanceOf(RuntimeException.class);
    }

    @Test
    void 비정상_수정_이미_삭제되거나_없는_투두리스트_id_조회(){
        PutTodolistDto wrongUserTodolist = new PutTodolistDto(
            existedTodolist.getTodoListName(),
            "new_put_writer",
            true,
            null,
            null
        );
        User otherUser = userRepository.findById(2L).get();
        assertThatThrownBy(() -> todolistService.modifyTodolist(otherUser,3L,wrongUserTodolist))
            .isInstanceOf(RuntimeException.class);
    }

    /**
     * DELETE TEST
     */
    @Test
    void 정상_삭제(){
        todolistService.deleteTodolist(existedUser,existedTodolist.getTodolistId());

        assertThat(todolistRepository.findById(existedTodolist.getTodolistId())).isEqualTo(Optional.empty());
    }

    @Test
    void 비정상_삭제_이미삭제된_투두리스트(){
        todolistService.deleteTodolist(existedUser,existedTodolist.getTodolistId());
        assertThatThrownBy(() -> todolistService.deleteTodolist(existedUser,existedTodolist.getTodolistId()))
            .isInstanceOf(RuntimeException.class);
    }

    @Test
    void 비정상_삭제_다른유저가_삭제시도(){
        User otherUser = userRepository.findById(2L).get();

        assertThatThrownBy(() -> todolistService.deleteTodolist(otherUser,existedTodolist.getTodolistId()))
            .isInstanceOf(RuntimeException.class);
    }
}