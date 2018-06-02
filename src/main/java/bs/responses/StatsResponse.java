package bs.responses;

/**
 *
 * @author yzy
 */
public class StatsResponse {
    private final long totalWords;
    private final long toStudyWords;
    private final long studiedWords;
    private final long customWords;
    private final long todayToStudyWords;

    public StatsResponse(long totalWords, long toStudyWords, long studiedWords, long customWords, long todayToStudyWords) {
        this.totalWords = totalWords;
        this.toStudyWords = toStudyWords;
        this.studiedWords = studiedWords;
        this.customWords = customWords;
        this.todayToStudyWords = todayToStudyWords;
    }

    public long getTotalWords() {
        return totalWords;
    }

    public long getToStudyWords() {
        return toStudyWords;
    }

    public long getStudiedWords() {
        return studiedWords;
    }

    public long getCustomWords() {
        return customWords;
    }

    public long getTodayToStudyWords() {
        return todayToStudyWords;
    }
}
