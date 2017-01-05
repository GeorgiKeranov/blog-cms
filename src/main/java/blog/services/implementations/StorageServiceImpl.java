package blog.services.implementations;

import blog.models.Image;
import blog.models.User;
import blog.repositories.ImageRepository;
import blog.services.interfaces.StorageService;
import blog.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    UserService userService;

    @Autowired
    ImageRepository imageRepo;

    @Override
    public String saveFile(MultipartFile file) {
        if(file.isEmpty()) return "Please choice an image";

        try {
            Long KB = file.getSize()/1024;
            Long MB = KB/1024;

            if(MB > 4) return "Image is more than 2 MB";

            // Getting the authenticated user.
            User authUser = userService.getAuthenticatedUser();

            // Making directory for images for that username.
            String locationForSave = images_directory + authUser.getUsername();

            // Creating unique name for the image.
            String nameOfImage = new Date() + file.getOriginalFilename();

            Path location = Paths.get(locationForSave);
            if(!Files.exists(location)) Files.createDirectory(location);

            Files.copy(file.getInputStream(), location.resolve(nameOfImage));

            Image image = new Image();
            image.setOriginalName(file.getOriginalFilename());
            image.setImgName(nameOfImage);
            image.setUser(authUser);
            imageRepo.save(image);

            return "Image is successful uploaded to server";

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Error, Please try again";
    }

    @Override
    public List<Image> userImages() {

        User authUser = userService.getAuthenticatedUser();

        Long user_id = authUser.getId();
        List<Image> images = imageRepo.findByUser_id(user_id);
        if(images.isEmpty()) return null; //TODO handle nulls in the controllers.

        return images;
    }

    public String getDynamicDirectory(){
        String authUsername = userService.getAuthenticatedUser().getUsername();
        return public_directory + authUsername + "/";
    }

    @Override
    public Image userImageById(Long id) {

        imageRepo.findOne(id);
        


        return null;
    }
}
