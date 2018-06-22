package bs.responses;

import java.util.Collection;

/**
 * @author yzy
 * <p>
 * today to study words list.
 */
public class WordsResponse {
    private final Collection<WordRepresentation> words;

    public WordsResponse(Collection<WordRepresentation> words) {
        this.words = words;
    }

    public Collection<WordRepresentation> getWords() {
        return words;
    }
}
