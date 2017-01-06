package blog.services.interfaces;

import blog.models.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StorageService {

    public final static String usersImgsDirectory = "/home/georgi/Server/images/";
    public final static String postsImgsDirectory = "/home/georgi/Server/image-post/";
    public final static String publicUserImgs = "/res/images/";

    public String savePostImage(MultipartFile file);
    public String saveUserImage(MultipartFile file);
    public List<Image> userImages();
    public String getUserDirectory();
    public Image ImageUserById(Long id);
}
