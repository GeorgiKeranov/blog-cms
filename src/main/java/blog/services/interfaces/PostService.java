package blog.services.interfaces;

import blog.models.Comment;
import blog.models.Post;
import blog.models.Reply;
import blog.models.User;

import java.util.List;

public interface PostService {

    List<Post> getAllPosts();
    List<Post> getUserPosts(User user);
    Post getPostById(Long id);
    List<Post> getPostsOnPage(int page);
    List<Post> getPostsByAuthorOnPage(String userUrl, int page);

    void savePost(Post post);
    void deletePostById(Long id);
    Comment getCommentById(Long id);
    void saveComment(Comment comment);
    void deleteComment(Comment comment);

    void saveReply(Reply reply);
    Reply getReplyById(Long replyId);
    void deleteReply(Reply reply);
}
