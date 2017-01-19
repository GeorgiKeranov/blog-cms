package blog.controllers;

import blog.forms.EditAccountForm;
import blog.models.Image;
import blog.models.User;
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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
public class AccountController {

    @Autowired
    StorageService storageService;

    @Autowired
    UserService userService;

    @RequestMapping("/account")
    public String showMyAccountPage(Model model){
        User authUser = userService.getAuthenticatedUser();
        model.addAttribute("user", authUser);
        return "/account/account";
    }

    @RequestMapping("/account/edit")
    public String showEditAccountPage(EditAccountForm editAccountForm, Model model){

        User authUser = userService.getAuthenticatedUser();
        model.addAttribute("user", authUser);

        return "/account/editAccount";
    }

    @RequestMapping(value = "/account/edit", method = RequestMethod.POST)
    public String setAccountDetails(Model model,
                                    @RequestParam(name = "profilePic", required = false) MultipartFile profilePic,
                                    @Valid EditAccountForm form, BindingResult bindingResult){

        User authUser = userService.getAuthenticatedUser();

        if(bindingResult.hasErrors()) {
            model.addAttribute("user", authUser);
            return "/account/editAccount";
        }

        String pass = form.getCurPassword();

        boolean isPasswordCorrect = userService.checkPassword(pass, authUser.getPassword());
        if(!isPasswordCorrect){
            model.addAttribute("user", authUser);
            model.addAttribute("errMsg", "Current Password is incorrect!");
            return "/account/editAccount";
        }

        String newPass = form.getNewPassword();
        String confirmNewPass = form.getConfirmNewPassword();

        String theNewPassword = null;
        if(!(newPass.equals("") && confirmNewPass.equals(""))){
            if(!newPass.equals(confirmNewPass)){
                model.addAttribute("user", userService.getAuthenticatedUser());
                model.addAttribute("errMsg", "New password and confirm password are not the same");
                return "/account/editAccount";
            }
          theNewPassword = newPass;
        }

        authUser.setFirstName(form.getFirstname());
        authUser.setLastName(form.getLastname());
        authUser.setEmail(form.getEmail());

        userService.updateUser(authUser, theNewPassword);

        if(!profilePic.isEmpty()) {
            boolean noError = storageService.saveProfilePicture(profilePic);
            if(!noError) {
                model.addAttribute("user", userService.getAuthenticatedUser());
                return "/account/editAccount";
            }
        }

        return "redirect:/account";
    }

    @RequestMapping("/{userUrl:.+}")
    public String viewUserAccount(@PathVariable("userUrl") String userUrl, Model model){
        User user = userService.getUserByUrl(userUrl);
        if(user == null) return "redirect:/";

        model.addAttribute("user", user);
        return "/account/viewOtherUser";
    }

    @RequestMapping("/account/loadPics")
    public String getImageById(Model model){

        List<Image> images = storageService.getUserImages();
        model.addAttribute("images", images);
        return "/tests/loadPics";
    }

    @RequestMapping("/account/delete")
    public String deleteAccount(HttpServletRequest request){

        User authUser = userService.getAuthenticatedUser();

        try {
            userService.deleteUser(authUser);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            request.logout();
        } catch (ServletException e) {
            e.printStackTrace();
        }

        return "redirect:/login?deleted=true";
    }
}
