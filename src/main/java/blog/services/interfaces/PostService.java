package blog.services.interfaces;

import blog.models.Post;
import blog.models.User;

import java.util.List;

public interface PostService {

    public List<Post> getLast5Posts();
    public Post getPostById(Long id);
    public List<Post> getAllPosts();
    public void savePost(Post post);
    public List<Post> getUserPosts(User user);
    public void deletePostById(Long id);
}
