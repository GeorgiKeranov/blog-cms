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
    private StorageService storageService;

    @Autowired
    private UserService userService;

    @RequestMapping("/account")
    public String showMyAccountPage(Model model) {

        User authUser = userService.getAuthenticatedUser();
        model.addAttribute("user", authUser);

        return "/account/account";
    }

    @RequestMapping("/account/edit")
    public String showEditAccountPage(EditAccountForm editAccountForm, Model model) {

        User authUser = userService.getAuthenticatedUser();
        model.addAttribute("user", authUser);

        return "/account/editAccount";
    }

    @RequestMapping(value = "/account/edit", method = RequestMethod.POST)
    public String setAccountDetails(Model model,
                                    @RequestParam(name = "profilePic", required = false) MultipartFile profilePic,
                                    @Valid EditAccountForm form, BindingResult bindingResult){

        User authUser = userService.getAuthenticatedUser();

        // Checks if there are errors in EditAccountForm.
        if(bindingResult.hasErrors()) {
            model.addAttribute("user", authUser);
            return "/account/editAccount";
        }

        String pass = form.getCurPassword();

        // Checks if the entered password by authenticated user is the same as his/her's account.
        boolean isPasswordCorrect = userService.checkPassword(pass, authUser.getPassword());

        // If password is incorrect add message to model and return the same page.
        if(!isPasswordCorrect){
            model.addAttribute("user", authUser);
            model.addAttribute("errMsg", "Current Password is incorrect!");
            return "/account/editAccount";
        }

        String newPass = form.getNewPassword();
        String confirmNewPass = form.getConfirmNewPassword();

        String theNewPassword = null;

        // Checks if new password and confirm new password aren't empty.
        if(!(newPass.equals("") && confirmNewPass.equals(""))){

            // Checks if new password and confirm new password aren't equal.
            if(!newPass.equals(confirmNewPass)){
                model.addAttribute("user", userService.getAuthenticatedUser());
                model.addAttribute("errMsg", "New password and confirm password are not the same");
                return "/account/editAccount";
            }

            // We will need this variable below when updating the user info.
            theNewPassword = newPass;
        }

        authUser.setFirstName(form.getFirstName());
        authUser.setLastName(form.getLastName());
        authUser.setEmail(form.getEmail());

        // Updating the user info and sending newPassword (if the new password
        // is null it will not change the authenticated user's password).
        userService.updateUser(authUser, theNewPassword);

        // Checks if profilePic multipart file is not empty.
        if(!profilePic.isEmpty()) {

            // Saving profile picture and getting false if there is error.
            boolean noError = storageService.saveProfilePicture(profilePic);

            if(!noError) {
                model.addAttribute("user", userService.getAuthenticatedUser());
                return "/account/editAccount";
            }
        }

        // And finally if you are there it will redirect you to your account page.
        return "redirect:/account";
    }

    // This url is for viewing other users and their posts.
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

    // This url is deleting your account.
    @RequestMapping(value = "/account/delete", method = RequestMethod.POST)
    public String deleteAccount(HttpServletRequest request){

        User authUser = userService.getAuthenticatedUser();

        try {
            userService.deleteUser(authUser);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // This logout the authenticated user directly.
            request.logout();
        } catch (ServletException e) {
            e.printStackTrace();
        }

        return "redirect:/login?deleted=true";
    }
}
