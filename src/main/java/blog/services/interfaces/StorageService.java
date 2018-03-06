package blog.services.interfaces;

import blog.models.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StorageService {

    final static String usersImgsDirectory = "/home/georgi/Server/images/";
    final static String postsImgsDirectory = "/home/georgi/Server/image-post/";

    // THE NAMES OF THE PICTURES ARE UNIQUE. Even if you save picture with the same name.

    // Saving image in directory(usersImgsDirectory + userUrl of authenticated user).
    // Then mapping name of the picture in database, table user, column : profile_picture.
    boolean saveProfilePicture(MultipartFile file);

    // Saving image in postImgsDirectory and returning it name.
    String savePostImage(MultipartFile file);

    // Saving image in directory (usersImgsDirectory + userUrl of the authenticated user).
    // Then mapping name of the image in the database, table images.
    String saveUserImage(MultipartFile file);

    // Getting saved by authenticated user pictures' names.
    List<Image> getUserImages();

    // Getting image name from database in table images.
    Image ImageUserById(Long id);

    // 2 Options to delete image by id from database
    // or to delete it by location.
    void deleteUserImageById(Long id, String Location) throws IOException;

    void deletePostImage(String directory) throws IOException;

}
