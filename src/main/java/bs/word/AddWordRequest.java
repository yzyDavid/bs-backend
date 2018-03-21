package bs.word;

import java.util.ArrayList;

public class AddWordRequest {
    private String word;

    private String meaning;

    private ArrayList<String> wordbooks;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public ArrayList<String> getWordbooks() {
        return wordbooks;
    }

    public void setWordbooks(ArrayList<String> wordbooks) {
        this.wordbooks = wordbooks;
    }
}
