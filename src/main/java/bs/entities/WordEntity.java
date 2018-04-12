package bs.entities;

import javax.persistence.*;
import java.util.Collection;

/**
 * @author yzy
 */
@Entity
public class WordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 50)
    private String word;

    private String meaning;

    @JoinTable(name = "WORD_WORDBOOK",
            joinColumns = {@JoinColumn(name = "WORDBOOK_ID", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "WORD_ID", referencedColumnName = "id")})
    @ManyToMany
    private Collection<WordbookEntity> wordbooks;

    @ManyToMany(mappedBy = "wordsStudying")
    private Collection<UserEntity> usersStudying;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Collection<WordbookEntity> getWordbooks() {
        return wordbooks;
    }

    public void setWordbooks(Collection<WordbookEntity> wordbooks) {
        this.wordbooks = wordbooks;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public Collection<UserEntity> getUsersStudying() {
        return usersStudying;
    }

    public void setUsersStudying(Collection<UserEntity> usersStudying) {
        this.usersStudying = usersStudying;
    }
}
