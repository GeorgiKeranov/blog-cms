package blog.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegisterForm {

    @Size(min=5, max=40, message = "Please enter username between 5 and 40 characters")
    private String username;

    @Size(min=5, max=40, message = "Please enter password between 5 and 40 characters")
    @NotNull(message = "Password cannot be empty")
    private String password;

    @Size(min=5, max=40, message = "Please enter password between 5 and 40 characters")
    @NotNull(message = "Password cannot be empty")
    private String password1;

    @Size(min=1, message = "Full name cannot be empty!")
    private String fullName;


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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}
