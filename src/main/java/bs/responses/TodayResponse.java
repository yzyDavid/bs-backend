package bs.responses;

import bs.entities.WordEntity;

import java.util.Collection;

/**
 * @author yzy
 * <p>
 * today to study words list.
 */
public class TodayResponse {
    private Collection<WordEntity> words;

    public TodayResponse(Collection<WordEntity> words) {
        this.words = words;
    }

    public Collection<WordEntity> getWords() {
        return words;
    }
}
