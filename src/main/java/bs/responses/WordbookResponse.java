package bs.responses;

import bs.entities.WordbookEntity;

import java.util.Collection;

public class WordbookResponse {
    private final Collection<WordbookEntity> wordbooks;

    public WordbookResponse(Collection<WordbookEntity> wordbooks) {
        this.wordbooks = wordbooks;
    }

    public Collection<WordbookEntity> getWordbooks() {
        return wordbooks;
    }
}
