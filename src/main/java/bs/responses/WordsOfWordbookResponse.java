package bs.responses;

import bs.entities.WordEntity;

import java.util.Collection;

/**
 * TODO: fix with views
 *
 * @author yzy
 */
public class WordsOfWordbookResponse {
    private final Collection<WordEntity> words;

    public WordsOfWordbookResponse(Collection<WordEntity> words) {
        this.words = words;
    }

    public Collection<WordEntity> getWords() {
        return words;
    }
}
