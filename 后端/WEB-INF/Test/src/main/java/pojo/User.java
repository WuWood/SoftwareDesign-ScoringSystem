package pojo;

public class User {
    private int id; //id
    private String username; //用户名称
    private String password; //用户密码
    private String WeChatCode; //微信登录的唯一标识
    private int level; //权限等级（1普通用户，2评委，3管理员）

    //Getter和Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getWeChatCode() { return WeChatCode; }

    public void setWeChatCode(String weChatCode) { WeChatCode = weChatCode; }

    public int getLevel() { return level; }

    public void setLevel(int level) { this.level = level; }
}
