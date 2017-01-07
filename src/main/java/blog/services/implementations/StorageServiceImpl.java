package blog.services.implementations;

import blog.models.Image;
import blog.models.User;
import blog.repositories.ImageRepository;
import blog.repositories.PostRepository;
import blog.repositories.UserRepository;
import blog.services.interfaces.StorageService;
import blog.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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

    @Autowired
    UserRepository userRepo;

    @Override
    public String savePostImage(MultipartFile file) {
        if(file.isEmpty()) return null;

        try {
            Long KB = file.getSize()/1024;
            Long MB = KB/1024;

            if(MB > 5) return null;

            // Creating unique name for the image.
            String nameOfImage = new Date() + file.getOriginalFilename();

            Path location = Paths.get(postsImgsDirectory);
            Files.copy(file.getInputStream(), location.resolve(nameOfImage));

            return nameOfImage;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String saveUserImage(MultipartFile file) {
        if(file.isEmpty()) return "Image is empty.";

        try {
            Long KB = file.getSize()/1024;
            Long MB = KB/1024;

            if(MB > 5) return "Size of the picture cannot be more than 5 MB";

            // Getting the authenticated user.
            User authUser = userService.getAuthenticatedUser();

            // Making directory for images for that username.
            String locationForSave = usersImgsDirectory + authUser.getUsername();

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

            return "Successful saved";

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Error, please try again";
    }

    @Override
    public List<Image> userImages() {

        User authUser = userService.getAuthenticatedUser();

        Long user_id = authUser.getId();
        List<Image> images = imageRepo.findByUser_id(user_id);
        if(images.isEmpty()) return null;

        return images;
    }

    @Override //TODO delete this and automate it in the thymeleaf.
    public String getUserDirectory(){
        String authUsername = userService.getAuthenticatedUser().getUsername();
        return publicUserImgs + authUsername + "/";
    }

    @Override
    public Image ImageUserById(Long id) {

        return imageRepo.findOne(id);
    }

    @Override
    public boolean saveProfilePicture(MultipartFile file) {

        try {
            Long KB = file.getSize()/1024;
            Long MB = KB/1024;

            if(MB > 5) return false;

            User authUser = userService.getAuthenticatedUser();

            // Making directory for profile picture for that username.
            String locationForSave = usersImgsDirectory + authUser.getUsername() + "/";
            String nameOfImage = new Date() + file.getOriginalFilename();

            Path location = Paths.get(locationForSave);
            if(!Files.exists(location)) Files.createDirectory(location);

            Files.copy(file.getInputStream(), location.resolve(nameOfImage));

            authUser.setProfile_picture(nameOfImage);
            userRepo.save(authUser);

            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
