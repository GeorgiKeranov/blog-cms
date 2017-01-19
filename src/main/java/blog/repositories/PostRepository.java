package blog.repositories;

import blog.models.Comment;
import blog.models.Post;
import blog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    public List<Post> findFirst5ByOrderByDateDesc();
    public List<Post> findByOrderByDateDesc();
    public List<Post> findByAuthorOrderByDateDesc(User author);
}
