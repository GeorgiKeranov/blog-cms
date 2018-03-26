package blog.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String icon = "no"; // This is default value if there is not icon.

    @Column(columnDefinition = "text")
    private String description;

    @JsonIgnore
    @ManyToOne(optional = false)
    private User author;

    @Column(nullable = false)
    private Date date = new Date();

    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
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

    public String getDate() {
        return date.toString();
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @JsonIgnore
    @Transient
    public String getSummaryTitle(){
        if(title.length() > 37) {
            String summary = title.substring(0, 34);
            return summary + "...";
        }
        return title;
    }

    @JsonIgnore
    @Transient
    public String getSummaryDesc(){
        if(description.length() > 325){
            String summary = description.substring(0, 325);
            return summary + "...";
        }
        return description;
    }

}
