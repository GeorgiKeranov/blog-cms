package blog.controllers;

import blog.services.implementations.PostServiceJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Autowired
    private PostServiceJPA postServiceJPA;

    @RequestMapping("/")
    public String index(Model model){

        model.addAttribute("allPosts", postServiceJPA.getAllPosts());
        model.addAttribute("last5Posts", postServiceJPA.getLatest10Posts());

        return "home";
    }

}
