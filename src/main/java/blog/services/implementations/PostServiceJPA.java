package blog.services.implementations;

import blog.models.Post;
import blog.models.User;
import blog.repositories.PostRepository;
import blog.services.interfaces.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceJPA implements PostService {

    @Autowired
    PostRepository postRepo;

    @Override
    public List<Post> getLast5Posts() {
        return postRepo.findFirst5ByOrderByDateDesc();
    }

    @Override
    public Post getPostById(Long id) {
        return postRepo.findOne(id);
    }

    @Override
    public List<Post> getAllPosts(){
        return postRepo.findByOrderByDateDesc();
    }

    @Override
    public void savePost(Post post) {
        postRepo.save(post);
    }

    @Override
    public List<Post> getUserPosts(User user) {

        return postRepo.findByAuthorOrderByDateDesc(user);
    }

    @Override
    public void deletePostById(Long id) {
        postRepo.delete(id);
    }
}
