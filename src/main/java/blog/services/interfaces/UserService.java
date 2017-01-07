package blog.services.interfaces;

import blog.models.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    public User getAuthenticatedUser();
    public boolean checkPassword(String password);
    public void updateUser(User user);
}
