package blog.services.interfaces;

import blog.models.Comment;
import blog.models.Post;
import blog.models.Reply;
import blog.models.User;

import java.util.List;

public interface PostService {


    public List<Post> getAllPosts();

    public List<Post> getLatest5Posts();

    public List<Post> getLatest5PostsUser(Long authorId);

    public List<Post> find5BeforeId(Long id);

    public List<Post> find5BeforeIdForUser(Long authorId, Long postsBeforeId);

    public Post getPostById(Long id);

    public void savePost(Post post);

    public void deletePostById(Long id);

    public List<Post> getUserPosts(User user);

    // Comment and reply functions.
    public Comment getCommentById(Long id);

    public void saveComment(Comment comment);

    public void saveReply(Reply reply);

    public void deletePost(Post post);

    //TODO Delete all user images : delete folder with account username.
}
