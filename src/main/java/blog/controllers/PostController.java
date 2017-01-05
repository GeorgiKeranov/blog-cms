package blog.controllers;

import blog.models.Post;
import blog.forms.PostForm;
import blog.models.User;
import blog.services.interfaces.PostService;
import blog.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @RequestMapping("/posts/{id}")
    public String curPost(@PathVariable("id") Long id, Model model){

        model.addAttribute("post", postService.getPostById(id));
        return "posts/currentPost";
    }

    @RequestMapping("/post/edit/{id}")
    public String editPost(@PathVariable("id") Long id, PostForm postForm, Model model){

        Post curPost = postService.getPostById(id);

        postForm.setAuthor(curPost.getAuthor());
        postForm.setBody(curPost.getBody());
        postForm.setTitle(curPost.getTitle());

        return "posts/editPost";
    }

    @RequestMapping(value = "/post/edit/{id}", method = RequestMethod.POST)
    public String editPostbyParams(@Valid PostForm postForm, BindingResult bindingResult, Model model){

        //TODO make edit.

        return "redirect:/posts/";

    }

    @RequestMapping("/create-post")
    public String createPost(PostForm postForm, Model model){

        // Finding the authenticated user.
        User user = userService.getAuthenticatedUser();
        model.addAttribute("loggedUser", user);

        return "posts/createPost";
    }

    @RequestMapping(value = "/create-post", method = RequestMethod.POST)
    public String savePost(@Valid PostForm postForm, BindingResult bindingResult,
                           Model model){

        if(bindingResult.hasErrors()) return "/posts/createPost";

        Post postForSave = new Post();
        postForSave.setTitle(postForm.getTitle());
        postForSave.setBody(postForm.getBody());
        postForSave.setAuthor(userService.getAuthenticatedUser());

        postService.savePost(postForSave);

        return "redirect:/";
    }

}
