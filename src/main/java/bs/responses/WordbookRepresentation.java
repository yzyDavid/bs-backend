package bs.responses;

import bs.entities.WordbookEntity;

/**
 * @author yzy
 */
public class WordbookRepresentation {
    private long id;
    private String wordbookName;
    private long wordCount;

    public WordbookRepresentation(WordbookEntity entity) {
        this.id = entity.getId();
        this.wordbookName = entity.getWordbookName();
        this.wordCount = entity.getWords().size();
    }

    public String getWordbookName() {
        return wordbookName;
    }

    public long getId() {
        return id;
    }

    public long getWordCount() {
        return wordCount;
    }
}
