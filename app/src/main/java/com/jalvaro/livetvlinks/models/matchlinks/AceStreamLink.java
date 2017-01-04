package com.jalvaro.livetvlinks.models.matchlinks;


import static com.jalvaro.livetvlinks.models.matchlinks.LinkType.ACESTREAM;

public class AceStreamLink extends PlexusLink {
    AceStreamLink(String rate, String ratecolor, String language, String link, String bitRate) {
        super(rate, ratecolor, language, link, bitRate, ACESTREAM);
    }
}
