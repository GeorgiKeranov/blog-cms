package blog.controllersRest;

import blog.models.User;
import blog.services.implementations.RegisterServiceJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegisterRestCont {

    @Autowired
    private RegisterServiceJPA registerService;

    // This method is saving new user into the database.
    @RequestMapping(value = "/rest/register", method = RequestMethod.POST, produces = "application/json")
    public String register(@RequestParam("firstName") String firstName,
                           @RequestParam("lastName") String lastName,
                           @RequestParam("username") String username,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password){

        // TODO escape ( , . ! @ # $ % ^ & ( ) + - * / .  : ; ? < > / '' "" [] {} ` ~ )

        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();

        User userForReg = new User(
                firstName,
                lastName,
                username,
                email,
                bCrypt.encode(password)
        );

        String message = registerService.register(userForReg);

        if(message != null ) {
                return "{ \"error\": true, \"error_msg\": \"" + message + "\" }";
        }

        return "{ \"error\": false }";
    }

}
