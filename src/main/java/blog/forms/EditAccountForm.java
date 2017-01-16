package blog.forms;

import javax.validation.constraints.Size;

public class EditAccountForm {

    @Size(min=2, message = "Please enter a first name with more than 2 characters")
    private String firstname;

    @Size(min=2, message = "Please enter last name with more than 2 characters")
    private String lastname;

    @Size(min=4, message = "Email is not correct.")
    private String email;

    private String newPassword;

    private String confirmNewPassword;

    @Size(min=1, message = "Current password is required!")
    private String curPassword;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    public String getCurPassword() {
        return curPassword;
    }

    public void setCurPassword(String curPassword) {
        this.curPassword = curPassword;
    }
}
