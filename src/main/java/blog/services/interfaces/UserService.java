package blog.services.interfaces;

import blog.models.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {

    User getUserByUsername(String username);

    User getUserByUrl(String url);

    User getAuthenticatedUser();

    boolean checkPassword(String password, String hashPass);

    void updateUser(User user, String newPass);

    void deleteUser(User user) throws IOException;

}
