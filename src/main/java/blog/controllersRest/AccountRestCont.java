package blog.controllersRest;

import blog.models.User;
import blog.services.interfaces.StorageService;
import blog.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AccountRestCont {

    @Autowired
    private UserService userService;

    @Autowired
    private StorageService storageService;

    // This method is returning the authenticated user's object as JSON object.
    @RequestMapping(value = "/rest/account", method = RequestMethod.GET)
    public User getAuthUserInfo() {
        return userService.getAuthenticatedUser();
    }


    // This method is editing the authenticated user's details in the database.
    // And if there are errors it is returning message of the error as JSON.
    @RequestMapping(value = "/rest/account", method = RequestMethod.POST, produces = "application/json")
    public String editAuthUser(@RequestParam(value = "picture", required = false) MultipartFile picture,
                               @RequestParam("firstName") String firstName,
                               @RequestParam("lastName") String lastName,
                               @RequestParam("email") String email,
                               @RequestParam(value = "newPassword", required = false) String newPassword,
                               @RequestParam("currPassword") String curPassword) {

        User authUser = userService.getAuthenticatedUser();

        // Check if the given password matches the authenticated user's password.
        boolean isCurrPasswordCorrect = userService.checkPassword(curPassword, authUser.getPassword());

        // If the passwords are not the same send json error.
        if(!isCurrPasswordCorrect)
            return "{ \"error\": true, \"error_msg\": \"Current password is invalid\" }";

        // If picture is given it is changing the authenticated user's profile picture.
        if(picture != null) {
            Boolean isSaved = storageService.saveProfilePicture(picture);
            if(!isSaved) return "{ \"error\": true, \"error_msg\": \"Error with saving the image\" }";
        }

        authUser.setFirstName(firstName);
        authUser.setLastName(lastName);
        authUser.setEmail(email);

        // Updating the user and sending the new password.
        // If the newPassword is null this method will not change it.
        userService.updateUser(authUser, newPassword);

        return "{ \"error\": false }";
    }


    @RequestMapping(value = "/rest/{userUrl:.+}", method = RequestMethod.GET)
    public User getUserByUserUrl(@PathVariable("userUrl") String userUrl) {
        return userService.getUserByUrl(userUrl);
    }
}
