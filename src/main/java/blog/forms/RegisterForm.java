package blog.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegisterForm {

    @Size(min=3, message = "Please enter a valid email")
    private String firstName;

    @Size(min=3, message = "Please enter a valid email")
    private String lastName;

    @Size(min=5, max=40, message = "Please enter username between 5 and 40 characters")
    private String username;

    @Size(min=3, message = "Please enter a valid email")
    private String email;

    @Size(min=6, max=40, message = "Please enter password between 5 and 40 characters")
    private String password;

    @Size(min=6, max=40, message = "Please enter password between 5 and 40 characters")
    private String password1;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
