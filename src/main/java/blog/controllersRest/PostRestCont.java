package blog.controllersRest;

import blog.models.Post;
import blog.services.interfaces.PostService;
import blog.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostRestCont {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @RequestMapping("/rest/posts/latest10")
    public List<Post> getLatest10Posts(){
       return postService.getLatest10Posts();
    }

    @RequestMapping("/rest/account/posts")
    public List<Post> getAuthUserPosts(){
        return userService.getAuthenticatedUser().getPosts();
    }

    @RequestMapping("/rest/posts/{id}")
    public Post getPostById(@PathVariable("id") Long id) {
        return postService.getPostById(id);
    }

}
