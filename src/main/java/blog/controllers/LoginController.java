package blog.controllers;

import blog.forms.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String showLoginPage(@Valid LoginForm loginForm, BindingResult bindingResult, Model model,
                                @RequestParam(value = "error", required = false) String error){

        if(error != null) model.addAttribute("loginError", true);

        return "/forms/login";
    }

}