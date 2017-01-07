package blog.services.implementations;

import blog.models.User;
import blog.repositories.UserRepository;
import blog.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepo;

    @Override
    public User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedUser = auth.getName();
        return userRepo.findByUsername(loggedUser);
    }

    @Override
    public boolean checkPassword(String password) {
        User authUser = getAuthenticatedUser();
        String userPass = authUser.getPassword();

        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

        if(bcrypt.matches(password, userPass)) return true;

        return false;
    }

    @Override
    public void updateUser(User user) {
        User authUser = getAuthenticatedUser();

        user.setId(authUser.getId());
        user.setUsername(authUser.getUsername());
        user.setProfile_picture(authUser.getProfile_picture());
        user.setImages(authUser.getImages());
        user.setPosts(authUser.getPosts());
        user.setRoles(authUser.getRoles());

        if(user.getPassword()  == null) {
            user.setPassword(authUser.getPassword());
        } else {
            BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
            String hashPass = bcrypt.encode(user.getPassword());
            user.setPassword(hashPass);
        }

        userRepo.save(user);
    }
}
