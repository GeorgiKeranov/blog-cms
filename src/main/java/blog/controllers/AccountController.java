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

    @RequestMapping("/myaccount")
    public String showMyAccountPage(Model model){
        return "/account/myAccount";
    }

    @RequestMapping(value = "/myaccount", method = RequestMethod.POST)
    public String uploadImg(@RequestParam("picture") MultipartFile picture, Model model){

        String msg = storageService.saveFile(picture);
        model.addAttribute("msg", msg);
        return "/account/myAccount";
    }

    @RequestMapping("/myaccount/loadPics")
    public String getImageById(Model model){


        return "loadPics";
    }

}
