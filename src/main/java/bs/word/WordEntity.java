package bs.word;

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

    private String word;

    private String meaning;

    @JoinTable(name = "WORD_WORDBOOK",
            joinColumns = {@JoinColumn(name = "WORDBOOK_ID", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "WORD_ID", referencedColumnName = "id")})
    @ManyToMany
    private Collection<WordbookEntity> wordbooks;

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
}
