package blog.forms;

import blog.models.User;

import javax.validation.constraints.Size;

public class PostForm {

    private User author;
    @Size(min=5, message = "Title must be more than 5 characters")
    private String title;
    @Size(min=5, message = "Body must be more than 5 characters")
    private String description;

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
