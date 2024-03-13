package arom.springtoy.domain.controller.service;

import arom.springtoy.domain.domain.Content;
import arom.springtoy.domain.dto.ContentDto;
import arom.springtoy.domain.dto.PutContentDto;
import arom.springtoy.domain.service.ContentService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("todolist/{todolistId}/content")
@Slf4j
public class ContentController {

    private final ContentService contentService;

    @GetMapping
    public List<Content> mainContent(@PathVariable("todolistId") Long todolistId, HttpServletRequest request){
        contentService.blockContent(request,todolistId);
        List<Content> allByTodolistId = contentService.findAllByTodolistId(todolistId);
        for (Content content : allByTodolistId) {
            log.info(content.getContentName());
        }
        List<Content> allByTodolistId1 = contentService.findAllByTodolistId(1L);
        for (Content content : allByTodolistId1) {
            log.info(content.getContentName());
        }

        return contentService.findAllByTodolistId(todolistId);
    }

    @PostMapping("/add")
    public Content addContent(@PathVariable("todolistId") Long todolistId, @RequestBody ContentDto contentDto, HttpServletRequest request){
        contentService.blockContent(request,todolistId);
        return contentService.addContent(todolistId,contentDto);
    }

    @PostMapping("/{contentId}/put")
    public Content putContent(@PathVariable("todolistId") Long todolistId, @PathVariable("contentId") Long contentId,@RequestBody PutContentDto putContentDto, HttpServletRequest request){
        contentService.blockContent(request,todolistId);
        return contentService.modifyContent(todolistId,contentId,putContentDto);
    }

    @PostMapping("/{contentId}/delete")
    public String deleteContent(@PathVariable("todolistId") Long todolistId, @PathVariable("contentId") Long contentId, @RequestBody PutContentDto putContentDto, HttpServletRequest request){
        contentService.blockContent(request,todolistId);
        return contentService.deleteContent(todolistId,contentId)+"-> delete ok";
    }
}
