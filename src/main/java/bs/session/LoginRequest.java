package bs.session;

/**
 * @author yzy
 */
public class LoginRequest {
    private String uid;
    private String password;

    public String getUid() {
        return uid;
    }

    public String getPassword() {
        return password;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
