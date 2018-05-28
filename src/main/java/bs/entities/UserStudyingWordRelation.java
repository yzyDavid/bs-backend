package bs.entities;

import bs.configs.Config;

import javax.persistence.*;

/**
 * @author yzy
 * TODO: set primary key
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

    /**
     * A countdown for if the word should be studied today.
     */
    private int rank;

    /**
     * Whether the word is studied TODAY.
     */
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

    public boolean shouldBeStudiedToday() {
        return Config.RECALL_DAYS.contains(this.getRank());
    }
}
