package blog.services.implementations;

import blog.models.Comment;
import blog.models.Post;
import blog.models.Reply;
import blog.models.User;
import blog.repositories.CommentRepository;
import blog.repositories.PostRepository;
import blog.repositories.ReplyRepository;
import blog.services.interfaces.PostService;
import blog.services.interfaces.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class PostServiceJPA implements PostService {

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private ReplyRepository replyRepo;

    @Autowired
    private StorageService storageService;

    @Override
    public List<Post> getLatest5Posts() {
        return postRepo.findFirst5ByOrderByDateDesc();
    }

    @Override
    public List<Post> getLatest5PostsUser(Long authorId) {
        return postRepo.findByUserLatest5Posts(authorId, new PageRequest(0, 5));
    }

    @Override
    public List<Post> find5BeforeId(Long id) {
        return postRepo.find5BeforeId(id, new PageRequest(0, 5));
    }

    @Override
    public List<Post> find5BeforeIdForUser(Long authorId, Long postsBeforeId) {
        return postRepo.find5BeforeIdUser(authorId, postsBeforeId, new PageRequest(0, 5));
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

        String pictureName = postRepo.getOne(id).getIcon();

        // Checks if user have profile picture.
        if(!pictureName.equals("no")) {
            try {
                // Deleting the picture from the server storage.
                storageService.deletePostImage(pictureName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Deleting the post from the database.
        postRepo.delete(id);
    }

    @Override
    public void saveComment(Comment comment) {
        commentRepo.save(comment);
    }

    @Override
    public void deleteComment(Comment comment) {
        commentRepo.delete(comment);
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentRepo.getOne(id);
    }

    @Override
    public void saveReply(Reply reply) {
        replyRepo.save(reply);
    }

    @Override
    public Reply getReplyById(Long replyId) {
        return replyRepo.getOne(replyId);
    }

    @Override
    public void deleteReply(Reply reply) {
        replyRepo.delete(reply);
    }

}
