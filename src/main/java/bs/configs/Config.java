package bs.configs;

import java.util.ArrayList;

/**
 * @author yzy
 */
public class Config {
    public static final String FRONTEND_URL = "";
    public static final String AUTH_HEADER = "X-Auth-Token";
    public static final String CURRENT_UID_ATTRIBUTE = "Current-UID";

    public static final ArrayList<Integer> RECALL_DAYS = new ArrayList<>();

    static {
        RECALL_DAYS.add(1);
        RECALL_DAYS.add(2);
        RECALL_DAYS.add(5);
        RECALL_DAYS.add(8);
        RECALL_DAYS.add(14);
        RECALL_DAYS.add(30);

        for (int i = 0; i < RECALL_DAYS.size(); ++i) {
            RECALL_DAYS.set(i, 31 - RECALL_DAYS.get(i));
        }
    }

    public static final String DEV_KEY = "096c7b14-f466-4b99-8853-62bfc9216cad";

    public static final int WORDS_PER_DAY = 30;

    public static final int MAX_BATCH_TODAY_WORDS = 500;
}
