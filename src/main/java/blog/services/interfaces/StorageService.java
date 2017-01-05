package blog.services.interfaces;

import blog.models.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StorageService {

    public final static String images_directory = "/home/georgi/Server/images/";
    public final static String public_directory = "/res/images/";
    public String saveFile(MultipartFile file);
    public List<Image> userImages();
    public String getDynamicDirectory();
    public Image userImageById(Long id);
}
