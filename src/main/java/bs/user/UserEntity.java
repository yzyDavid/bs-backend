package bs.user;

import bs.word.WordEntity;

import javax.persistence.*;
import java.util.Collection;

/**
 * @author yzy
 *
 * TODO: index
 */
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;

    private String name;

    private String hashedPassword;

    private String salt;

    @JoinTable(name = "USER_WORD_STUDYING",
    joinColumns = {@JoinColumn(name = "WORD_ID", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "id")})
    @ManyToMany
    private Collection<WordEntity> wordsStudying;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Collection<WordEntity> getWordsStudying() {
        return wordsStudying;
    }

    public void setWordsStudying(Collection<WordEntity> wordsStudying) {
        this.wordsStudying = wordsStudying;
    }
}
