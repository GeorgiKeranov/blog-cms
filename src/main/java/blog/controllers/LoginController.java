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
    public String showLoginPage(Model model,
                                @RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout,
                                @RequestParam(value = "regSuccess", required = false) String regSuccess){

        if(error != null) model.addAttribute("loginError", true);
        if(logout != null) model.addAttribute("logout", true);
        if(regSuccess != null) model.addAttribute("regSuccess", regSuccess);

        return "/account/login";
    }

}