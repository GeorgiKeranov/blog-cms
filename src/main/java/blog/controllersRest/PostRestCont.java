package blog.controllersRest;

import blog.models.Post;
import blog.services.interfaces.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostRestCont {

    @Autowired
    PostService postService;

    @RequestMapping("/rest/posts/latest10")
    public List<Post> getLatest10Posts(){
       List<Post> posts = postService.getLatest10Posts();
       for(Post post : posts) {
           post.setAuthor(null);
           post.setComments(null);
       }

       return posts;
    }

}
