package blog.controllersRest;

import blog.forms.PostForm;
import blog.models.Comment;
import blog.models.Post;
import blog.models.User;
import blog.services.interfaces.PostService;
import blog.services.interfaces.StorageService;
import blog.services.interfaces.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class PostCont {

    @Autowired
    PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private StorageService storageService;


    // Methods that are saving in database.

    // This method is creating new Post in the database.
    @RequestMapping(value = "/rest/create-post", method = RequestMethod.POST)
    public String createNewPost(PostForm postForm,
                                @RequestParam(value="picture", required = false) MultipartFile picture){

        JSONObject response = new JSONObject();

        Post postForSave = new Post();

        postForSave.setTitle(postForm.getTitle());
        postForSave.setDescription(postForm.getDescription());
        postForSave.setAuthor(userService.getAuthenticatedUser());

        if(picture != null) {
            if (!picture.isEmpty()) {
                String imageName = storageService.savePostImage(picture);

                if (imageName == null) {
                    response.put("error", true);
                    response.put("error_msg", "Error, please try again. Size of the image cannot be more than 10MB.");
                    return response.toString();
                }

                postForSave.setIcon(imageName);
            }
        }

        postService.savePost(postForSave);

        response.put("error", false);
        return response.toString();
    }

    // This method is commenting on given by id Post.
    @RequestMapping(value = "/rest/posts/{id}/comment", method = RequestMethod.POST)
    public String commentOnPost(@PathVariable("id") Long id,
                                @RequestParam(value = "comment", required = false) String comment) {

        JSONObject response = new JSONObject();
        if(comment == null) {
            response.put("error", true);
            response.put("error_msg", "There is not comment.");
            return response.toString();
        }

        User authUser = userService.getAuthenticatedUser();
        Post post = postService.getPostById(id);

        Comment commentToSave = new Comment();
        commentToSave.setAuthor(authUser);
        commentToSave.setPost(post);
        commentToSave.setComment(comment);

        postService.saveComment(commentToSave);

        response.put("error", false);
        return response.toString();
    }


    // Methods that are loading from the database.

    // This method is getting the id of the requested post
    // in the url and returning it as JSON object.
    @RequestMapping(value = "/rest/posts/{id}", method = RequestMethod.GET)
    public Post getPostById(@PathVariable("id") Long id) {
        return postService.getPostById(id);
    }

    // This method is returning latest 5 posts ordered
    // by date from the database as JSON array.
    @RequestMapping(value = "/rest/posts/latest", method = RequestMethod.GET)
    public List<Post> getLatest5Posts(){
        return postService.getLatest5Posts();
    }

    // This method is returning latest 5 posts for user given by userUrl.
    @RequestMapping(value = "/rest/{userUrl:.+}/latest-posts", method = RequestMethod.GET)
    public List<Post> getLatest5UserPosts(@PathVariable("userUrl") String userUrl) {
        Long userId = userService.getUserByUrl(userUrl).getId();
        return postService.getLatest5PostsUser(userId);
    }

    // This method is returning latest 5 posts for the authenticated user.
    @RequestMapping(value = "/rest/account/latest-posts", method = RequestMethod.GET)
    public List<Post> getLatest5AuthenticatedUserPosts() {
        Long authUserId = userService.getAuthenticatedUser().getId();
        return postService.getLatest5PostsUser(authUserId);
    }

    // This method is used to return 5 Posts ordered by date before given id of other Post.
    @RequestMapping(value = "/rest/posts", method = RequestMethod.GET)
    public List<Post> get5PostsBeforeId(@RequestParam("postsBeforeId") Long id) {
        return postService.find5BeforeId(id);
    }

    // This method is used to return 5 Posts by the user given by userUrl
    // ordered by date before the given id of other user's Post.
    @RequestMapping(value = "/rest/{userUrl:.+}/posts", method = RequestMethod.GET)
    public List<Post> get5UserPostsBeforeId(@PathVariable("userUrl") String userUrl,
                                            @RequestParam("postsBeforeId") Long postsBeforeId) {

        Long userId = userService.getUserByUrl(userUrl).getId();
        return postService.find5BeforeIdForUser(userId, postsBeforeId);
    }

    @RequestMapping(value = "/rest/account/posts", method = RequestMethod.GET)
    public List<Post> get5AuthenticatedUserPostsBeforeId(
            @RequestParam("postsBeforeId") Long postsBeforeId) {

        Long userId = userService.getAuthenticatedUser().getId();
        return postService.find5BeforeIdForUser(userId, postsBeforeId);
    }

    // This method is getting id of post and return the author(as User object) of that post.
    @RequestMapping(value = "/rest/posts/{id}/author", method = RequestMethod.GET)
    public User getAuthorByPostId(@PathVariable("id") Long id) {
        return postService.getPostById(id).getAuthor();
    }

    // This method is returning all the comments and replies
    // on given Post by id.
    @RequestMapping(value = "/rest/posts/{id}/comments", method = RequestMethod.GET)
    public List<Comment> getPostComments(@PathVariable("id") Long id) {
        return postService.getPostById(id).getComments();
    }


    // Methods that are editing in the database.

    // This method is editing post by given id
    // It can change the image, title and description of the post.
    @RequestMapping(value = "/rest/posts/{id}", method = RequestMethod.POST, produces = "application/json")
    public String editPostById(@PathVariable("id") Long id,
                               @RequestParam(value = "picture", required = false) MultipartFile picture,
                               @RequestParam("title") String title,
                               @RequestParam("description") String description) {

        JSONObject response = new JSONObject();

        Post post = postService.getPostById(id);

        // If post is null we are returning error.
        if(post == null) {
            response.put("error", true);
            response.put("error_msg", "Post cannot be found.");
            return response.toString();
        }

        if(picture != null) {
            String picName = storageService.savePostImage(picture);
            post.setIcon(picName);
        }

        post.setTitle(title);
        post.setDescription(description);

        postService.savePost(post);

        // Adding field "error" : false to JSON object to show that there isn't error.
        response.put("error", false);

        // Returning the JSON object as String
        return response.toString();
    }


    // This method is deleting post if the post's author is authenticated user.
    @RequestMapping(value = "/rest/posts/{id}/delete", method = RequestMethod.POST, produces = "application/json")
    public String deletePostById(@PathVariable("id") Long id) {

        User authUser = userService.getAuthenticatedUser();

        Post postForDelete = postService.getPostById(id);

        if(postForDelete.getAuthor().getUsername().equals(authUser.getUsername())) {
            postService.deletePostById(id);
            return "{ \"deleted\": true }";
        }

        return "{ \"deleted\": false }";
    }
}
