package arom.springtoy.domain.validation;

import arom.springtoy.domain.controller.user.UserSession;
import arom.springtoy.domain.domain.Content;
import arom.springtoy.domain.domain.Todolist;
import arom.springtoy.domain.exception.ContentException;
import arom.springtoy.global.validation.GlobalValidation;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class ContentValidation extends GlobalValidation {

    public ContentValidation(UserSession userSession) {
        super(userSession);
    }

    public void checkTodolistExist(Optional<Todolist> todolist) {
        if(todolist.isEmpty()){
            throw new ContentException("cannot search your todolist");
        }
    }

    public void checkContentExist(Optional<Content> content) {
        if(content.isEmpty()){
            throw new ContentException("cannot search your content in this todolist");
        }
    }

    public void checkModifyContentRoute(Optional<Content> content){
        if (content.isEmpty()){
            throw new ContentException("when you modified your content, set wrong route");
        }
    }
}
