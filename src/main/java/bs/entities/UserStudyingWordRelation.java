package bs.entities;

import javax.persistence.*;

/**
 * @author yzy
 */
@Table(name = "USER_WORD_STUDYING")
@Entity
public class UserStudyingWordRelation {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "USER_ID")
    private long userId;

    @Column(name = "WORD_ID")
    private long wordId;

    private int rank;

    private boolean studied;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getWordId() {
        return wordId;
    }

    public void setWordId(long wordId) {
        this.wordId = wordId;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean isStudied() {
        return studied;
    }

    public void setStudied(boolean studied) {
        this.studied = studied;
    }
}
