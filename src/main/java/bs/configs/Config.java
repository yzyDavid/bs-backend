package bs.configs;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author yzy
 */
public class Config {
    public static final String FRONTEND_URL = "";
    public static final String AUTH_HEADER = "X-Auth-Token";
    public static final String CURRENT_UID_ATTRIBUTE = "Current-UID";

    public static final Collection<Integer> RECALL_DAYS = new ArrayList<>();

    static {
        RECALL_DAYS.add(1);
        RECALL_DAYS.add(2);
        RECALL_DAYS.add(5);
        RECALL_DAYS.add(8);
        RECALL_DAYS.add(14);
        RECALL_DAYS.add(30);
    }

    public static final String DEV_KEY = "096c7b14-f466-4b99-8853-62bfc9216cad";
}
