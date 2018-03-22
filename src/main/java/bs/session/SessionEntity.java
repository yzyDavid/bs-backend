package bs.session;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author yzy
 */
@Entity
@Table(name="SESSION_ENTITY",
        indexes = {
        @Index(name = "emailIndex", columnList = "email", unique = true),
        @Index(name = "tokenIndex", columnList = "token")
})
public class SessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 80)
    private String email;

    /**
     * TODO: expire control
     */
    @GeneratedValue()
    private Timestamp timestamp;

    @Column(length = 100)
    private String token;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
