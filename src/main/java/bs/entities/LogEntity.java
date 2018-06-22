package bs.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * @author yzy
 */
@Entity
public class LogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 80)
    private String email;

    private Date date;

    private long studyCount;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getStudyCount() {
        return studyCount;
    }

    public void setStudyCount(long studyCount) {
        this.studyCount = studyCount;
    }
}
