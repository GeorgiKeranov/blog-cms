package blog.services.interfaces;

import blog.models.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {

    public User getUserByUsername(String username);
    public User getUserByUrl(String url);
    public User getAuthenticatedUser();
    public boolean checkPassword(String password, String hashPass);
    public void updateUser(User user, String newPass);
    public void deleteUser(User user) throws IOException;

}
