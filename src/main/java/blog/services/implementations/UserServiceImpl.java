package blog.services.implementations;

import blog.models.*;
import blog.repositories.CommentRepository;
import blog.repositories.ReplyRepository;
import blog.repositories.RoleRepository;
import blog.repositories.UserRepository;
import blog.services.interfaces.PostService;
import blog.services.interfaces.StorageService;
import blog.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    RoleRepository roleRepo;

    @Autowired
    CommentRepository commentRepo;

    @Autowired
    ReplyRepository replyRepo;


    @Autowired
    PostService postService;

    @Autowired
    StorageService storageService;


    @Override
    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public User getUserByUrl(String url) {
        return userRepo.findByUserUrl(url);
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

    @Override
    public void deleteUser(User user) throws IOException {
        //Deleting all images of user.
        List<Image> images = user.getImages();
        for(Image img : images)
            storageService.deleteUserImageById(img.getId(), null);

        //Deleting profile picture
        storageService.deleteUserImageById(null, user.getProfile_picture());

        //Deleting posts pictures.
        List<Post> posts = user.getPosts();
        for(Post post : posts)
            storageService.deletePostImage(post.getIcon());

        userRepo.delete(user.getId());
    }


}
