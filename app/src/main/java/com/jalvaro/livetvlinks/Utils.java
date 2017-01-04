package com.jalvaro.livetvlinks;

import java.util.Calendar;


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
}
