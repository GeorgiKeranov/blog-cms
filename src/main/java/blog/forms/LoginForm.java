package blog.forms;

import javax.validation.constraints.Size;

public class LoginForm {

    @Size(min=1, message = "Username is empty! Please enter username.")
    private String username;

    @Size(min=1, message = "Password is empty! Please enter password.")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
