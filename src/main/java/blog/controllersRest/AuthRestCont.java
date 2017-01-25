package blog.controllersRest;

import blog.services.interfaces.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthRestCont {

    @Autowired
    UserService userService;

    @RequestMapping("/rest/is-auth")
    public String isAuthenticated(){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("auth_error", false);

        return jsonObject.toString();
    }


}
