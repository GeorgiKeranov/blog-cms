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

    // This method is checking if the client is authenticated.
    // And returning JSON object that shows that.
    @RequestMapping(value = "/rest/authentication", produces = "application/json")
    public String isAuthenticated() {

        JSONObject response = new JSONObject();

        if(userService.getAuthenticatedUser() != null)
            response.put("authenticated", true);
        else response.put("authenticated", false);

        return response.toString();
    }


}
