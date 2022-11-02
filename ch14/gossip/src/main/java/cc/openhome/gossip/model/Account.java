package cc.openhome.gossip.model;

public class Account {
    private String name;
    private String email;
    private String password;
    private Boolean enable;

    public Account() {
    }

    public Account(String name, String email, String password, Boolean enable) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.enable = enable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
