package blog.controllersRest;

import blog.models.Image;
import blog.models.Post;
import blog.models.User;
import blog.services.interfaces.PostService;
import blog.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class RestTest {

    //TODO JsonIgnoreFilter learn it!!

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @RequestMapping("/rest/all-posts")
    public List<Post> getAllPosts(){
        return postService.getAllPosts();
    }

    @RequestMapping("/rest/user")
    public User checkUser(@RequestParam(value = "user", required = false) String username,
                          @RequestParam(value = "pass", required = false) String pass){

        if(username == null || pass == null) return null;

        User user = userService.getUserByUsername(username);
        if(user == null ) return null;

        boolean isCorrect = userService.checkPassword(pass, user.getPassword());

        if(isCorrect) return user;
        else return null;
    }

    @RequestMapping("/rest/user/images")
    public Set<Image> getUserImages(@RequestParam("username") String username){
        User user = userService.getUserByUsername(username);
        return user.getImages();
    }

    @RequestMapping("/rest/user/posts")
    public Set<Post> getUserPosts(@RequestParam("username") String username){

        User user = userService.getUserByUsername(username);
        Set<Post> posts = user.getPosts();
        return posts == null? null :posts;
    }
}
