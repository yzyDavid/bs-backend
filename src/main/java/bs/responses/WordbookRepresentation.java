package bs.responses;

import bs.entities.WordbookEntity;

/**
 * @author yzy
 */
public class WordbookRepresentation {
    private long id;
    private String wordbookName;

    public WordbookRepresentation(WordbookEntity entity) {
        this.id = entity.getId();
        this.wordbookName = entity.getWordbookName();
    }

    public String getWordbookName() {
        return wordbookName;
    }

    public long getId() {
        return id;
    }
}
