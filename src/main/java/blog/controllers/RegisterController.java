package blog.controllers;

import blog.forms.RegisterForm;
import blog.models.Role;
import blog.models.User;
import blog.repositories.RoleRepository;
import blog.services.interfaces.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.HashSet;

@Controller
public class RegisterController {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RegisterService registerService;

    @RequestMapping("/register")
    public String showRegisterPage(RegisterForm registerForm){
        return "/forms/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@Valid RegisterForm registerForm, BindingResult result, Model model){

        if(result.hasErrors()) return "/forms/register";

        boolean password = registerForm.getPassword().equals(registerForm.getPassword1());
        if(!password){
            model.addAttribute("passError", "Passwords are not same!");
            return "/forms/register";
        }

        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

        User userForReg = new User(
                registerForm.getFullName(),
                registerForm.getUsername(),
                bcrypt.encode(registerForm.getPassword()));

        HashSet<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByRole("ROLE_USER"));

        userForReg.setRoles(roles);

        User user = registerService.register(userForReg);

        if(user != null) {
            model.addAttribute("user", user);
            return "home";
        }

        model.addAttribute("errUsername", "Username is already taken! Please try another one.");
        return "/forms/register";
    }

}