package bs.word;

import java.util.Collection;

class WordsOfWordbookResponse {
    private final Collection<WordEntity> words;

    WordsOfWordbookResponse(Collection<WordEntity> words) {
        this.words = words;
    }

    public Collection<WordEntity> getWords() {
        return words;
    }
}
