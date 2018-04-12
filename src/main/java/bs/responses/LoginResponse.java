package bs.responses;

import org.jetbrains.annotations.Nls;

/**
 * @author yzy
 */
public class LoginResponse {
    private final String uid;
    private final String token;
    private final @Nls
    String info;

    public LoginResponse(String uid, String token, String info) {
        this.uid = uid;
        this.token = token;
        this.info = info;
    }

    public String getUid() {
        return uid;
    }

    public String getToken() {
        return token;
    }

    public String getInfo() {
        return info;
    }
}
