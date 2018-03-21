package bs.word;

import javax.persistence.*;
import java.util.Collection;

/**
 * @author yzy
 */
@Entity
public class WordbookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String wordbookName;

    @ManyToMany
    private Collection<WordEntity> words;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWordbookName() {
        return wordbookName;
    }

    public void setWordbookName(String wordbookName) {
        this.wordbookName = wordbookName;
    }

    public Collection<WordEntity> getWords() {
        return words;
    }

    public void setWords(Collection<WordEntity> words) {
        this.words = words;
    }
}