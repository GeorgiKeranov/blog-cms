package blog.services.interfaces;

import blog.models.Comment;
import blog.models.Post;
import blog.models.Reply;
import blog.models.User;

import java.util.List;

public interface PostService {

    List<Post> getAllPosts();

    List<Post> getUserPosts(User user);

    List<Post> getLatest5Posts();

    List<Post> getLatest5PostsUser(Long authorId);

    List<Post> find5BeforeId(Long id);

    List<Post> find5BeforeIdForUser(Long authorId, Long postsBeforeId);


    // Comment and reply functions.

    Post getPostById(Long id);

    void savePost(Post post);

    void deletePostById(Long id);


    Comment getCommentById(Long id);

    void saveComment(Comment comment);

    void deleteComment(Comment comment);


    void saveReply(Reply reply);

    Reply getReplyById(Long replyId);

    void deleteReply(Reply reply);
}
