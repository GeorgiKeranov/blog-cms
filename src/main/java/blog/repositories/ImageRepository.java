package blog.repositories;

import blog.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByUser_id(Long id);
}
