package blog.repositories;

import blog.models.Post;
import blog.models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {


    @Query("select p from Post p order by p.date desc")
    List<Post> findPostsOnPage(Pageable pageable);

    @Query("select p from Post p where p.author.userUrl = ?1 order by p.date desc")
    List<Post> findPostsByAuthorOnPage(String userUrl, Pageable pageable);

    List<Post> findByOrderByDateDesc();

    List<Post> findByAuthorOrderByDateDesc(User author);
}
