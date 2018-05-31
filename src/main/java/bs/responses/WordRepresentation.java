package bs.responses;

import bs.entities.WordEntity;

/**
 * @author yzy
 */
public class WordRepresentation {
    private String word;
    private String meaning;

    public WordRepresentation(WordEntity entity) {
        this.word = entity.getWord();
        this.meaning = entity.getMeaning();
    }

    public String getWord() {
        return word;
    }

    public String getMeaning() {
        return meaning;
    }
}
