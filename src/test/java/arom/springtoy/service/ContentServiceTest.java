package arom.springtoy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import arom.springtoy.domain.domain.Content;
import arom.springtoy.domain.domain.Todolist;
import arom.springtoy.domain.domain.User;
import arom.springtoy.domain.dto.ContentDto;
import arom.springtoy.domain.dto.DateDto;
import arom.springtoy.domain.dto.PutContentDto;
import arom.springtoy.domain.repository.ContentRepository;
import arom.springtoy.domain.repository.TodolistRepository;
import arom.springtoy.domain.repository.UserRepository;
import arom.springtoy.domain.service.ContentService;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
@Transactional
public class ContentServiceTest {
    @Autowired
    private TodolistRepository todolistRepository;
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private ContentService contentService;
    @Autowired
    private UserRepository userRepository;

    private User existedUser;
    private Todolist existedTodolist;
    private Todolist addedTodolist;

    @BeforeEach
    void setUp() {
        existedUser = userRepository.findById(1L).get();
        existedTodolist = todolistRepository.findAllByUser(existedUser).get(0);

        //새로운 투두리스트 등록
        DateDto startDate = new DateDto(
            2024,3,6,14,23
        );
        DateDto endDate = new DateDto(
            2024,4,20,15,30
        );

        Todolist newTodolist = new Todolist(
            existedUser,
            "testWriter1",
            "test_new_testTodolistName1",
            startDate,
            endDate
        );

        addedTodolist = todolistRepository.save(newTodolist);
        log.info("saved.getTodolistId() == {}",addedTodolist.getTodolistId());
    }

    @Test
    void 비정상_path_wrong_user(){
        User otherUser = userRepository.findById(2L).get();

//        assertThatThrownBy(() -> contentService.blockContent(new LoginDto(otherUser.getEmail(), "1234"),
//            existedTodolist.getTodolistId()))
//            .isInstanceOf(RuntimeException.class);
    }

    @Test
    void 비정상_path_wrong_todolistId_contentId(){
        Todolist otherTodolist = todolistRepository.findAllByUser(existedUser).get(1);

        assertThatThrownBy(() -> contentService.deleteContent(otherTodolist.getTodolistId(), 1L))
            .isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> contentService.modifyContent(otherTodolist.getTodolistId(), 1L, new PutContentDto()))
            .isInstanceOf(RuntimeException.class);

    }
    @Test
    void 비정상_path_wrong_contentId(){
        Todolist otherTodolist = todolistRepository.findAllByUser(existedUser).get(1);

        ContentDto newContentDto = new ContentDto(
            "new_test_234534",
            "new test description"
        );
        Content addedContent = contentService.addContent(otherTodolist.getTodolistId(), newContentDto);
        assertThatThrownBy(() -> contentService.modifyContent(existedTodolist.getTodolistId(),addedContent.getContentId(),new PutContentDto()))
            .isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() ->  contentService.deleteContent(existedTodolist.getTodolistId(),addedContent.getContentId()))
            .isInstanceOf(RuntimeException.class);
    }

    @Test
    void 정상_등록(){
        ContentDto rightContentDto = new ContentDto(
            "test_new_content_1231",
            "this is a test content"
        );
        Content addedContent = contentService.addContent(existedTodolist.getTodolistId(),
            rightContentDto);
        assertThat(addedContent.getTodolist().getTodolistId()).isEqualTo(existedTodolist.getTodolistId());
    }

    @Test
    void 비정상_등록_content_validation(){
        ContentDto emptyContentName = new ContentDto(
            "",
            "this is a test content"
        );
        assertThatThrownBy(() -> contentService.addContent(existedTodolist.getTodolistId(),
            emptyContentName)).isInstanceOf(ConstraintViolationException.class).hasMessageContaining("공백일 수 없습니다").hasMessageContaining("크기가 1에서 30 사이");


        ContentDto emptyDescription = new ContentDto(
            "test_new_content_1231",
            ""
        );
        assertThatThrownBy(() -> contentService.addContent(existedTodolist.getTodolistId(),
            emptyDescription)).isInstanceOf(ConstraintViolationException.class).hasMessageContaining("공백일 수 없습니다");
    }

    @Test
    void 정상_수정_투두리스트_이동_포함(){

        Long originLocaTodolistId = addedTodolist.getTodolistId();
        Long changeLocaTodolistId = 1L;

        //content 넣기
        ContentDto newContentDto = new ContentDto(
            "new_test_234534",
            "new test description"
        );

        contentService.addContent(originLocaTodolistId,newContentDto);

        log.info("todolistRepository.existsById(1L) == {}",todolistRepository.existsById(1L));
        log.info("todolistRepository.existsById(originLocaTodolistId) == {}",todolistRepository.existsById(originLocaTodolistId));

        Content originContent = contentRepository.findAllByTodolist(todolistRepository.findById(originLocaTodolistId).get()).get(0);

        //2번째 투두리스트의 content를 1번째 투두리스트로 이동 및 content 내용 변경
        PutContentDto rightPutContentDto = new PutContentDto(
            todolistRepository.findById(changeLocaTodolistId).get().getTodoListName(),
            "test_change_content_name",
            null,
            true
        );

        Content modifiedContent = contentService.modifyContent(originLocaTodolistId,
            originContent.getContentId(),
            rightPutContentDto);

        //수정 내용 일치
        assertThat(contentRepository.findById(modifiedContent.getContentId()).get().getContentName())
            .isEqualTo(rightPutContentDto.getContentName());
        assertThat(contentRepository.findById(modifiedContent.getContentId()).get().getDescription())
            .isEqualTo(originContent.getDescription());
        assertThat(contentRepository.findById(modifiedContent.getContentId()).get().getIsSuccess())
            .isEqualTo(rightPutContentDto.getIsSuccess());
        assertThat(contentRepository.findById(modifiedContent.getContentId()).get().getContentName())
            .isEqualTo(rightPutContentDto.getContentName());
        assertThat(originContent.getContentId())
            .isEqualTo(modifiedContent.getContentId());

        //투두리스트 이동확인
        assertThat(modifiedContent.getTodolist()).isEqualTo(todolistRepository.findById(changeLocaTodolistId).get());
        assertThat(contentRepository.findByContentIdAndTodolist(modifiedContent.getContentId(), todolistRepository.findById(changeLocaTodolistId).get()).get().getContentName())
            .isEqualTo(rightPutContentDto.getContentName());
        assertThat(contentRepository.findByContentIdAndTodolist(modifiedContent.getContentId(),todolistRepository.findById(originLocaTodolistId).get()).isEmpty())
            .isEqualTo(true);
    }

    @Test
    void 비정상_수정_콘텐츠명_글자수_제한() {

    }

    @Test
    @Transactional
    void 정상_삭제(){
        Content existContent = contentRepository.findAllByTodolist(existedTodolist).get(0);
        String existContentName = existContent.getContentName();
        String deletedContentName = contentService.deleteContent(existedTodolist.getTodolistId(),
            existContent.getContentId());
        assertThat(existContentName).isEqualTo(deletedContentName);
    }

    @Test
    void 비정상_삭제(){

    }
}
