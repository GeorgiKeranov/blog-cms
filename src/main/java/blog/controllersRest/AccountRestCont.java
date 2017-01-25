package blog.controllersRest;

import blog.models.User;
import blog.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountRestCont {

    @Autowired
    UserService userService;

}
