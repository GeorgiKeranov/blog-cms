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
    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedUser = auth.getName();
        return userRepo.findByUsername(loggedUser);
    }

    @Override
    public boolean checkPassword(String password, String hashPass) {

        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();

        if(bCrypt.matches(password, hashPass)) return true;

        return false;
    }

    @Override
    public void updateUser(User user, String newPass) {

        if(newPass != null) {
            BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
            String hashPass = bcrypt.encode(newPass);
            user.setPassword(hashPass);
        }

        userRepo.save(user);
    }
}
