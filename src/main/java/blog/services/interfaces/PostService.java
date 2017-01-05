package blog.services.interfaces;

import blog.models.Post;

import java.util.List;

/**
 * Created by Georgi on 22.12.2016 г..
 */
public interface PostService {

    public List<Post> getLast5Posts();
    public Post getPostById(Long id);
    public List<Post> getAllPosts();
    public void savePost(Post post);
}
