package blog.controllers;

import blog.models.Comment;
import blog.models.Post;
import blog.forms.PostForm;
import blog.models.Reply;
import blog.models.User;
import blog.repositories.ImageRepository;
import blog.services.interfaces.PostService;
import blog.services.interfaces.StorageService;
import blog.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private StorageService storageService;

    @RequestMapping("/create-post")
    public String createPost(PostForm postForm){
        return "posts/createPost";
    }

    @RequestMapping(value = "/create-post", method = RequestMethod.POST)
    public String savePost(@Valid PostForm postForm, BindingResult bindingResult,
                           @RequestParam("picture") MultipartFile picture, Model model){

        if(bindingResult.hasErrors()) return "/posts/createPost";

        Post postForSave = new Post();

        if(!picture.isEmpty()) {
            String imageName = storageService.savePostImage(picture);
            if (imageName == null) {
                model.addAttribute("msg", "Error, please try again. Size of the image cannot be more than 5MB.");
                return "/posts/createPost";
            }
            postForSave.setIcon(imageName);
        }

        postForSave.setTitle(postForm.getTitle());
        postForSave.setDescription(postForm.getDescription());
        postForSave.setAuthor(userService.getAuthenticatedUser());

        postService.savePost(postForSave);

        return "redirect:/";
    }

    @RequestMapping("/posts/{id}")
    public String curPost(@PathVariable("id") Long id, Model model){
        model.addAttribute("post", postService.getPostById(id));
        User authUser = userService.getAuthenticatedUser();
        if(authUser != null)
            model.addAttribute("user", authUser);
        return "posts/currentPost";
    }

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.POST)
    public String postCommentOrReply(@PathVariable("id") Long id,
                                     @RequestParam(value = "comment", required = false) String comment,
                                     @RequestParam(value = "reply", required = false) String reply,
                                     @RequestParam(value = "commentForReply", required = false) Long commentId){

        if(comment == null && reply== null) return "redirect:/posts/" + id;

        User authUser = userService.getAuthenticatedUser();
        if(authUser == null) return "redirect:/login";



        Post curPost = postService.getPostById(id);

        if(comment != null) {
            Comment commentForSave = new Comment();
            commentForSave.setAuthor(authUser);
            commentForSave.setComment(comment);
            commentForSave.setPost(curPost);

            postService.saveComment(commentForSave);
        }

        if(reply != null){
            Reply replyForSave = new Reply();
            replyForSave.setAuthor(authUser);
            replyForSave.setReply(reply);
            Comment commentForReply = postService.getCommentById(commentId);
            replyForSave.setComment(commentForReply);

            postService.saveReply(replyForSave);
        }

        return "redirect:/posts/" + id;
    }


    @RequestMapping("/posts/edit/{id}")
    public String editPost(@PathVariable("id") Long id, PostForm postForm, Model model){

        User user = userService.getAuthenticatedUser();
        String username = user.getUsername();

        Post curPost = postService.getPostById(id);
        String postOwner = curPost.getAuthor().getUsername();

        if(username.equals(postOwner)){
            model.addAttribute("post", curPost);
            return "posts/editPost";
        }

        return "redirect:/";
    }

    @RequestMapping(value = "/posts/edit/{id}", method = RequestMethod.POST)
    public String editPostByParams(@PathVariable("id") Long id,
                                   @Valid PostForm postForm, BindingResult bindingResult,
                                   Model model, MultipartFile picture){

        User user = userService.getAuthenticatedUser();
        String username = user.getUsername();

        Post post = postService.getPostById(id);
        String postOwner = post.getAuthor().getUsername();

        if(username.equals(postOwner)) {

            post.setTitle(postForm.getTitle());
            post.setDescription(postForm.getDescription());

            if (!picture.isEmpty())
                post.setIcon(storageService.savePostImage(picture));

            postService.savePost(post);
            return "redirect:/posts/";
        }

        return "redirect:/";

    }

    @RequestMapping(value = "/posts")
    public String showAllPosts(Model model){

        User authUser = userService.getAuthenticatedUser();
        List<Post> userPosts = postService.getUserPosts(authUser);

        model.addAttribute("posts", userPosts);

        return "/posts/showAllPosts";
    }

    @RequestMapping("/posts/delete/{id}")
    public String deletePost(@PathVariable("id") Long id){

        User authUser = userService.getAuthenticatedUser();

        if(authUser == null) return "redirect:/posts";

        String authUsername = authUser.getUsername();

        Post postForDel = postService.getPostById(id);
        String postOwner = postForDel.getAuthor().getUsername();

        if(authUsername.equals(postOwner)){
            postService.deletePostById(postForDel.getId());
        }

        return "redirect:/posts";
    }

}
