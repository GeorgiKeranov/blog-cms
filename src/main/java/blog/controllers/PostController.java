package blog.controllers;

import blog.models.Post;
import blog.forms.PostForm;
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
    PostService postService;

    @Autowired
    UserService userService;

    @Autowired
    StorageService storageService;

    @Autowired
    ImageRepository imageRepository;

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
        return "posts/currentPost";
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
    public String deletePost(@PathVariable("id") Long id, Model model){

        User authUser = userService.getAuthenticatedUser();
        String authUsername = authUser.getUsername();

        Post postForDel = postService.getPostById(id);
        String postOwner = postForDel.getAuthor().getUsername();

        if(authUsername.equals(postOwner)){
            postService.deletePostById(id);
        }

        return "redirect:/posts";
    }

}
