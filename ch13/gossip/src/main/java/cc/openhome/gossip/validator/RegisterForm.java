package cc.openhome.gossip.validator;


import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class RegisterForm {

    @Email(message = "邮件格式不正确")
    private String email;

    @Size(min = 1, max = 16, message = "用户名格式不正确")
    private String username;

    @Size(min = 8, max = 16, message = "密码格式不正确")
    private String password;

    private String password2;

    @AssertTrue(message = "请确认两次密码一致")
    public boolean isValid() {
        return password != null && password.equals(password2);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }
}
