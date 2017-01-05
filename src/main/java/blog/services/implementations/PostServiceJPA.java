package blog.services.implementations;

import blog.models.Post;
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
        Post postById = postRepo.findOne(id);
        return postById;
    }

    @Override
    public List<Post> getAllPosts(){
        return postRepo.findAll();
    }

    @Override
    public void savePost(Post post) {
        postRepo.save(post);
    }
}
