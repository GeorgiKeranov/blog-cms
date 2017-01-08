package blog.services.interfaces;

import blog.models.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StorageService {

    public final static String usersImgsDirectory = "/home/georgi/Server/images/";
    public final static String postsImgsDirectory = "/home/georgi/Server/image-post/";

    // THE NAMES OF THE PICTURES ARE UNIQUE. Even if you save picture with the same name.

    // Saving image in directory(usersImgsDirectory + username of authenticated user)/
    // Then mapping name of the picture in database, table user.
    public boolean saveProfilePicture(MultipartFile file);

    // Saving image in postImgsDirectory and saving name of the image in the database, table posts.
    public String savePostImage(MultipartFile file);

    // Saving image in directory (usersImgsDirectory + username of the authenticated user).
    // Then mapping name of the image in the database, table images.
    public String saveUserImage(MultipartFile file);

    // Getting saved by authenticated user pictures' names.
    public List<Image> getUserImages();

    // Getting image name from database in table images.
    public Image ImageUserById(Long id);

}
