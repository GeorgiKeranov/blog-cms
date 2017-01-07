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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
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

        if(bindingResult.hasErrors()) {
            model.addAttribute("user", userService.getAuthenticatedUser());
            return "/account/editAccount";
        }

        String pass = form.getCurPassword();

        boolean isPasswordCorrect = userService.checkPassword(pass);
        if(!isPasswordCorrect){
            model.addAttribute("user", userService.getAuthenticatedUser());
            model.addAttribute("errMsg", "Current Password is incorrect!");
            return "/account/editAccount";
        }

        User userDetails = new User();

        String newPass = form.getNewPassword();
        String confirmNewPass = form.getConfirmNewPassword();

        if(!(newPass.equals("") && confirmNewPass.equals(""))){
            if(!newPass.equals(confirmNewPass)){
                model.addAttribute("user", userService.getAuthenticatedUser());
                model.addAttribute("errMsg", "New password and confirm password are not the same");
                return "/account/editAccount";
            }
            userDetails.setPassword(newPass);
        }

        userDetails.setFirstName(form.getFirstname());
        userDetails.setLastName(form.getLastname());
        userDetails.setEmail(form.getEmail());

        userService.updateUser(userDetails);

        if(profilePic != null) {
            boolean noError = storageService.saveProfilePicture(profilePic);
            if(!noError) {
                model.addAttribute("user", userService.getAuthenticatedUser());
                return "/account/editAccount";
            }
        }

        return "redirect:/account";
    }

    @RequestMapping("/account/loadPics")
    public String getImageById(Model model){

        List<Image> images = storageService.userImages();
        String linkImages = storageService.getUserDirectory();
        model.addAttribute("images", images);
        model.addAttribute("linkImgs", linkImages);
        return "/tests/loadPics";
    }

}
