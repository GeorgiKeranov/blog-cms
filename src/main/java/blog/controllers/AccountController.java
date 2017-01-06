package blog.controllers;

import blog.models.Image;
import blog.services.interfaces.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class AccountController {

    @Autowired
    StorageService storageService;

    @RequestMapping("/account")
    public String showMyAccountPage(Model model){
        return "/account/myAccount";
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
