package blog.services.interfaces;

import blog.models.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    public User getUserByUsername(String username);
    public User getAuthenticatedUser();
    public boolean checkPassword(String password, String hashPass);
    public void updateUser(User user, String newPass);
}
