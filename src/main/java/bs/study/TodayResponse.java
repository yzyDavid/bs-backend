package bs.study;

import bs.word.WordEntity;

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
