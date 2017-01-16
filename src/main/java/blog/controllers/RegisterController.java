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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        return "/account/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@Valid RegisterForm registerForm, BindingResult result, Model model,
                           RedirectAttributes redirectAttributes){

        if(result.hasErrors()) return "/account/register";

        boolean password = registerForm.getPassword().equals(registerForm.getPassword1());
        if(!password){
            model.addAttribute("passError", "Passwords are not same!");
            return "/account/register";
        }

        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

        String urlForTheAccount = registerService.generateUserUrl(
                registerForm.getFirstname() + "." +
                        registerForm.getLastname()
        );

        User userForReg = new User(
                urlForTheAccount,
                registerForm.getFirstname(),
                registerForm.getLastname(),
                registerForm.getEmail(),
                registerForm.getUsername(),
                bcrypt.encode(registerForm.getPassword()));

        HashSet<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByRole("ROLE_USER"));

        userForReg.setRoles(roles);

        String message = registerService.register(userForReg);

        if(message != null ) {
            if(message.equals("Username is already taken."))
                model.addAttribute("errUsername", message);
            else model.addAttribute("errEmail", message);
            return "/account/register";
        }

        redirectAttributes.addAttribute("regSuccess", "true");
        return "redirect:/login";
    }

}