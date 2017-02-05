package blog.controllersRest;

import blog.forms.PostForm;
import blog.models.Comment;
import blog.models.Post;
import blog.models.User;
import blog.services.interfaces.PostService;
import blog.services.interfaces.StorageService;
import blog.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PostRestCont {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @Autowired
    StorageService storageService;

    @RequestMapping("/rest/posts/latest")
    public List<Post> getLatestPosts(){
       return postService.getLatest5Posts();
    }

    @RequestMapping("/rest/your-posts/latest")
    public List<Post> getAuthUserLast5Posts(){

        Long userId = userService.getAuthenticatedUser().getId();
        return postService.getLatest5PostsUser(userId);
    }

    @RequestMapping("/rest/posts/{id}")
    public Post getPostById(@PathVariable("id") Long id) {
        return postService.getPostById(id);
    }


    @RequestMapping(value = "/rest/create-post", method = RequestMethod.POST)
    public String savePost(PostForm postForm,
                           @RequestParam(value="picture", required = false) MultipartFile picture,
                           Model model){

        Post postForSave = new Post();

        postForSave.setTitle(postForm.getTitle());
        postForSave.setDescription(postForm.getDescription());
        postForSave.setAuthor(userService.getAuthenticatedUser());

        if(picture != null) {
            if (!picture.isEmpty()) {
                String imageName = storageService.savePostImage(picture);

                if (imageName == null) {
                    model.addAttribute("msg",
                            "Error, please try again. Size of the image cannot be more than 10MB.");
                    return "JSONERROR2"; // TODO json response
                }

                postForSave.setIcon(imageName);
            }
        }

        postService.savePost(postForSave);

        return "Successful";
    }

    @RequestMapping(value = "/rest/posts/{id}", method = RequestMethod.POST)
    public String commentOnPost(@PathVariable("id") Long id,
                                @RequestParam(value = "comment", required = false) String comment) {

        if(comment == null) return "error : No comment"; // TODO JSON response

        User authUser = userService.getAuthenticatedUser();
        Post post = postService.getPostById(id);

        Comment commentToSave = new Comment();
        commentToSave.setAuthor(authUser);
        commentToSave.setPost(post);
        commentToSave.setComment(comment);

        postService.saveComment(commentToSave);

        return "Successful"; // TODO JSON response
    }

    @RequestMapping(value = "/rest/posts/{id}/comments")
    public List<Comment> getPostComments(@PathVariable("id") Long id) {

        return postService.getPostById(id).getComments();

    }

    @RequestMapping(value = "/rest/posts", method = RequestMethod.GET)
    public List<Post> get5PostsBeforeId(@RequestParam("postsBeforeId") Long id) {

        return postService.find5BeforeId(id);
    }

    @RequestMapping(value = "/rest/your-posts", method = RequestMethod.GET)
    public List<Post> get5UserPostsBeforeId(@RequestParam("postsBeforeId") Long postsBeforeId) {

        Long userId = userService.getAuthenticatedUser().getId();
        return postService.find5BeforeIdForUser(userId, postsBeforeId);
    }
}
