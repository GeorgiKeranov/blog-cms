package blog.repositories;

import blog.models.Comment;
import blog.models.Post;
import blog.models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findFirst5ByOrderByDateDesc();

    // TODO fix pageable
    @Query("select p from Post p where p.author.id = ?1 order by p.date desc")
    List<Post> findByUserLatest5Posts(Long userId, Pageable pageable);

    @Query("select p from Post p where p.id < ?1 order by p.date desc")
    List<Post> find5BeforeId(Long id, Pageable pageable);

    @Query("select p from Post p where p.author.id = ?1 and p.id < ?2 order by p.date desc")
    List<Post> find5BeforeIdUser(Long authorId, Long id, Pageable pageable);

    List<Post> findByOrderByDateDesc();

    List<Post> findByAuthorOrderByDateDesc(User author);
}
