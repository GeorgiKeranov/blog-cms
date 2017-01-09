package blog.repositories;

import blog.models.Post;
import blog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findFirst5ByOrderByDateDesc();
    List<Post> findByOrderByDateDesc();
    List<Post> findByAuthorOrderByDateDesc(User author);
}
