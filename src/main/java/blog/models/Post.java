package blog.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String icon;

    @Column(length = 500)
    private String description;

    @ManyToOne(optional = false)
    private User author;

    @Column(nullable = false)
    private Date date = new Date();

    @OneToMany(mappedBy = "post")
    @OrderBy("date")
    private List<Comment> comments;

    public Post(){

    }

    public Post(String title, String description, User author) {
        this.title = title;
        this.description = description;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Transient
    public String getSummaryTitle(){
        if(title.length() > 15) {
            String summary = title.substring(0, 15);
            return summary + "...";
        }
        return title;
    }

    @Transient
    public String getSummaryDesc(){
        if(description.length() > 325){
            String summary = description.substring(0, 325);
            return summary + "...";
        }
        return description;
    }

}
