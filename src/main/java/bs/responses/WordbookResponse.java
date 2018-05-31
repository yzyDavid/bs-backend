package bs.responses;

import java.util.Collection;

/**
 * @author yzy
 */
public class WordbookResponse {
    private final Collection<WordbookRepresentation> wordbooks;

    public WordbookResponse(Collection<WordbookRepresentation> wordbooks) {
        this.wordbooks = wordbooks;
    }

    public Collection<WordbookRepresentation> getWordbooks() {
        return wordbooks;
    }
}
