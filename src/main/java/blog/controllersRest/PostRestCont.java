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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class PostRestCont {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @Autowired
    StorageService storageService;

    // This method is returning latest 5 posts ordered
    // by date from the database as JSON array.
    @RequestMapping("/rest/posts/latest")
    public List<Post> getLatest5Posts(){
        return postService.getLatest5Posts();
    }


    // This method is returning latest 5 posts by
    // the authenticated user ordered by date.
    @RequestMapping("/rest/your-posts/latest")
    public List<Post> getAuthUserLast5Posts(){

        Long userId = userService.getAuthenticatedUser().getId();
        return postService.getLatest5PostsUser(userId);
    }


    // This method is getting the id of the requested post
    // in the url and returning it as JSON object.
    @RequestMapping(value = "/rest/posts/{id}", method = RequestMethod.GET)
    public Post getPostById(@PathVariable("id") Long id) {
        return postService.getPostById(id);
    }


    // This method is editing post by given id
    // It can change the image, title and description of the post.
    @RequestMapping(value = "/rest/posts/{id}", method = RequestMethod.POST)
    public String editPostById(@PathVariable("id") Long id,
                               @RequestParam(value = "picture", required = false) MultipartFile picture,
                               @RequestParam("title") String title,
                               @RequestParam("description") String description) {

        Post post = postService.getPostById(id);

        if(picture != null) {
            String picName = storageService.savePostImage(picture);
            post.setIcon(picName);
        }

        post.setTitle(title);
        post.setDescription(description);

        postService.savePost(post);

        return "Successful"; // TODO JSON
    }


    // This method is creating new Post in the database.
    @RequestMapping(value = "/rest/create-post", method = RequestMethod.POST)
    public String createNewPost(PostForm postForm,
                           @RequestParam(value="picture", required = false) MultipartFile picture){

        Post postForSave = new Post();

        postForSave.setTitle(postForm.getTitle());
        postForSave.setDescription(postForm.getDescription());
        postForSave.setAuthor(userService.getAuthenticatedUser());

        if(picture != null) {
            if (!picture.isEmpty()) {
                String imageName = storageService.savePostImage(picture);

                if (imageName == null) {
                    return "{ \"error\": true, \"error_msg\": \"Error, please try again. Size of the image cannot be more than 10MB.\" }";
                }

                postForSave.setIcon(imageName);
            }
        }

        postService.savePost(postForSave);

        return "Successful";
    }

    // This method is commenting on given by id Post.
    @RequestMapping(value = "/rest/posts/{id}/comment", method = RequestMethod.POST)
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

    // This method is returning all the comments and replies
    // on given Post by id.
    @RequestMapping(value = "/rest/posts/{id}/comments")
    public List<Comment> getPostComments(@PathVariable("id") Long id) {

        return postService.getPostById(id).getComments();

    }

    // This method is used to return 5 Posts
    // ordered by date before given id of other Post.
    // And returns JSON array.
    @RequestMapping(value = "/rest/posts", method = RequestMethod.GET)
    public List<Post> get5PostsBeforeId(@RequestParam("postsBeforeId") Long id) {
        return postService.find5BeforeId(id);
    }

    // This method is used to return 5 Posts by the authenticated user
    // ordered by date before the given id of other user's Post.
    @RequestMapping(value = "/rest/your-posts", method = RequestMethod.GET)
    public List<Post> get5UserPostsBeforeId(@RequestParam("postsBeforeId") Long postsBeforeId) {

        Long userId = userService.getAuthenticatedUser().getId();
        return postService.find5BeforeIdForUser(userId, postsBeforeId);
    }

}