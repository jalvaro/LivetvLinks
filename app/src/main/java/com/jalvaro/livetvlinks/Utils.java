package com.jalvaro.livetvlinks;

import com.jalvaro.livetvlinks.models.MatchLink;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by jordi on 2/10/16.
 */
public class Utils {
    public static boolean refresh(Calendar lastUpdate) {
        Calendar someMinutesAgo = Calendar.getInstance();
        someMinutesAgo.add(Calendar.MINUTE, -5);

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
