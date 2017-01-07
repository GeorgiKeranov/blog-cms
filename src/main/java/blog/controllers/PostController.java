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

    @RequestMapping("/posts/{id}")
    public String curPost(@PathVariable("id") Long id, Model model){
        model.addAttribute("post", postService.getPostById(id));
        return "posts/currentPost";
    }

    @RequestMapping("/post/edit/{id}")
    public String editPost(@PathVariable("id") Long id, PostForm postForm, Model model){

        Post curPost = postService.getPostById(id);

        postForm.setAuthor(curPost.getAuthor());
        postForm.setDescription(curPost.getDescription());
        postForm.setTitle(curPost.getTitle());

        return "posts/editPost";
    }

    @RequestMapping(value = "/post/edit/{id}", method = RequestMethod.POST)
    public String editPostbyParams(@Valid PostForm postForm, BindingResult bindingResult, Model model){

        //TODO make edit. (update the database .update() with postservice)

        return "redirect:/posts/";

    }

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

}
