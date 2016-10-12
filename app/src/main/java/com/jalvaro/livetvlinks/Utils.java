package com.jalvaro.livetvlinks;

import com.jalvaro.livetvlinks.models.MatchLink;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by jordi on 2/10/16.
 */
public class Utils {
    public static final String LIVETV_BASE_URL = "http://livetv.sx/";
    public static final String LIVETV_URL = LIVETV_BASE_URL + "es/";
    private static final int SECONDS = 30;
    private static final int MINUTES = 5;

    public static boolean needsTobeRefreshed(Calendar lastUpdate) {
        Calendar someMinutesAgo = Calendar.getInstance();
        //someMinutesAgo.add(Calendar.MINUTE, -MINUTES);
        someMinutesAgo.add(Calendar.SECOND, -SECONDS);

        return lastUpdate == null || lastUpdate.before(someMinutesAgo);
    }

    public static List<MatchLink> filter(List<MatchLink> matchLinks, MatchLink.LinkType linkType) {
        List<MatchLink> filtered = new ArrayList<>();
        for (MatchLink matchLink : matchLinks) {
            if (matchLink.getLinkType() == linkType) {
                filtered.add(matchLink);
            }
        }
        return filtered;
    }
}
